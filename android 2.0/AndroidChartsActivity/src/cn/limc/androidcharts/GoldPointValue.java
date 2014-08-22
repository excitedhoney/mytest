package cn.limc.androidcharts;

public class GoldPointValue {
	
	private int position ;
	
	private double axisYvalue ;
	
	public GoldPointValue(int position,double axisYvalue){
		this.position  = position ;
		this.axisYvalue = axisYvalue ;
	}
	

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public double getAxisYvalue() {
		return axisYvalue;
	}

	public void setAxisYvalue(double axisYvalue) {
		this.axisYvalue = axisYvalue;
	}
	
	

}
