package mobi.dreambox.frameowrk.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import mobi.dreambox.frameowrk.core.util.GenericConstants;

import org.apache.commons.logging.Log;



/**
 * <p>Project: Common</p>
 * <p>Title: ZipFileUtil.java</p>
 * <p>Company: Wonders Group</p>
 *
 * @author Ryan
 * @date 2005-9-14 10:49:24
 * @version 1.0
 *
 */
public class ZipFileUtil {

	public static File unzipFile(String toBeUnzipFileName) throws IOException {
		return ZipFileUtil.unzipFile(new File(toBeUnzipFileName), false);
	}

	public static File unzipFile(String toBeUnzipFileName, boolean deleteSource) throws IOException {
		return ZipFileUtil.unzipFile(new File(toBeUnzipFileName), deleteSource);
	}

	public static File unzipFile(File toBeUnzipFile) throws IOException {
		return ZipFileUtil.unzipFile(toBeUnzipFile, toBeUnzipFile.getParentFile(), false);
	}

	public static File unzipFile(File toBeUnzipFile, boolean deleteSource) throws IOException {
		return ZipFileUtil.unzipFile(toBeUnzipFile, toBeUnzipFile.getParentFile(), deleteSource);
	}

	public static File unzipFile(ZipFile toBeUnzipFile) throws IOException {
		File tempFile = new File(toBeUnzipFile.getName());
		return ZipFileUtil.unzipFile(toBeUnzipFile, tempFile.getParentFile(), false);
	}

	public static File unzipFile(ZipFile toBeUnzipFile, boolean deleteSource) throws IOException {
		File tempFile = new File(toBeUnzipFile.getName());
		return ZipFileUtil.unzipFile(toBeUnzipFile, tempFile.getParentFile(), deleteSource);
	}

	public static File unzipFile(File toBeUnzipFile, File unzipFileDirectory, boolean deleteSource)
			throws IOException {
		FileOutputStream fileOutputStream = null;
		FileInputStream fileInputStream = null;
		ZipInputStream zipInputStream = null;
		ZipEntry zipEntry = null;
		File entryFile = null;
		Set roots = new HashSet();
		try {
			fileInputStream = new FileInputStream(toBeUnzipFile);
			zipInputStream = new ZipInputStream(fileInputStream);
			
			String zipEntryName = null;
			while((zipEntry = zipInputStream.getNextEntry()) != null) {
				zipEntryName = zipEntry.getName();
				zipEntryName = zipEntryName.replace('\\', '/');
				if(zipEntryName.indexOf("/") >= 0)
					roots.add(zipEntryName.substring(0,
						(zipEntryName.indexOf("/") >= 0 ? zipEntryName.indexOf("/")
								: zipEntryName.length())));

				if(zipEntry.isDirectory())
					continue;

				entryFile = new File(unzipFileDirectory, zipEntryName);
				if(!entryFile.getParentFile().exists())
					entryFile.getParentFile().mkdirs();
				fileOutputStream = new FileOutputStream(entryFile);
				byte bytes[] = new byte[GenericConstants.BUFFER_SIZE];
				int readBytes = -1;
				while((readBytes = zipInputStream.read(bytes, 0, bytes.length)) != -1)
					fileOutputStream.write(bytes, 0, readBytes);
				
		        fileOutputStream.close();
			}
		}
		finally {
			try {
				if(fileOutputStream != null)
					fileOutputStream.close();
				if(fileInputStream != null)
					fileInputStream.close();
				if(zipInputStream != null)
					zipInputStream.close();
			}
			catch(Exception e) {
			}
		}

		if(deleteSource) {
			try {
				FileUtil.deleteFile(toBeUnzipFile);
			}
			catch(Exception e) {
			}
		}
		
		File returnFile = null;
		if(roots.size() != 1)
			returnFile = unzipFileDirectory;
		else
			returnFile = new File(unzipFileDirectory.getAbsolutePath() + "/"
					+ (String)(new LinkedList(roots).getFirst()));

//		ZipFileUtil.logger.debug("Finish to unzip file " + toBeUnzipFile
//			+ " and return file " + returnFile + ".");
		return returnFile;
	}

	public static File unzipFile(ZipFile toBeUnzipFile, File unzipFileDirectory,
			boolean deleteSource) throws IOException {
		File tempFile = new File(toBeUnzipFile.getName());
		return ZipFileUtil.unzipFile(tempFile, unzipFileDirectory, deleteSource);
	}

	public static File zipDirectory(String toBeZippedDirectoryName) throws IOException {
		return ZipFileUtil.zipDirectory(new File(toBeZippedDirectoryName), false);
	}

	public static File zipDirectory(String toBeZippedDirectoryName, int zipLevel) throws IOException {
		return ZipFileUtil.zipDirectory(new File(toBeZippedDirectoryName), zipLevel, false);
	}

	public static File zipDirectory(String toBeZippedDirectoryName, boolean deleteSource)
			throws IOException {
		return ZipFileUtil.zipDirectory(new File(toBeZippedDirectoryName), deleteSource);
	}

