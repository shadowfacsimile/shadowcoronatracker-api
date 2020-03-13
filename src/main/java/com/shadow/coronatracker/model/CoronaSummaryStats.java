package com.shadow.coronatracker.model;

import java.util.List;

public class CoronaSummaryStats {

	private int totalCases;
	private int totalCasesSinceYesterday;
	private int totalDeaths;
	private int totalDeathsSinceYesterday;
	private int totalRecoveries;
	private String mortalityRate;
	private String recoveryRate;
	private List<String> countriesWithFirstCase;
	private List<String> countriesWithFirstDeath;

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

	public String getRecoveryRate() {
		return recoveryRate;
	}

	public void setRecoveryRate(String recoveryRate) {
		this.recoveryRate = recoveryRate;
	}

	public List<String> getCountriesWithFirstCase() {
		return countriesWithFirstCase;
	}

	public void setCountriesWithFirstCase(List<String> countriesWithFirstCase) {
		this.countriesWithFirstCase = countriesWithFirstCase;
	}

	public List<String> getCountriesWithFirstDeath() {
		return countriesWithFirstDeath;
	}

	public void setCountriesWithFirstDeath(List<String> countriesWithFirstDeath) {
		this.countriesWithFirstDeath = countriesWithFirstDeath;
	}

}
