package com.shadow.coronatracker.model;

public class CoronaSummaryStats {

	private int totalCases;
	private int totalCasesSinceYesterday;
	private int totalDeaths;
	private int totalDeathsSinceYesterday;
	private int totalRecoveries;
	private String mortalityRate;

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public int getTotalCasesSinceYesterday() {
		return totalCasesSinceYesterday;
	}

	public void setTotalCasesSinceYesterday(int totalCasesSinceYesterday) {
		this.totalCasesSinceYesterday = totalCasesSinceYesterday;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public int getTotalDeathsSinceYesterday() {
		return totalDeathsSinceYesterday;
	}

	public void setTotalDeathsSinceYesterday(int totalDeathsSinceYesterday) {
		this.totalDeathsSinceYesterday = totalDeathsSinceYesterday;
	}

	public int getTotalRecoveries() {
		return totalRecoveries;
	}

	public void setTotalRecoveries(int totalRecoveries) {
		this.totalRecoveries = totalRecoveries;
	}

	public String getMortalityRate() {
		return mortalityRate;
	}

	public void setMortalityRate(String mortalityRate) {
		this.mortalityRate = mortalityRate;
	}

	@Override
	public String toString() {
		return "CoronaSummaryStats [totalCases=" + totalCases + ", totalCasesSinceYesterday=" + totalCasesSinceYesterday
				+ ", totalDeaths=" + totalDeaths + ", totalDeathsSinceYesterday=" + totalDeathsSinceYesterday
				+ ", totalRecoveries=" + totalRecoveries + ", mortalityRate=" + mortalityRate + "]";
	}

}