	public static File zipDirectory(String toBeZippedDirectoryName, int zipLevel, boolean deleteSource)
			throws IOException {
		return ZipFileUtil.zipDirectory(new File(toBeZippedDirectoryName), zipLevel, deleteSource);
	}

	public static File zipDirectory(File toBeZippedDirectory) throws IOException {
		return ZipFileUtil.zipDirectory(toBeZippedDirectory, false);
	}

	public static File zipDirectory(File toBeZippedDirectory, int zipLevel) throws IOException {
		return ZipFileUtil.zipDirectory(toBeZippedDirectory, zipLevel, false);
	}

	public static File zipDirectory(File toBeZippedDirectory, boolean deleteSource)
			throws IOException {
		File zipFile = ZipFileUtil.getZipFile(toBeZippedDirectory);
		ZipFileUtil.zipDirectory(toBeZippedDirectory, zipFile, deleteSource);
		return zipFile;
	}

	public static File zipDirectory(File toBeZippedDirectory, int zipLevel, boolean deleteSource)
			throws IOException {
		File zipFile = ZipFileUtil.getZipFile(toBeZippedDirectory);
		ZipFileUtil.zipDirectory(toBeZippedDirectory, zipFile, zipLevel, deleteSource);
		return zipFile;
	}

	public static void zipDirectory(String toBeZippedDirectoryName, String zipFileName)
			throws IOException {
		ZipFileUtil.zipDirectory(new File(toBeZippedDirectoryName), new File(zipFileName), false);
	}

	public static void zipDirectory(String toBeZippedDirectoryName, String zipFileName, int zipLevel)
			throws IOException {
		ZipFileUtil.zipDirectory(new File(toBeZippedDirectoryName), new File(zipFileName), zipLevel, false);
	}

	public static void zipDirectory(String toBeZippedDirectoryName, String zipFileName,
			boolean deleteSource) throws IOException {
		ZipFileUtil.zipDirectory(new File(toBeZippedDirectoryName), new File(zipFileName),
				deleteSource);
	}

	public static void zipDirectory(String toBeZippedDirectoryName, String zipFileName,
			int zipLevel, boolean deleteSource) throws IOException {
		ZipFileUtil.zipDirectory(new File(toBeZippedDirectoryName), new File(zipFileName),
				zipLevel, deleteSource);
	}

	public static void zipDirectory(File toBeZippedDirectory, File zipFile, boolean deleteSource)
			throws IOException {
		ZipFileUtil.zipDirectory(toBeZippedDirectory, zipFile, Deflater.DEFAULT_COMPRESSION, deleteSource);
	}

