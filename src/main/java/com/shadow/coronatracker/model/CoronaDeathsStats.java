package com.shadow.coronatracker.model;

public class CoronaDeathsStats {

	private Location location;
	private int deaths;
	private int deathsSinceYesterday;
	private boolean firstDeathReported;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getDeathsSinceYesterday() {
		return deathsSinceYesterday;
	}

	public void setDeathsSinceYesterday(int deathsSinceYesterday) {
		this.deathsSinceYesterday = deathsSinceYesterday;
	}

	public boolean isFirstDeathReported() {
		return firstDeathReported;
	}

	public void setFirstDeathReported(boolean firstDeathReported) {
		this.firstDeathReported = firstDeathReported;
	}

}
