package cn.precious.metal.common;

import cn.precious.metal.exception.AClientException;


public class ServiceException extends AClientException {
	private static final long serialVersionUID = 1L;
	
	
	public ServiceException(String errCode, String errMsg) {
		super(errCode, errMsg);
		// TODO Auto-generated constructor stub
	}
}
