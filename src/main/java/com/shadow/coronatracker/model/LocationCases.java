package com.shadow.coronatracker.model;

import java.util.Map;

public class LocationCases {

	private Location location;
	private Map<String, Integer> cases;
	private Map<String, Integer> newCases;
	private String currDate;
	private boolean isFirstCase;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Map<String, Integer> getCases() {
		return cases;
	}

	public void setCases(Map<String, Integer> cases) {
		this.cases = cases;
	}

	public Map<String, Integer> getNewCases() {
		return newCases;
	}

	public void setNewCases(Map<String, Integer> newCases) {
		this.newCases = newCases;
	}

	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public boolean isFirstCase() {
		return isFirstCase;
	}

	public void setFirstCase(boolean isFirstCase) {
		this.isFirstCase = isFirstCase;
	}

}
