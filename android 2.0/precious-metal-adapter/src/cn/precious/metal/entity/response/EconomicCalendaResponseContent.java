package cn.precious.metal.entity.response;

import java.util.List;

import cn.precious.metal.entity.EconomicCalenda;

public class EconomicCalendaResponseContent {
private int count ;
	
	private List<EconomicCalenda> results ;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<EconomicCalenda> getResults() {
		return results;
	}

	public void setResults(List<EconomicCalenda> results) {
		this.results = results;
	}
	
	
	
}
