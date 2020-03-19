package com.shadow.coronatracker.model;

import java.util.List;

public class CoronaDataResponse {

	private List<CoronaData> coronaStats;
	private CoronaSummaryStats coronaSummaryStats;
	private List<CoronaCaseGrowthStats> coronaCaseGrowthStats;
	private List<CoronaCaseGrowthCountryStats> coronaCaseGrowthCountryStats;
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

}
