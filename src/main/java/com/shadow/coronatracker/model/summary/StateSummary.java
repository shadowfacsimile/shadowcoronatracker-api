package com.shadow.coronatracker.model.summary;

import com.shadow.coronatracker.model.Location;

public class StateSummary {

	private Location location;
	private int cases;
	private int newCases;
	private int deaths;
	private int newDeaths;
	private double mortalityRate;
	private int recoveries;
	private double recoveryRate;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getCases() {
		return cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}

	public int getNewCases() {
		return newCases;
	}

	public void setNewCases(int newCases) {
		this.newCases = newCases;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getNewDeaths() {
		return newDeaths;
	}

	public void setNewDeaths(int newDeaths) {
		this.newDeaths = newDeaths;
	}

	public double getMortalityRate() {
		return mortalityRate;
	}

	public void setMortalityRate(double mortalityRate) {
		this.mortalityRate = mortalityRate;
	}

	public int getRecoveries() {
		return recoveries;
	}

	public void setRecoveries(int recoveries) {
		this.recoveries = recoveries;
	}

	public double getRecoveryRate() {
		return recoveryRate;
	}

	public void setRecoveryRate(double recoveryRate) {
		this.recoveryRate = recoveryRate;
	}

}
