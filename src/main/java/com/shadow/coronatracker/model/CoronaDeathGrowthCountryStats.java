package com.shadow.coronatracker.model;

import java.util.List;

public class CoronaDeathGrowthCountryStats {

	private String country;
	private List<CoronaDeathGrowthStats> growthStats;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<CoronaDeathGrowthStats> getGrowthStats() {
		return growthStats;
	}

	public void setGrowthStats(List<CoronaDeathGrowthStats> growthStats) {
		this.growthStats = growthStats;
	}

}
