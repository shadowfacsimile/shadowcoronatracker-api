package com.shadow.coronatracker.model.deathgrowth;

import java.util.List;

public class CoronaDeathGrowthCountry {

	private String country;
	private List<CoronaDeathGrowth> growthStats;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<CoronaDeathGrowth> getGrowthStats() {
		return growthStats;
	}

	public void setGrowthStats(List<CoronaDeathGrowth> growthStats) {
		this.growthStats = growthStats;
	}

}
