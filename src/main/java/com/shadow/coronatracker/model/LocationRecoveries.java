package com.shadow.coronatracker.model;

import java.util.Map;

public class LocationRecoveries {

	private Location location;
	private Map<String, Integer> recoveries;
	private String currDate;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, Integer> getRecoveries() {
		return recoveries;
	}

	public void setRecoveries(Map<String, Integer> recoveries) {
		this.recoveries = recoveries;
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

}
