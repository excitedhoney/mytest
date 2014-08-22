package cn.limc;

public class KLineEntity {
	//交易时间,市场类型,商品代码,昨结,昨收,现价,今开,最高,最低,成交量,成交额,持仓量,买价,卖价,买量,卖量
	private String date ;
	
	private String marketType ;
	
	private String symbol ;
	
	private double zuoJie   ;
	
	private double zuoShou   ;
	
	private double currentPrice   ;
	
	private double open   ;
	
	private double high   ;
	
	private double low   ;
	
	private double volume   ;
	
	private double turnover   ; //成交额
	
	private double inventory   ; 
	
	private double buyPrice   ; 
	
	private double sellPrice   ;
	
	private double buyVolume   ; 
	
	private double sellVolume   ;
	
	
	private double normValue ;
	
	/**
	 * 
	 * @param date
	 * @param zuoShou
	 * @param open
	 * @param high
	 * @param low
	 * @param currentPrice
	 * @param volume
	 */
	public KLineEntity(String date,double zuoShou,double open,double high,double low,double currentPrice,double volume ){
		this.date = date ;
		this.zuoShou = zuoShou ;
		this.open = open ;
		this.high = high ;
		this.low = low ;
		this.currentPrice = currentPrice ;
		this.volume = volume ;
	}
	
	
	public KLineEntity(double normValue){
		this.normValue = normValue ;
	}
	
	
	public KLineEntity(double high,double low){
		this.high = high ;
		this.low = low; 
	}
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getZuoJie() {
		return zuoJie;
	}

	public void setZuoJie(double zuoJie) {
		this.zuoJie = zuoJie;
	}

	public double getZuoShou() {
		return zuoShou;
	}

	public void setZuoShou(double zuoShou) {
		this.zuoShou = zuoShou;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getTurnover() {
		return turnover;
	}

	public void setTurnover(double turnover) {
		this.turnover = turnover;
	}

	public double getInventory() {
		return inventory;
	}

	public void setInventory(double inventory) {
		this.inventory = inventory;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public double getBuyVolume() {
		return buyVolume;
	}

	public void setBuyVolume(double buyVolume) {
		this.buyVolume = buyVolume;
	}

	public double getSellVolume() {
		return sellVolume;
	}

	public void setSellVolume(double sellVolume) {
		this.sellVolume = sellVolume;
	}


	public double getNormValue() {
		return normValue;
	}


	public void setNormValue(double normValue) {
		this.normValue = normValue;
	}


	
}
