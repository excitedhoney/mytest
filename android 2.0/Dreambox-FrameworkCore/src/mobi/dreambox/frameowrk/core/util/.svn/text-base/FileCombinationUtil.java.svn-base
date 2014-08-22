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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 合并文件：合并由拆分文件拆分的文件
 * 要求将拆分文件放到一个文件夹中
 * 主要利用随机文件读取和文件输入输出流
 * @author oumingzhi
 * @version $Revision$ 2009-10-30
 * @author (lastest modification by $Author$)
 * @since 4.1
 */
public class FileCombinationUtil
 {
	private String srcDirectory = null;//拆分文件存放的目录

	private String[] separatedFiles;//存放所有拆分文件名

	private String[][] separatedFilesAndSize;//存放所有拆分文件名及分件大小

	private int FileNum = 0;//确定文件个数

	private String fileRealName = "";//据拆分文件名确定现在原文件名
	
	private String[] successCombinatedFileNames = null; //调用一次CombFile方法时成功合并的文件全路径名称集合

	public FileCombinationUtil() {
	}
	
	public FileCombinationUtil(String srcDirectory) {
		this.srcDirectory = srcDirectory;
	}
	
	public String getSrcDirectory() {
		return srcDirectory;
	}

	public void setSrcDirectory(String srcDirectory) {
		this.srcDirectory = srcDirectory;
	}

	/**
	 * 
	 * @param sFileName 任一一个拆分文件名
	 * @return 原文件名
	 */
	private String getRealName(String sFileName) {
		StringTokenizer st = new StringTokenizer(sFileName, ".");
		return st.nextToken() + "." + st.nextToken();
	}

	/**
	 * 取得指定拆分文件模块的文件大小
	 * @param FileName 拆分的文件名
	 * @return
	 */
	private long getFileSize(String FileName) {
		FileName = srcDirectory + FileName;
		return (new File(FileName).length());
	}

	/**
	 * 生成一些属性，做初使化
	 * @param drictory 拆分文件目录
	 */
	public void setRealFileName(String fileName) {
		File file = new File(srcDirectory);
		List<String> allFileNames = new ArrayList<String>();
		String[] allFiles = file.list();
		if(allFiles!=null && allFiles.length>0){
		   for(int i=0;i<allFiles.length;i++){
			   if(allFiles[i].startsWith(fileName) && allFiles[i].indexOf(FileSeparatorUtil.SEPARATED_FILE_SUFFIX)>=0){
				  allFileNames.add(allFiles[i]);
			   }
		   }
		}
		separatedFiles = new String[allFileNames.size()];//依文件数目动态生成一维数组，只有文件名
		separatedFiles =(String[]) allFileNames.toArray(new String[allFileNames.size()]);
		//依文件数目动态生成二维数组，包括文件名和文件大小
		//第一维装文件名，第二维为该文件的字节大小
		separatedFilesAndSize = new String[separatedFiles.length][2];
		Arrays.sort(separatedFiles);//排序
		FileNum = separatedFiles.length;//当前文件夹下面有多少个文件
		for (int i = 0; i < FileNum; i++) {
			separatedFilesAndSize[i][0] = separatedFiles[i];//文件名

			separatedFilesAndSize[i][1] = String.valueOf(getFileSize(separatedFiles[i]));//文件大上
		}
		//fileRealName = getRealName(separatedFiles[FileNum - 1]);//取得文件分隔前的原文件名
		fileRealName = fileName;
	}
	

	/**
	 * 合并文件：利用随机文件读写
	 * @return true为成功合并文件
	 */
	public boolean CombFile() {
		List<String> successCombinatedFileNamesLst = new ArrayList<String>();
		RandomAccessFile raf = null;
		long alreadyWrite = 0;
		FileInputStream fis = null;
		int len = 0;
		byte[] bt = new byte[1024];
		try {
			raf = new RandomAccessFile(srcDirectory + fileRealName, "rw");
			for (int i = 0; i < FileNum; i++) {
				raf.seek(alreadyWrite);
				fis = new FileInputStream(srcDirectory + separatedFilesAndSize[i][0]);
				while ((len = fis.read(bt)) > 0) {
					raf.write(bt, 0, len);
				}
				fis.close();
				successCombinatedFileNamesLst.add(srcDirectory + separatedFilesAndSize[i][0]);
				alreadyWrite = alreadyWrite + Long.parseLong(separatedFilesAndSize[i][1]);
			}
			raf.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			try {
				if (raf != null)
					raf.close();
				if (fis != null)
					fis.close();
			}
			catch (IOException f) {
				f.printStackTrace();
			}
			successCombinatedFileNamesLst.clear();
			return false;
		}
		successCombinatedFileNames = successCombinatedFileNamesLst.toArray(new String[successCombinatedFileNamesLst.size()]);
		return true;
	}

	/**
	 * 删除已经被成功合并的各Part分割文件
	 * @return boolean 成功如否
	 */
	public boolean deletePartFiles(){
		if(successCombinatedFileNames == null || successCombinatedFileNames.length==0){
		   return true;
		}
		for(int i=0;i<successCombinatedFileNames.length;i++){
			String fileName = successCombinatedFileNames[i];
			File f = new File(fileName);
			if(f.exists()){
			   if(f.isFile()){
				  f.delete();
			   }
			}
		}
		return true;
	}
	
	public String[] getSuccessCombinatedFileNames(){
		return this.successCombinatedFileNames;
	}
	
	public static void main(String[] args) {
		FileCombinationUtil combination = new FileCombinationUtil();
		combination.setSrcDirectory("f:\\testtest\\");
		combination.setRealFileName("testtest.rar");
		if(combination.CombFile()){
		   combination.deletePartFiles();
		}
		System.exit(0);
	}
} 
