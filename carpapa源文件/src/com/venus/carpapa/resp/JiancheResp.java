package com.venus.carpapa.resp;

import java.util.List;

import com.venus.carpapa.vo.CjVo;

public class JiancheResp {
	private int code;
	private String msg="";
	private 	List<CjVo> data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<CjVo> getData() {
		return data;
	}
	public void setData(List<CjVo> data) {
		this.data = data;
	}
	
}
