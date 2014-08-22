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

package mobi.dreambox.frameowrk.core.exception;

/**
 * <p>
 * Title: w3studio_[子系统统名]_[模块名]
 * </p>
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author caven
 * @version $Revision$ May 17, 2012
 * @author (lastest modification by $Author$)
 * @since 20100620
 */
public class DboxFrameworkException extends Exception {
	private static final long serialVersionUID = 1L;

	protected String errCode;

	protected String errMsg;

	public DboxFrameworkException(String errCode, String errMsg, Throwable cause) {
		super(errMsg, cause);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public DboxFrameworkException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	/**
	 * 
	 * @param cause
	 */
	public DboxFrameworkException(Throwable cause) {
		super(cause);
	}

	public DboxFrameworkException(String message,Throwable cause) {
		super(message, cause);
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
