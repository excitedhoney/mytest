package cn.precious.metal.entity.response;

import java.util.List;

import cn.precious.metal.entity.Hangqing_Price;

public class OptionalPriceResponseContent {
	private int timestamp ;
	
	private int count ;
	
	private List<Hangqing_Price> results ;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Hangqing_Price> getResults() {
		return results;
	}

	public void setResults(List<Hangqing_Price> results) {
		this.results = results;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
