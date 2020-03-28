package com.shadow.coronatracker.model.casegrowth;

import java.util.List;

public class CoronaCaseGrowthCountry {

	private String country;
	private List<CoronaCaseGrowth> growthStats;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<CoronaCaseGrowth> getGrowthStats() {
		return growthStats;
	}

	public void setGrowthStats(List<CoronaCaseGrowth> growthStats) {
		this.growthStats = growthStats;
	}

}
