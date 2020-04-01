package com.shadow.coronatracker.model;

import java.util.Map;

public class LocationDeaths {

	private Location location;
	private Map<String, Integer> deaths;
	private Map<String, Integer> newDeaths;
	private String currDate;
	private boolean deathsReported;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, Integer> getDeaths() {
		return deaths;
	}

	public void setDeaths(Map<String, Integer> deaths) {
		this.deaths = deaths;
	}

	public Map<String, Integer> getNewDeaths() {
		return newDeaths;
	}

	public void setNewDeaths(Map<String, Integer> newDeaths) {
		this.newDeaths = newDeaths;
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public boolean isDeathsReported() {
		return deathsReported;
	}

	public void setDeathsReported(boolean deathsReported) {
		this.deathsReported = deathsReported;
	}

}
