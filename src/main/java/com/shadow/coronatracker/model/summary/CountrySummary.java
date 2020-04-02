package com.shadow.coronatracker.model.summary;

import com.shadow.coronatracker.model.Location;

public class CountrySummary {

	private Location location;
	private int cases;
	private int newCases;
	private int deaths;
	private int newDeaths;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cases;
		result = prime * result + deaths;
		result = prime * result + (firstCaseReported ? 1231 : 1237);
		result = prime * result + (firstDeathReported ? 1231 : 1237);
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		long temp;
		temp = Double.doubleToLongBits(mortalityRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + newCases;
		result = prime * result + newDeaths;
		result = prime * result + recoveries;
		temp = Double.doubleToLongBits(recoveryRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (statewiseDataExists ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountrySummary other = (CountrySummary) obj;
		if (cases != other.cases)
			return false;
		if (deaths != other.deaths)
			return false;
		if (firstCaseReported != other.firstCaseReported)
			return false;
		if (firstDeathReported != other.firstDeathReported)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (Double.doubleToLongBits(mortalityRate) != Double.doubleToLongBits(other.mortalityRate))
			return false;
		if (newCases != other.newCases)
			return false;
		if (newDeaths != other.newDeaths)
			return false;
		if (recoveries != other.recoveries)
			return false;
		if (Double.doubleToLongBits(recoveryRate) != Double.doubleToLongBits(other.recoveryRate))
			return false;
		if (statewiseDataExists != other.statewiseDataExists)
			return false;
		return true;
	}

}
