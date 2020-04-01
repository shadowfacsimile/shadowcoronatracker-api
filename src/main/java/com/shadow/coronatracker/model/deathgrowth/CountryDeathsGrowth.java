package com.shadow.coronatracker.model.deathgrowth;

import java.util.List;

public class CountryDeathsGrowth {

	private String country;
	private List<DeathsGrowth> deathsGrowths;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<DeathsGrowth> getDeathsGrowths() {
		return deathsGrowths;
	}

	public void setDeathsGrowths(List<DeathsGrowth> deathsGrowths) {
		this.deathsGrowths = deathsGrowths;
	}

}
