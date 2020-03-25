package com.shadow.coronatracker.model.casegrowth;

import java.util.List;

public class CoronaCaseGrowthCountryStats {

	private String country;
	private List<CoronaCaseGrowthStats> growthStats;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<CoronaCaseGrowthStats> getGrowthStats() {
		return growthStats;
	}

	public void setGrowthStats(List<CoronaCaseGrowthStats> growthStats) {
		this.growthStats = growthStats;
	}

}
