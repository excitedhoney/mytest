package mobi.dreambox.frameowrk.core.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import mobi.dreambox.frameowrk.core.io.filter.DirectoryFilter;
import mobi.dreambox.frameowrk.core.io.filter.GenericFileFilter;
import mobi.dreambox.frameowrk.core.util.CollectionsUtil;
import mobi.dreambox.frameowrk.core.util.GenericConstants;
import mobi.dreambox.frameowrk.core.util.StringUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;



/**
 * <p>
 * Title: Data Grid
 * </p>
 * 
 * <p>
 * Description: A general data persistence layer.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Wonders Group
 * </p>
 * 
 * @author Ryan
 * @version 1.0
 */
public class FileUtil {
	/**
	 * Logger for this class
	 */
	private static String seperator = "$";
	public static String RELATIVE_PATH="relative_path";

	public synchronized static String createWorkDir(String workdir) {
		String oldworkdir = workdir;
		File dirFile = new File(workdir);
		if (!dirFile.exists())
			return workdir;
		String[] includeFiles = dirFile.list();
		if (includeFiles == null || includeFiles.length < 1000) {
			return workdir;
		}
		int currentDirNum = 0;
		if (workdir.lastIndexOf(FileUtil.seperator) > 0) {
			String currentDirNumStr = workdir.substring(workdir
					.lastIndexOf(FileUtil.seperator)
					+ FileUtil.seperator.length());
			oldworkdir = workdir.substring(0, workdir
					.lastIndexOf(FileUtil.seperator));
			if (currentDirNumStr != null
					&& StringUtil.isNumeric(currentDirNumStr)) {
				currentDirNum = Integer.parseInt(currentDirNumStr);
			}
		}
		currentDirNum = currentDirNum + 1;
		return FileUtil.createWorkDir(oldworkdir + FileUtil.seperator
				+ currentDirNum);

	}

