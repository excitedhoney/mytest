package cn.precious.metal.entity;

import com.j256.ormlite.field.DatabaseField;

public class Optional {
	
	
	@DatabaseField(generatedId = true)
	private int localId ;
	@DatabaseField
	private int drag ;
	@DatabaseField
	private int top ;
	@DatabaseField
	private boolean isOptional ; //是否是自选股
	@DatabaseField
	private boolean isHostory ; //
	@DatabaseField
	private String type ; //  sg  tg
	
	
	
	@DatabaseField
    private  String treaty ;	//代码
	@DatabaseField
	private  String title ;	   // 名称
	@DatabaseField
	private  String date ;	
	@DatabaseField
	private  String time ;	
	@DatabaseField
	private  String opening ;	
	@DatabaseField
	private  String highest ;
	@DatabaseField
	private  String lowest ;
	@DatabaseField
	private  String newest ;	
	@DatabaseField
	private  String buyone ;
	@DatabaseField
	private  String sellone ;	 
	@DatabaseField
	private  String buyquantity ;
	@DatabaseField
	private  String sellquantity ;	
	@DatabaseField
	private  String volume ;
	@DatabaseField
	private  String price ;	
	@DatabaseField
	private  String position ;	
	@DatabaseField
	private  String lastsettle ;
	@DatabaseField	 
	private  String closed ;
	@DatabaseField	 
	private  String add_time ;
	
	@DatabaseField(defaultValue = "0.1f")	 
	private  String ratio ;
	
	public int getLocalId() {
		return localId;
	}
	public void setLocalId(int localId) {
		this.localId = localId;
	}
	public int getDrag() {
		return drag;
	}
	public void setDrag(int drag) {
		this.drag = drag;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	public boolean isOptional() {
		return isOptional;
	}
	public void setOptional(boolean isOptional) {
		this.isOptional = isOptional;
	}
	public boolean isHostory() {
		return isHostory;
	}
	public void setHostory(boolean isHostory) {
		this.isHostory = isHostory;
	}
	public String getTreaty() {
		return treaty;
	}
	public void setTreaty(String treaty) {
		this.treaty = treaty;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOpening() {
		return opening;
	}
	public void setOpening(String opening) {
		this.opening = opening;
	}
	public String getHighest() {
		return highest;
	}
	public void setHighest(String highest) {
		this.highest = highest;
	}
	public String getLowest() {
		return lowest;
	}
	public void setLowest(String lowest) {
		this.lowest = lowest;
	}
	public String getNewest() {
		return newest;
	}
	public void setNewest(String newest) {
		this.newest = newest;
	}
	public String getBuyone() {
		return buyone;
	}
	public void setBuyone(String buyone) {
		this.buyone = buyone;
	}
	public String getSellone() {
		return sellone;
	}
	public void setSellone(String sellone) {
		this.sellone = sellone;
	}
	public String getBuyquantity() {
		return buyquantity;
	}
	public void setBuyquantity(String buyquantity) {
		this.buyquantity = buyquantity;
	}
	public String getSellquantity() {
		return sellquantity;
	}
	public void setSellquantity(String sellquantity) {
		this.sellquantity = sellquantity;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getLastsettle() {
		return lastsettle;
	}
	public void setLastsettle(String lastsettle) {
		this.lastsettle = lastsettle;
	}
	public String getClosed() {
		return closed;
	}
	public void setClosed(String closed) {
		this.closed = closed;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}
	public String getRatio() {
		if("10%".equals(ratio)) {
			return "0.1" ;
		}else if("8%".equals(ratio)) {
			return "0.08" ;
				
		}else{
			return ratio ;
		}
	}
	public void setRatio(String ratio) {
		this.ratio = ratio;
	}
	
}
