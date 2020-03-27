package com.shadow.coronatracker.model;

public class CoronaCountryStats {

	private Location location;
	private int cases;
	private int casesSinceYesterday;
	private int deaths;
	private int deathsSinceYesterday;
	private double mortalityRate;
	private int recoveries;
	private double recoveryRate;
	private boolean firstCaseReported;
	private boolean firstDeathReported;
	private boolean statewiseDataExists;

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

	public int getCasesSinceYesterday() {
		return casesSinceYesterday;
	}

	public void setCasesSinceYesterday(int casesSinceYesterday) {
		this.casesSinceYesterday = casesSinceYesterday;
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

	public boolean isFirstCaseReported() {
		return firstCaseReported;
	}

	public void setFirstCaseReported(boolean firstCaseReported) {
		this.firstCaseReported = firstCaseReported;
	}

	public boolean isFirstDeathReported() {
		return firstDeathReported;
	}

	public void setFirstDeathReported(boolean firstDeathReported) {
		this.firstDeathReported = firstDeathReported;
	}

	public boolean isStatewiseDataExists() {
		return statewiseDataExists;
	}

	public void setStatewiseDataExists(boolean statewiseDataExists) {
		this.statewiseDataExists = statewiseDataExists;
	}

}
