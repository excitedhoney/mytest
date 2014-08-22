/** 
 * Copyright (c) 2003-2007 Wonders Information Co.,Ltd. All Rights Reserved.
 * 5-6/F, 20 Bldg, 481 Guiping RD. Shanghai 200233,PRC
 *
 * This software is the confidential and proprietary information of Wonders Group.
 * (Research & Development Center). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gun.org
 */

package mobi.dreambox.frameowrk.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
/**
 * 文件分隔器:给定文件的路径和每一块要拆分的大小，就可以按要求拆分文件
 * 如果指定的块给原文件都还要大，为了不动原文件，就生成另一个文件，以.bak为后缀，这样可以保证原文件
 * 如果是程序自动拆分为多个文件，那么后缀分别为".part序号"，这样就可以方便文件的合并了
 * 原理：很简单，就是利用是输入输出流，加上随机文件读取。
 * @author oumingzhi
 * @version $Revision$ 2009-10-30
 * @author (lastest modification by $Author$)
 * @since 4.1
 */
public class FileSeparatorUtil
 {
	private String FileName = null;//原文件名

	private long FileSize = 0;//原文件的大小

	private long BlockNum = 0;//可分的块数
	
	public final static String SEPARATED_FILE_SUFFIX = ".part";
	
	private String[] SuccessSeparatoredFileNames = null; //调用一次separatorFile方法后成功分离的文件全路径名称集合

	public FileSeparatorUtil() {
	}

	/**
	 * 
	 * @param fileAndPath 原文件名及路径
	 */
	private void getFileAttribute(String fileAndPath)//取得原文件的属性
	{
		File file = new File(fileAndPath);
		FileName = file.getName();
		FileSize = file.length();
	}

	/**
	 * 
	 * @param blockSize 每一块的大小
	 * @return 能够分得的块数
	 */
	private long getBlockNum(long blockSize)//取得分块数
	{
		long fileSize = FileSize;
		if (fileSize <= blockSize){//如果分块的小小只够分一个块
			return 1;
		}
		else {
			if (fileSize % blockSize > 0) {
				return fileSize / blockSize + 1;
			}
			else {
				return fileSize / blockSize;
			}
		}
	}

	/**
	 * 
	 * @param filePath 原文件及完整路径
	 * @param currentBlock 当前块的序号
	 * @return 现在拆分后块的文件名
	 */
	private String generateSeparatorFileName(String fileAndPath, int currentBlock)//生成折分后的文件名，以便于将来合将
	{
		return fileAndPath + SEPARATED_FILE_SUFFIX + currentBlock;
	}

	/**
	 * 
	 * @param fileAndPath 原文件及完整路径
	 * @param fileSeparateName 文件分隔后要生成的文件名，与原文件在同一个目录下
	 * @param blockSize 当前块要写的字节数
	 * @param beginPos 从原文件的什么地方开始读取
	 * @return true为写入成功，false为写入失败
	 */
	private boolean writeFile(String fileAndPath, String fileSeparateName, long blockSize, long beginPos)//往硬盘写文件
	{
		RandomAccessFile raf = null;
		FileOutputStream fos = null;
		byte[] bt = new byte[1024];
		long writeByte = 0;
		int len = 0;
		try {
			raf = new RandomAccessFile(fileAndPath, "r");
			raf.seek(beginPos);
			fos = new FileOutputStream(fileSeparateName);
			while ((len = raf.read(bt)) > 0) {
				if (writeByte < blockSize)//如果当前块还没有写满
				{
					writeByte = writeByte + len;
					if (writeByte <= blockSize)
						fos.write(bt, 0, len);
					else {
						len = len - (int) (writeByte - blockSize);
						fos.write(bt, 0, len);
					}
				}
			}
			fos.close();
			raf.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				if (fos != null)
					fos.close();
				if (raf != null)
					raf.close();
			}
			catch (Exception f) {
				f.printStackTrace();
			}
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param fileAndPath 原文路径及文件名
	 * @param blockSize 要拆分的每一块的大小
	 * @return true为拆分成功，false为拆分失败
	 */
	public boolean separatorFile(String fileAndPath, long blockSize)//折分文件主函数
	{
		List<String> successSeparatoredFileNamesLst = new ArrayList<String>();
		getFileAttribute(fileAndPath);//将文件的名及大小属性取出来
		//System.out.println("FileSize:"+FileSize);
		//System.out.println("blockSize:"+blockSize);
		BlockNum = getBlockNum(blockSize);//取得分块总数
		//System.out.println("BlockNum:"+BlockNum);
		//System.exit(0);
		if (BlockNum == 1)//如果只能够分一块，就一次性写入
			blockSize = FileSize;
		long writeSize = 0;//每次写入的字节
		long writeTotal = 0;//已经写了的字节
		String FileCurrentNameAndPath = null;
		for (int i = 1; i <= BlockNum; i++) {
			if (i < BlockNum)
				writeSize = blockSize;//取得每一次要写入的文件大小
			else
				writeSize = FileSize - writeTotal;
			if (BlockNum == 1)
				FileCurrentNameAndPath = fileAndPath + SEPARATED_FILE_SUFFIX + "1";
			else
				FileCurrentNameAndPath = generateSeparatorFileName(fileAndPath, i);
			//System.out.print("本次写入:"+writeSize);     
			
			//循环往硬盘写文件
			if (writeFile(fileAndPath, FileCurrentNameAndPath, writeSize, writeTotal)){
				successSeparatoredFileNamesLst.add(FileCurrentNameAndPath);
			}else{
				successSeparatoredFileNamesLst.clear();
				return false;
			}
			writeTotal = writeTotal + writeSize;
			//System.out.println("  总共写入:"+writeTotal);
		}
		SuccessSeparatoredFileNames =(String[]) successSeparatoredFileNamesLst.toArray(new String[successSeparatoredFileNamesLst.size()]);
		return true;
	}

	/**
	 * 获取分离成功的文件列表(仅仅当调用separatorFile方法被成功返回后才有效，否则始终返回空值Null)
	 * @return
	 */
	public List<File> getSeparatoredFiles(){
		List<File> files = new ArrayList<File>();
		if(SuccessSeparatoredFileNames==null || SuccessSeparatoredFileNames.length==0){
		   return null;
		}
		for(int i=0;i<SuccessSeparatoredFileNames.length;i++){
			File f = new File(SuccessSeparatoredFileNames[i]);
			files.add(f);
		}
		return files;
	}
	
	public static void main(String[] args) {
/*		FileSeparatorUtil separator = new FileSeparatorUtil();
		String fileAndPath = "f:\\testtest\\testtest.rar";//文件名及路径
		long blockSize =5 * 1024 * 1024;//每一个文件块的大小，大小是按字节计算
		if (separator.separatorFile(fileAndPath, blockSize)) {
			System.out.println("文件折分成功！");
			System.out.println("被分拆的文件列表如下");
			List<File> files = separator.getSeparatoredFiles();
			for(int i=0;i<files.size();i++){
				File f = (File) files.get(i);
				System.out.println("文件"+(i+1)+"完整路径为:"+f.getAbsolutePath());
			}
		}
		else {
			System.out.println("文件折分失败！");
		}*/
	}
}
