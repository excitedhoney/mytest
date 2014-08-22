/** 
* 
 * Copyright (c) 1995-2012 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Wonders Group.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gnu.org
 */

package mobi.dreambox.frameowrk.core.util;

import java.io.File;

/**<p>
 * Title: w3studio_[子系统统名]_[模块名]
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author cavenshen
 * @version $Revision$ Nov 12, 2012
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class ImageRename {
	public static void main(String[] args) {
		File imgFolder = new File("/Users/cavenshen/Downloads/@4x");
		if(imgFolder.isDirectory()){
			for(File file : imgFolder.listFiles()){
				if(!file.isDirectory()){
					file.renameTo(new File(imgFolder.getAbsoluteFile()+File.separator+file.getName().replace("@4x", "@2x")));
				}
			}
		}
	}
}
