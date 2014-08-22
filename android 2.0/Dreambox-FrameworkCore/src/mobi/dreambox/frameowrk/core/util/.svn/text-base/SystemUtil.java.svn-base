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

/**
 * <p>
 * Title: cuteinfo_[子系统统名]_[模块名]
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author Administrator
 * @version $Revision$ 2010-12-2
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public final class SystemUtil {

	private static final String CI_DEBUG_MODE = "CI_DEBUG_MODE";

	public static final String DEBUG_SUFFIX = "_DEBUG";

	public static final String LABEL_SUFFIX_SIGN = "_LABEL";

	/**
	 * <p>
	 * Description:[方法功能中文描述]
	 * </p>
	 * 
	 * @return
	 */
	public static boolean isDebugMode() {
		return System.getProperty(CI_DEBUG_MODE) != null && System.getProperty(CI_DEBUG_MODE).equals("1");
	}

	/**
	 * <p>
	 * Description:[方法功能中文描述]
	 * </p>
	 */
	public static void switchToDebugMode() {
		System.setProperty(CI_DEBUG_MODE, "1");
	}

	/**
	 * <p>
	 * Description:[方法功能中文描述]
	 * </p>
	 */
	public static void switchToRunMode() {
		System.setProperty(CI_DEBUG_MODE, "0");
	}

}
