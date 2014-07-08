package com.venus.carpapa.vo;

public class JianCheItemVo {
	private String name;
	private String defaul = "正常";

	public JianCheItemVo(String name, String defaul) {
		super();
		this.name = name;
		this.defaul = defaul;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaul() {
		return defaul;
	}

	public void setDefaul(String defaul) {
		this.defaul = defaul;
	}

}
