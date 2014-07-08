package com.venus.carpapa.resp;

import java.util.List;

import com.venus.carpapa.vo.CarSellDtoVo;

public class CarOrderResp {
	private int sum;
	private List<CarSellDtoVo> carSellDtoList;

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public List<CarSellDtoVo> getCarSellDtoList() {
		return carSellDtoList;
	}

	public void setCarSellDtoList(List<CarSellDtoVo> carSellDtoList) {
		this.carSellDtoList = carSellDtoList;
	}

}
