package com.venus.carpapa.vo;

public class CarSellDtoVo {
	private int carId;
	private String carSellCoding;
	private String tel;
	private String address;
	private int state;
	private int checkState;
	String licenseNumber;

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public String getCarSellCoding() {
		return carSellCoding;
	}

	public void setCarSellCoding(String carSellCoding) {
		this.carSellCoding = carSellCoding;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCheckState() {
		return checkState;
	}

	public void setCheckState(int checkState) {
		this.checkState = checkState;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

}
