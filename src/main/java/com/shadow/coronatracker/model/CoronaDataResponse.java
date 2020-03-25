package com.shadow.coronatracker.model;

import java.util.List;

public class CoronaDataResponse {

	private List<CoronaData> coronaStats;
	private CoronaSummaryStats coronaSummaryStats;
	private List<CoronaCaseGrowthStats> coronaCaseGrowthStats;
	private List<CoronaCaseGrowthCountryStats> coronaCaseGrowthCountryStats;
	private List<CoronaDeathGrowthStats> coronaDeathGrowthStats;
	private List<CoronaDeathGrowthCountryStats> coronaDeathGrowthCountryStats;
	private List<CoronaCaseGrowthFactorStats> coronaCaseGrowthFactorStats;

	public List<CoronaData> getCoronaStats() {
		return coronaStats;
	}

	public void setCoronaStats(List<CoronaData> coronaStats) {
		this.coronaStats = coronaStats;
	}

	public CoronaSummaryStats getCoronaSummaryStats() {
		return coronaSummaryStats;
	}

	public void setCoronaSummaryStats(CoronaSummaryStats coronaSummaryStats) {
		this.coronaSummaryStats = coronaSummaryStats;
	}

	public List<CoronaCaseGrowthStats> getCoronaCaseGrowthStats() {
		return coronaCaseGrowthStats;
	}

	public void setCoronaCaseGrowthStats(List<CoronaCaseGrowthStats> coronaCaseGrowthStats) {
		this.coronaCaseGrowthStats = coronaCaseGrowthStats;
	}

	public List<CoronaCaseGrowthFactorStats> getCoronaCaseGrowthFactorStats() {
		return coronaCaseGrowthFactorStats;
	}

	public void setCoronaCaseGrowthFactorStats(List<CoronaCaseGrowthFactorStats> coronaCaseGrowthFactorStats) {
		this.coronaCaseGrowthFactorStats = coronaCaseGrowthFactorStats;
	}

	public List<CoronaCaseGrowthCountryStats> getCoronaCaseGrowthCountryStats() {
		return coronaCaseGrowthCountryStats;
	}

	public void setCoronaCaseGrowthCountryStats(List<CoronaCaseGrowthCountryStats> coronaCaseGrowthCountryStats) {
		this.coronaCaseGrowthCountryStats = coronaCaseGrowthCountryStats;
	}

	public List<CoronaDeathGrowthStats> getCoronaDeathGrowthStats() {
		return coronaDeathGrowthStats;
	}

	public void setCoronaDeathGrowthStats(List<CoronaDeathGrowthStats> coronaDeathGrowthStats) {
		this.coronaDeathGrowthStats = coronaDeathGrowthStats;
	}

	public List<CoronaDeathGrowthCountryStats> getCoronaDeathGrowthCountryStats() {
		return coronaDeathGrowthCountryStats;
	}

	public void setCoronaDeathGrowthCountryStats(List<CoronaDeathGrowthCountryStats> coronaDeathGrowthCountryStats) {
		this.coronaDeathGrowthCountryStats = coronaDeathGrowthCountryStats;
	}

}
