package cn.precious.metal.exception;

import mobi.dreambox.frameowrk.core.exception.DboxFrameworkException;
/**
 * 客户端的异常 基类
 * @author mac
 *
 */
public class AClientException extends DboxFrameworkException {
	private static final long serialVersionUID = 1L;
	
	protected AClientException(String errCode, String errMsg) {
		super(errCode, errMsg);
	}
	
	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}
}