	public static File mergeFile(String destFileName,File[] srcFiles) throws IOException {
		File outFile = new File(destFileName); //取得输出名
		if(outFile.exists())
			outFile.delete();
		outFile.createNewFile();
		RandomAccessFile raf= new RandomAccessFile(outFile,"rw"); 
		try{
			int len = 0;
			byte[] bt = new byte[1024];
			FileInputStream fis = null;
			long alreadyWrite = 0;
			//打开所有的文件再写入到一个文件里
			for(File srcFile:srcFiles){
				raf.seek(alreadyWrite);
				fis = new FileInputStream(srcFile);
				while ((len = fis.read(bt)) > 0) {
					raf.write(bt, 0, len);
				}
				alreadyWrite = alreadyWrite + srcFile.length();
			}
			raf.close();
			return outFile;
		}catch(IOException e){
			throw new IOException(e.getMessage());
		}
		finally{
			raf.close();
		}
		

	}
	public static File appendFile(File destFile,File srcFile) throws IOException{
		if(!destFile.exists())
			destFile.createNewFile();
		byte[] buffer = new byte[1024];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(srcFile);
			while(fis.read(buffer) != -1)
				FileUtils.writeByteArrayToFile(destFile, buffer);
			return destFile;
		}
		finally{
			if(fis != null)
				fis.close();
		}
	}
	/**
	 * Generate the destination file. Use the source file name if the
	 * destination file is not existing, or generate a new file name for it.
	 * 
	 * @param sourceFile
	 * @param destinationFileDirectory
	 * @return
	 */
	public static File destinationFile(File sourceFile,
			File destinationFileDirectory) {
		File destinationFile = new File(destinationFileDirectory, sourceFile
				.getName());
		if (destinationFile.exists())
			destinationFile = new File(destinationFileDirectory, FilenameUtils
					.getBaseName(sourceFile.getName())
					+ "-"
					+ System.currentTimeMillis()
					+ "."
					+ FilenameUtils.getExtension(sourceFile.getName()));

		return destinationFile;
	}

	public static String getTXTFileEncoding(File file) throws IOException {
		// the system encoding
		String encoding = System.getProperty("file.encoding");
		RandomAccessFile randomReader = null;
		try {
			randomReader = new RandomAccessFile(file, "r");

			// read the first two bytes in file
			byte[] fileEncoding = new byte[2];
			randomReader.read(fileEncoding);

			if (fileEncoding[0] == -1 && fileEncoding[1] == -2)
				encoding = GenericConstants.ENCODING_UTF16;
		} finally {
			// TODO: handle exception
			if (randomReader != null)
				randomReader.close();
		}

		return encoding;
	}

	/**
	 * Get all files in directory.
	 * 
	 * @param directory
	 * @return
	 * @throws IOException
	 */
	public static File[] getFiles(File directory) throws IOException {
		return FileUtil.getFiles(directory, true);
	}

	/**
	 * Get files number the directory contains, include it's sub directory.
	 * 
	 * @param file
	 *            File
	 * @return int
	 */
	public static int getFilesNumber(File directory) throws IOException {
		return FileUtil.getFilesNumber(directory, true);
	}

	/**
	 * Get all files in directory.
	 * 
	 * @param directory
	 * @param fileFilter
	 * @return
	 * @throws IOException
	 */
	public static File[] getFiles(File directory, FileFilter fileFilter)
			throws IOException {
		return FileUtil.getFiles(directory, fileFilter, true);
	}

	/**
	 * Get files number the directory contains, include it's sub directory.
	 * 
	 * @param directory
	 *            File
	 * @param fileFilter
	 *            FileFilter
	 * @return int
	 * @throws IOException
	 */
	public static int getFilesNumber(File directory, FileFilter fileFilter)
			throws IOException {
		return FileUtil.getFilesNumber(directory, fileFilter, true);
	}

	/**
	 * Get all files in directory.
	 * 
	 * @param directory
	 * @param fileNamePattern
	 * @return
	 * @throws IOException
	 */
	public static File[] getFiles(File directory, String fileNamePattern)
			throws IOException {
		return FileUtil.getFiles(directory, new GenericFileFilter(
				fileNamePattern), true);
	}

	/**
	 * Get files number the directory contains, include it's sub directory.
	 * 
	 * @param directory
	 *            File
	 * @param fileNamePattern
	 *            String
	 * @return int
	 * @throws IOException
	 */
	public static int getFilesNumber(File directory, String fileNamePattern)
			throws IOException {
		return FileUtil.getFilesNumber(directory, new GenericFileFilter(
				fileNamePattern), true);
	}

	/**
	 * Get all files in directory.
	 * 
	 * @param directory
	 * @param fileNamePattern
	 * @param fileSuffixs
	 * @return
	 * @throws IOException
	 */
	public static File[] getFiles(File directory, String fileNamePattern,
			String fileSuffixs[]) throws IOException {
		return FileUtil.getFiles(directory, new GenericFileFilter(
				fileNamePattern, fileSuffixs), true);
	}

	/**
	 * Get files number the directory contains, include it's sub directory.
	 * 
	 * @param directory
	 *            File
	 * @param fileNamePattern
	 *            String
	 * @param fileSuffixs
	 *            String[]
	 * @return int
	 * @throws IOException
	 */
	public static int getFilesNumber(File directory, String fileNamePattern,
			String fileSuffixs[]) throws IOException {
		return FileUtil.getFilesNumber(directory, new GenericFileFilter(
				fileNamePattern, fileSuffixs), true);
	}

	/**
	 * Get all files in directory.
	 * 
	 * @param directory
	 * @param containSubdirectoy
	 * @return
	 * @throws IOException
	 */
	public static File[] getFiles(File directory, boolean containSubdirectoy)
			throws IOException {
		if (directory.isFile())
			return new File[] { directory };

		List<File> allFiles = new ArrayList<File>();

		File files[] = directory.listFiles();
		if (files != null && files.length > 0)
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile())
					allFiles.add(files[i]);
				else if (containSubdirectoy)
					CollectionsUtil.addAllIfNotNull(allFiles, CollectionsUtil
							.toList(FileUtil.getFiles(files[i],
									containSubdirectoy)));

		return FileUtil.toFileArray(allFiles);
	}

	/**
	 * Get files number the directory contains.
	 * 
	 * @param directory
	 *            File
	 * @param containSubdirectoy
	 *            boolean
	 * @return int
	 */
	public static int getFilesNumber(File directory, boolean containSubdirectoy)
			throws IOException {
		if (directory.isFile())
			return 1;

		int filesNumber = 0;

		File files[] = directory.listFiles();
		if (files != null && files.length > 0)
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile())
					filesNumber++;
				else if (containSubdirectoy)
					filesNumber += FileUtil.getFilesNumber(files[i],
							containSubdirectoy);

		return filesNumber;
	}

	/**
	 * Get all files in directory.
	 * 
	 * @param directory
	 * @param fileNamePattern
	 * @param containSubdirectoy
	 * @return
	 * @throws IOException
	 */
	public static File[] getFiles(File directory, String fileNamePattern,
			boolean containSubdirectoy) throws IOException {
		return FileUtil.getFiles(directory, new GenericFileFilter(
				fileNamePattern), containSubdirectoy);
	}

	/**
	 * Get files number the directory contains.
	 * 
	 * @param directory
	 *            File
	 * @param fileNamePattern
	 *            String
	 * @param containSubdirectoy
	 *            boolean
	 * @return int
	 * @throws IOException
	 */
	public static int getFilesNumber(File directory, String fileNamePattern,
			boolean containSubdirectoy) throws IOException {
		return FileUtil.getFilesNumber(directory, new GenericFileFilter(
				fileNamePattern), containSubdirectoy);
	}

	/**
	 * Get all files in directory.
	 * 
	 * @param directory
	 * @param fileNamePattern
	 * @param fileSuffixs
	 * @param containSubdirectoy
	 * @return
	 * @throws IOException
	 */
	public static File[] getFiles(File directory, String fileNamePattern,
			String fileSuffixs[], boolean containSubdirectoy)
			throws IOException {
		return FileUtil.getFiles(directory, new GenericFileFilter(
				fileNamePattern, fileSuffixs), containSubdirectoy);
	}

	/**
	 * Get files number the directory contains.
	 * 
	 * @param directory
	 *            File
	 * @param fileNamePattern
	 *            String
	 * @param fileSuffixs
	 *            String[]
	 * @param containSubdirectoy
	 *            boolean
	 * @return int
	 * @throws IOException
	 */
	public static int getFilesNumber(File directory, String fileNamePattern,
			String fileSuffixs[], boolean containSubdirectoy)
			throws IOException {
		return FileUtil.getFilesNumber(directory, new GenericFileFilter(
				fileNamePattern, fileSuffixs), containSubdirectoy);
	}

	/**
	 * Get all files in directory.
	 * 
	 * @param directory
	 * @param fileFilter
	 * @param containSubdirectoy
	 * @return
	 * @throws IOException
	 */
	public static File[] getFiles(File directory, FileFilter fileFilter,
			boolean containSubdirectoy) throws IOException {
		if (directory.isFile())
			return new File[] { directory };

		List<File> allFiles = new ArrayList<File>();

		// search files
		File files[] = directory.listFiles(fileFilter);
		if (files != null && files.length > 0)
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile())
					allFiles.add(files[i]);

		if (containSubdirectoy) {
			// search sub directories
			File directories[] = directory.listFiles(new DirectoryFilter());
			if (directories != null && directories.length > 0)
				for (int i = 0; i < directories.length; i++) {
					files = FileUtil.getFiles(directories[i], fileFilter,
							containSubdirectoy);
					if (files != null)
						CollectionsUtil.addAllIfNotNull(allFiles,
								CollectionsUtil.toList(files));
				}
		}

		return FileUtil.toFileArray(allFiles);
	}

	/**
	 * Get files number the directory contains.
	 * 
	 * @param directory
	 *            File
	 * @param fileFilter
	 *            FileFilter
	 * @param containSubdirectoy
	 *            boolean
	 * @return int
	 * @throws IOException
	 */
	public static int getFilesNumber(File directory, FileFilter fileFilter,
			boolean containSubdirectoy) throws IOException {
		if (directory.isFile())
			return 1;

		int filesNumber = 0;

		// search files
		File files[] = directory.listFiles(fileFilter);
		if (files != null && files.length > 0)
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile())
					filesNumber++;

		if (containSubdirectoy) {
			// search sub directories
			File directories[] = directory.listFiles(new DirectoryFilter());
			if (directories != null && directories.length > 0)
				for (int i = 0; i < directories.length; i++)
					filesNumber += FileUtil.getFilesNumber(directories[i],
							fileFilter, containSubdirectoy);
		}

		return filesNumber;
	}

	/**
	 * Get all sub directories in directory.
	 * 
	 * @param directory
	 * @return
	 * @throws IOException
	 */
	public static File[] getDirectories(File directory) throws IOException {
		return FileUtil.getDirectories(directory, true);
	}

	/**
	 * Get sub directories number the directory contains, include it's sub
	 * directory.
	 * 
	 * @param file
	 *            File
	 * @return int
	 */
	public static int getDirectoriesNumber(File directory) throws IOException {
		return FileUtil.getDirectoriesNumber(directory, true);
	}

	/**
	 * Get all sub directories in directory.
	 * 
	 * @param directory
	 * @param fileFilter
	 * @return
	 * @throws IOException
	 */
	public static File[] getDirectories(File directory, FileFilter fileFilter)
			throws IOException {
		return FileUtil.getDirectories(directory, fileFilter, true);
	}

	/**
	 * Get sub directories number the directory contains, include it's sub
	 * directory.
	 * 
	 * @param directory
	 *            File
	 * @param fileFilter
	 *            FileFilter
	 * @return int
	 * @throws IOException
	 */
	public static int getDirectoriesNumber(File directory, FileFilter fileFilter)
			throws IOException {
		return FileUtil.getDirectoriesNumber(directory, fileFilter, true);
	}

	/**
	 * Get all sub directories in directory.
	 * 
	 * @param directory
	 * @param directoryNamePattern
	 * @return
	 * @throws IOException
	 */
	public static File[] getDirectories(File directory,
			String directoryNamePattern) throws IOException {
		return FileUtil.getDirectories(directory, new DirectoryFilter(
				directoryNamePattern), true);
	}

	/**
	 * Get sub directories number the directory contains, include it's sub
	 * directory.
	 * 
	 * @param directory
	 *            File
	 * @param directoryNamePattern
	 *            String
	 * @return int
	 * @throws IOException
	 */
	public static int getDirectoriesNumber(File directory,
			String directoryNamePattern) throws IOException {
		return FileUtil.getDirectoriesNumber(directory, new DirectoryFilter(
				directoryNamePattern), true);
	}

	/**
	 * Get all sub directories in directory.
	 * 
	 * @param directory
	 * @param containSubdirectoy
	 * @return
	 * @throws IOException
	 */
	public static File[] getDirectories(File directory,
			boolean containSubdirectoy) throws IOException {
		return FileUtil.getDirectories(directory, new DirectoryFilter(),
				containSubdirectoy);
	}

	/**
	 * Get sub directories number the directory contains.
	 * 
	 * @param directory
	 *            File
	 * @param containSubdirectoy
	 *            boolean
	 * @return int
	 */
	public static int getDirectoriesNumber(File directory,
			boolean containSubdirectoy) throws IOException {
		return FileUtil.getDirectoriesNumber(directory, new DirectoryFilter(),
				containSubdirectoy);
	}

	/**
	 * Get all sub directories in directory.
	 * 
	 * @param directory
	 * @param directoryNamePattern
	 * @param containSubdirectoy
	 * @return
	 * @throws IOException
	 */
	public static File[] getDirectories(File directory,
			String directoryNamePattern, boolean containSubdirectoy)
			throws IOException {
		return FileUtil.getDirectories(directory, new DirectoryFilter(
				directoryNamePattern), containSubdirectoy);
	}

	/**
	 * Get sub directories number the directory contains.
	 * 
	 * @param directory
	 *            File
	 * @param directoryNamePattern
	 *            String
	 * @param containSubdirectoy
	 *            boolean
	 * @return int
	 * @throws IOException
	 */
	public static int getDirectoriesNumber(File directory,
			String directoryNamePattern, boolean containSubdirectoy)
			throws IOException {
		return FileUtil.getDirectoriesNumber(directory, new DirectoryFilter(
				directoryNamePattern), containSubdirectoy);
	}

	/**
	 * Get all sub directories in directory.
	 * 
	 * @param directory
	 * @param directoryFilter
	 * @param containSubdirectoy
	 * @return
	 * @throws IOException
	 */
	public static File[] getDirectories(File directory,
			FileFilter directoryFilter, boolean containSubdirectoy)
			throws IOException {
		if (directory.isFile())
			return null;

		List<File> allFiles = new ArrayList<File>();

		if (directoryFilter == null)
			directoryFilter = new DirectoryFilter();
		File files[] = directory.listFiles(directoryFilter);
		if (files != null && files.length > 0)
			for (int i = 0; i < files.length; i++) {
				allFiles.add(files[i]);
				if (containSubdirectoy)
					CollectionsUtil.addAllIfNotNull(allFiles, CollectionsUtil
							.toList(FileUtil.getDirectories(files[i],
									directoryFilter, containSubdirectoy)));
			}

		return FileUtil.toFileArray(allFiles);
	}

	/**
	 * Get sub directories number the directory contains.
	 * 
	 * @param directory
	 *            File
	 * @param directoryFilter
	 *            FileFilter
	 * @param containSubdirectoy
	 *            boolean
	 * @return int
	 * @throws IOException
	 */
	public static int getDirectoriesNumber(File directory,
			FileFilter directoryFilter, boolean containSubdirectoy)
			throws IOException {
		if (directory.isFile())
			return 0;

		int directoriesNumber = 0;

		if (directoryFilter == null)
			directoryFilter = new DirectoryFilter();
		File directories[] = directory.listFiles(directoryFilter);
		if (directories != null && directories.length > 0)
			for (int i = 0; i < directories.length; i++) {
				directoriesNumber++;
				if (containSubdirectoy)
					directoriesNumber += FileUtil
							.getDirectoriesNumber(directories[i],
									directoryFilter, containSubdirectoy);
			}

		return directoriesNumber;
	}

	public static File[] toFileArray(Collection<File> files) {
		if (files == null || files.size() == 0)
			return null;

		File fileArray[] = new File[files.size()];
		int i = 0;
		for (Iterator<File> iter = files.iterator(); iter.hasNext(); i++)
			fileArray[i] =  iter.next();

		return fileArray;
	}

	public static int countCharacters(File file) throws IOException {
		return FileUtil.countCharacters(file, GenericConstants.ENCODING_GBK);
	}

	public static int countCharacters(File file, String charsetName)
			throws IOException {
		char chars[] = new char[GenericConstants.BUFFER_SIZE];
		FileInputStream fileInputStream = null;
		InputStreamReader inputStreamReader = null;
		int readNumner = -1, characterNumber = 0;
		try {
			fileInputStream = new FileInputStream(file);
			inputStreamReader = new InputStreamReader(fileInputStream,
					charsetName);
			while ((readNumner = inputStreamReader.read(chars)) != -1)
				characterNumber += readNumner;

			return characterNumber;
		} finally {
			// TODO: handle exception
			IOUtil.closeInputStream(fileInputStream);
			IOUtil.closeReader(inputStreamReader);
		}
	}

	public static byte[] readFileToBytes(File file) throws IOException {
		FileInputStream fileInputStream = null;
		byte bytes[] = new byte[GenericConstants.BUFFER_SIZE];
		int read = -1;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
			fileInputStream = new FileInputStream(file);
			while ((read = fileInputStream.read(bytes)) != -1)
				byteArrayOutputStream.write(bytes, 0, read);

			return byteArrayOutputStream.toByteArray();
		} finally {
			// TODO: handle exception
			IOUtil.closeStream(fileInputStream, byteArrayOutputStream);
		}
	}

	public static String readFileToString(File file) throws IOException {
		return FileUtils.readFileToString(file, GenericConstants.ENCODING_GBK);
	}

	public static String readFileToString(File file, String charsetName)
			throws IOException {
		return FileUtils.readFileToString(file, charsetName);
	}

	public static File toExistingFile(Object dataValue) {
		File file = null;
		if (dataValue instanceof File)
			return (File) dataValue;
		else {
			String str = (String) dataValue;
			if (str.length() > 1024 * 2)
				return null;
			else
				file = new File(str);
		}

		if (file.exists())
			return file;
		else
			return null;
	}

	public static boolean overwriteRenameTo(File sourceFile,
			File destinationFile) {
		if (!destinationFile.exists())
			return sourceFile.renameTo(destinationFile);
		else
			try {
				FileUtils.copyFile(sourceFile, destinationFile, true);
				FileUtil.deleteFile(sourceFile);
				return true;
			} catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
	}

	public static File[] moveTo(File files[], String rootDirectoryName,
			File destinationFileDirectory) {
		File destinationFiles[] = null;
		if (CollectionsUtil.isNotNull(files)) {
			destinationFiles = new File[files.length];
			for (int i = 0; i < files.length; i++)
				destinationFiles[i] = FileUtil.moveTo(files[i],
						rootDirectoryName, destinationFileDirectory);
		}

		return destinationFiles;
	}

	public static File moveTo(File file, String rootDirectoryName,
			File destinationFileDirectory) {
		File destinationFile = null;
		if (file != null && file.exists()) {
			destinationFile = FileUtil.destinationFile(file, rootDirectoryName,
					destinationFileDirectory);
			if (!file.renameTo(destinationFile)) {
				destinationFile = null;
			}
		}

		return destinationFile;
	}

	public static File destinationFile(File file, String rootDirectoryName,
			File destinationFileDirectory) {
		if (StringUtil.isNull(rootDirectoryName))
			return new File(destinationFileDirectory, file.getName());
		else
			return new File(destinationFileDirectory.getAbsolutePath()
					+ file.getAbsolutePath().substring(
							rootDirectoryName.length()));
	}

	public static void deleteFiles(List<File> files) {
		if (files == null)
			return;
		try {
			for (int i = 0; i < files.size(); i++) {
				FileUtil.deleteFile((File) files.get(i));
			}
		} catch (Exception e) {
		}
	}

	public static void deleteFiles(File files[]) {
		if (files == null || files.length == 0)
			return;
		for (int i = 0; i < files.length; i++)
			FileUtil.deleteFile(files[i]);
	}

	public static void deleteFile(File file) {
		if (file == null) {
			return;
		}
		if (!file.exists()) {
			return;
		}
		try {
			if (file.isFile())
				FileUtils.forceDelete(file);
			else if (file.isDirectory())
				FileUtils.deleteDirectory(file);
		} catch (Exception e) {
			try {
				Thread.sleep(1000);
				if (file.isFile())
					FileUtils.forceDelete(file);
				else if (file.isDirectory())
					FileUtils.deleteDirectory(file);
			} catch (IOException e1) {
			} catch (InterruptedException e2) {
			}
		}
	}

	public static void copyDirectory(File sourceFile, File destFile) {
		try {
			FileUtils.copyDirectory(sourceFile, destFile);
		} catch (IOException e) {
		}
	}
 
	public static void copyFile(File sourceFile, File destFile) {
		try {
			FileUtils.copyFile(sourceFile, destFile);
		} catch (IOException e) {
		}
	}

	public static void copyFile(File sourceFile, File destFile,
			boolean preserveFileData) {
		try {
			FileUtils.copyFile(sourceFile, destFile, preserveFileData);
		} catch (IOException e) {
		}
	}

	public static long sizeOfFile(File file) {
		int size = 0;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			size = is.available();
			return size;
		} catch (IOException e) {
			return -1;
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}
	}

	public static File cloneFile(File sourceFile) {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(sourceFile);
			String suffix = ".dgf.zip";
			String sourceFileName = sourceFile.getAbsolutePath();
			String copFileName = sourceFileName.substring(0, sourceFileName
					.lastIndexOf(suffix))
					+ "_copy" + suffix;
			File targetFile = new File(copFileName);
			output = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024 * 4];
			int n = 0;
			while ((n = input.read(buffer)) != -1) {
				output.write(buffer, 0, n);
			}
			return targetFile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
	/**
	 * 此方法可以用于文件夹的拷贝功能
	 * @param desFile 为目标端的文件路径
	 * @param srcFile 源端的文件路径
	 */
	public static void copyDirectiory(String desFile, String srcFile) {
		new File(desFile).mkdirs();
		File[] file = (new File(srcFile)).listFiles();
		try {
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					FileInputStream input = new FileInputStream(file[i]);
					FileOutputStream output = new FileOutputStream(desFile
							+ "/" + file[i].getName());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}

				if (file[i].isDirectory()) {
					copyDirectiory(desFile + "/" + file[i].getName(), srcFile
							+ "/" + file[i].getName());
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public static File putToFile(File destFile,List<String> dataList){
		try {
			if(!destFile.getParentFile().exists())
				destFile.getParentFile().mkdirs();
			if(!destFile.exists())
				destFile.createNewFile();
			String logStr = new String();
			for(int i=0;i<dataList.size();i++){
				logStr += dataList.get(i) +"\\$cuteums\\$"+ "\n";
			}
			//将已经包含了新的log信息的文件保存到log文件
		   BufferedWriter utput = new BufferedWriter(new FileWriter(destFile));
	       utput.write(logStr);
	       utput.close();
		} catch (IOException e) {
			return null;
		}
		
		return destFile;
	}
	
	public static List<String> popFromFile(File srcFile){
		if(srcFile==null)
			return null;
		if(!srcFile.exists())
			return null;
		List<String> resultList = new ArrayList<String>();
		BufferedReader input;
		try {
			input = new BufferedReader(new FileReader(srcFile));
			String logTemp = new String();
			String tmpString = new String();
			//读出所有已经记录的log信息
			while ((logTemp = input.readLine()) != null) {
				if(tmpString==null || tmpString.equals("")){
					tmpString = logTemp;
				}else{
					tmpString += "\n"+logTemp;
				}
				if(logTemp.endsWith("\\$cuteums\\$")){
					tmpString = tmpString.substring(0,tmpString.lastIndexOf("\\$cuteums\\$"));
					resultList.add(tmpString);
					tmpString = "";
				}
				
		   }
		   input.close();
			return resultList;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
	}
	
	public static boolean downloadFile(String fileUrl,File destFile){
		boolean result = false; 
		try {  
		    URL url = new URL(fileUrl);  
		    HttpURLConnection conn =(HttpURLConnection) url.openConnection();  
		    conn.setDoInput(true);  
		    conn.connect();  
		  if(  conn.getResponseCode() == HttpURLConnection.HTTP_OK)  
		  {  
		      InputStream is = conn.getInputStream();  
		        FileOutputStream fos = new FileOutputStream(destFile);  
		        byte[] bt = new byte[1024];  
		        int i = 0;  
		        while ((i = is.read(bt)) > 0) {  
		        fos.write(bt, 0, i);  
		        }  
		        fos.flush();  
		        fos.close();  
		        is.close();   
		        result = true;
		          
		  }
		     
		} catch (FileNotFoundException e) {  
		    
		} catch (IOException e) {  
		    
		}  
		return result; 
	}
	
	public static String getExtension(File f) { 
        return (f != null) ? getExtension(f.getName()) : ""; 
    }
	
	public static String getExtension(String filename) { 
        return getExtension(filename, ""); 
    } 

    public static String getExtension(String filename, String defExt) { 
        if ((filename != null) && (filename.length() > 0)) { 
            int i = filename.lastIndexOf('.'); 

            if ((i >-1) && (i < (filename.length() - 1))) { 
                return filename.substring(i + 1); 
            } 
        } 
        return defExt; 
    } 

	public static void main(String[] args) {
		File tmpFile = new File("/Test/tmpFile.csv");
		List<String> testList = new ArrayList<String>();
		for(int i=0;i<1000;i++){
//			testList.add("asdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljfasdfjlsadkjflasjdf;klasjdf;klsajdf;lkasjdfl;kjasdl;fjsadl;fkjdskjfdsdsakjfkdsjkfajsldkfjklasdjfklsajdflkjaslfjasdljf");
			testList.add("aaaaaaaa\naa"+i);
		}
		FileUtil.putToFile(tmpFile, testList);
		
		List<String> resultList = FileUtil.popFromFile(tmpFile);
		System.out.println(resultList.get(0));
	}
}