	public static void zipDirectory(File toBeZippedDirectory, File zipFile, int zipLevel, boolean deleteSource)
			throws IOException {
		FileOutputStream fileOutputStream = null;
		ZipOutputStream zipOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(zipFile);
			zipOutputStream = new ZipOutputStream(fileOutputStream);
			zipOutputStream.setLevel(zipLevel);

//			ZipFileUtil.logger.debug("Start to zip directory " + toBeZippedDirectory
//					+ " to zip file " + zipFile + ".");
			ZipFileUtil.zipDirectory(toBeZippedDirectory, toBeZippedDirectory, zipOutputStream,
					deleteSource);
			zipOutputStream.finish();
			
//			ZipFileUtil.logger.debug("Finish to zip directory " + toBeZippedDirectory
//					+ " to zip file " + zipFile + ".");
		}
		finally {
			try {
				if(zipOutputStream != null)
					zipOutputStream.close();
				if(fileOutputStream != null)
					fileOutputStream.close();
			}
			catch(Exception e) {
			}
		}
	}

	private static void zipDirectory(File root, File toBeZippedDirectory,
			ZipOutputStream zipOutputStream, boolean deleteSource) throws IOException {
		File files[] = toBeZippedDirectory.listFiles();
		if(files != null) {
			for(int i = 0; i < files.length; i++) {
				if(files[i] != null) {
					if(files[i].isFile())
						ZipFileUtil.zipFile(root, files[i], zipOutputStream, false);
					else if(files[i].isDirectory())
						ZipFileUtil.zipDirectory(root, files[i], zipOutputStream, false);
				}
			}
		}

		if(deleteSource) {
			try {
				FileUtil.deleteFile(toBeZippedDirectory);
			}
			catch(Exception e) {
			}
		}
	}

	public static File zipFile(String toBeZippedFileName) throws IOException {
		return ZipFileUtil.zipFile(new File(toBeZippedFileName));
	}

	public static File zipFile(String toBeZippedFileName, int zipLevel) throws IOException {
		return ZipFileUtil.zipFile(new File(toBeZippedFileName), zipLevel);
	}

	public static File zipFile(String toBeZippedFileName, boolean deleteSource) throws IOException {
		return ZipFileUtil.zipFile(new File(toBeZippedFileName), deleteSource);
	}

	public static File zipFile(String toBeZippedFileName, int zipLevel, boolean deleteSource) throws IOException {
		return ZipFileUtil.zipFile(new File(toBeZippedFileName), zipLevel, deleteSource);
	}

	public static File zipFile(File toBeZippedFile) throws IOException {
		return ZipFileUtil.zipFile(toBeZippedFile, false);
	}

	public static File zipFile(File toBeZippedFile, int zipLevel) throws IOException {
		return ZipFileUtil.zipFile(toBeZippedFile, zipLevel, false);
	}

	public static File zipFile(File toBeZippedFile, boolean deleteSource) throws IOException {
		File zipFile = ZipFileUtil.getZipFile(toBeZippedFile);
		ZipFileUtil.zipFile(toBeZippedFile, zipFile, deleteSource);
		return zipFile;
	}

	public static File zipFile(File toBeZippedFile, int zipLevel, boolean deleteSource) throws IOException {
		File zipFile = ZipFileUtil.getZipFile(toBeZippedFile);
		ZipFileUtil.zipFile(toBeZippedFile, zipFile, zipLevel, deleteSource);
		return zipFile;
	}

	public static void zipFile(String toBeZippedFileName, String zipFileName) throws IOException {
		ZipFileUtil.zipFile(new File(toBeZippedFileName), new File(zipFileName), false);
	}

	public static void zipFile(String toBeZippedFileName, String zipFileName, int zipLevel) throws IOException {
		ZipFileUtil.zipFile(new File(toBeZippedFileName), new File(zipFileName), zipLevel, false);
	}

	public static void zipFile(String toBeZippedFileName, String zipFileName, boolean deleteSource)
			throws IOException {
		ZipFileUtil.zipFile(new File(toBeZippedFileName), new File(zipFileName), deleteSource);
	}

	public static void zipFile(String toBeZippedFileName, String zipFileName, int zipLevel, boolean deleteSource)
			throws IOException {
		ZipFileUtil.zipFile(new File(toBeZippedFileName), new File(zipFileName), zipLevel, deleteSource);
	}

	public static void zipFile(File toBeZippedFile, File zipFile, boolean deleteSource)
			throws IOException {
		ZipFileUtil.zipFile(toBeZippedFile, zipFile, Deflater.DEFAULT_COMPRESSION, deleteSource);
	}

	public static void zipFile(File toBeZippedFile, File zipFile, int zipLevel, boolean deleteSource)
			throws IOException {
		FileOutputStream fileOutputStream = null;
		ZipOutputStream zipOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(zipFile);
			zipOutputStream = new ZipOutputStream(fileOutputStream);
			zipOutputStream.setLevel(zipLevel);

//			ZipFileUtil.logger.debug("Start to zip file " + toBeZippedFile + " to zip file "
//					+ zipFile + ".");
			ZipFileUtil.zipFile(null, toBeZippedFile, zipOutputStream, deleteSource);
			zipOutputStream.finish();

//			ZipFileUtil.logger.debug("Finish to zip file " + toBeZippedFile + " to zip file "
//					+ zipFile + ".");
		}
		finally {
			try {
				if(zipOutputStream != null)
					zipOutputStream.close();
				if(fileOutputStream != null)
					fileOutputStream.close();
			}
			catch(Exception e) {
			}
		}
	}

	public static void zipFile(File root, File toBeZippedFile, ZipOutputStream zipOutputStream,
			boolean deleteSource) throws IOException {
		FileInputStream fileInputStream = null;
		try {
			if(root != null)
				zipOutputStream.putNextEntry(new ZipEntry(toBeZippedFile.getAbsolutePath().substring(
								root.getParentFile().getAbsolutePath().length()
										+ File.separator.length())));
			else
				zipOutputStream.putNextEntry(new ZipEntry(toBeZippedFile.getName()));

			fileInputStream = new FileInputStream(toBeZippedFile);
			int readBytes = -1;
			byte bytes[] = new byte[GenericConstants.BUFFER_SIZE];
			while((readBytes = fileInputStream.read(bytes, 0, bytes.length)) != -1)
				zipOutputStream.write(bytes, 0, readBytes);
		}
		finally {
			try {
				if(fileInputStream != null)
					fileInputStream.close();
			}
			catch(Exception e) {
			}
		}

		if(deleteSource) {
			try {
				FileUtil.deleteFile(toBeZippedFile);
			}
			catch(Exception e) {
			}
		}
	}

	private static File getZipFile(File file) {
		if(file.isDirectory())
			return new File(file.getParentFile(), file.getName() + ".zip");

		String fileName = file.getName();
		String zipFileName = null;
		int lastDotPosition = -1;
		if((lastDotPosition = fileName.lastIndexOf(".")) >= 0)
			zipFileName = fileName.substring(0, lastDotPosition) + ".zip";
		else
			zipFileName = fileName + ".zip";

		return new File(file.getParent(), zipFileName);
	}
}
