package com.shadow.coronatracker.model.summary;

import java.util.List;

public class Summary {

	private int totalCases;
	private int totalNewCases;
	private int totalDeaths;
	private int totalNewDeaths;
	private int totalRecoveries;
	private double mortalityRate;
	private double recoveryRate;
	private List<String> countriesWithFirstCase;
	private List<String> countriesWithFirstDeath;

	public int getTotalCases() {
		return totalCases;
	}

	public void setTotalCases(int totalCases) {
		this.totalCases = totalCases;
	}

	public int getTotalNewCases() {
		return totalNewCases;
	}

	public void setTotalNewCases(int totalNewCases) {
		this.totalNewCases = totalNewCases;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(int totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public int getTotalNewDeaths() {
		return totalNewDeaths;
	}

	public void setTotalNewDeaths(int totalNewDeaths) {
		this.totalNewDeaths = totalNewDeaths;
	}

	public int getTotalRecoveries() {
		return totalRecoveries;
	}

	public void setTotalRecoveries(int totalRecoveries) {
		this.totalRecoveries = totalRecoveries;
	}

	public double getMortalityRate() {
		return mortalityRate;
	}

	public void setMortalityRate(double mortalityRate) {
		this.mortalityRate = mortalityRate;
	}

	public double getRecoveryRate() {
		return recoveryRate;
	}

	public void setRecoveryRate(double recoveryRate) {
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
