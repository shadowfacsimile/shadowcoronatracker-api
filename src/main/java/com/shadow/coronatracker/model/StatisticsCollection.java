package com.shadow.coronatracker.model;

import java.util.List;
import java.util.Map;

import com.shadow.coronatracker.model.summary.CountrySummary;
import com.shadow.coronatracker.model.summary.StateSummary;

public class StatisticsCollection {

	private List<LocationCases> locationCasesStats;
	private List<LocationDeaths> locationDeathsStats;
	private List<LocationRecoveries> locationRecoveriesStats;
	private List<CountrySummary> countrySummaryStats;
	private List<StateSummary> stateSummaryStats;
	private Map<String, Integer> totalCasesGrowthMap;
	private Map<String, Double> totalCasesGrowthFactorMap;
	private Map<String, Integer> totalNewCasesGrowthMap;
	private Map<String, Integer> totalDeathsGrowthMap;
	private Map<String, Integer> totalNewDeathsGrowthMap;
	private IndiaStats indiaStats;

	public List<LocationCases> getLocationCasesStats() {
		return locationCasesStats;
	}

	public void setLocationCasesStats(List<LocationCases> locationCasesStats) {
		this.locationCasesStats = locationCasesStats;
	}

	public List<LocationDeaths> getLocationDeathsStats() {
		return locationDeathsStats;
	}

	public void setLocationDeathsStats(List<LocationDeaths> locationDeathsStats) {
		this.locationDeathsStats = locationDeathsStats;
	}

	public List<LocationRecoveries> getLocationRecoveriesStats() {
		return locationRecoveriesStats;
	}

	public void setLocationRecoveriesStats(List<LocationRecoveries> locationRecoveriesStats) {
		this.locationRecoveriesStats = locationRecoveriesStats;
	}

	public List<CountrySummary> getCountrySummaryStats() {
		return countrySummaryStats;
	}

	public void setCountrySummaryStats(List<CountrySummary> countrySummaryStats) {
		this.countrySummaryStats = countrySummaryStats;
	}

	public List<StateSummary> getStateSummaryStats() {
		return stateSummaryStats;
	}

	public void setStateSummaryStats(List<StateSummary> stateSummaryStats) {
		this.stateSummaryStats = stateSummaryStats;
	}

	public Map<String, Integer> getTotalCasesGrowthMap() {
		return totalCasesGrowthMap;
	}

	public void setTotalCasesGrowthMap(Map<String, Integer> totalCasesGrowthMap) {
		this.totalCasesGrowthMap = totalCasesGrowthMap;
	}

	public Map<String, Double> getTotalCasesGrowthFactorMap() {
		return totalCasesGrowthFactorMap;
	}

	public void setTotalCasesGrowthFactorMap(Map<String, Double> totalCasesGrowthFactorMap) {
		this.totalCasesGrowthFactorMap = totalCasesGrowthFactorMap;
	}

	public Map<String, Integer> getTotalNewCasesGrowthMap() {
		return totalNewCasesGrowthMap;
	}

	public void setTotalNewCasesGrowthMap(Map<String, Integer> totalNewCasesGrowthMap) {
		this.totalNewCasesGrowthMap = totalNewCasesGrowthMap;
	}

	public Map<String, Integer> getTotalDeathsGrowthMap() {
		return totalDeathsGrowthMap;
	}

	public void setTotalDeathsGrowthMap(Map<String, Integer> totalDeathsGrowthMap) {
		this.totalDeathsGrowthMap = totalDeathsGrowthMap;
	}

	public Map<String, Integer> getTotalNewDeathsGrowthMap() {
		return totalNewDeathsGrowthMap;
	}

	public void setTotalNewDeathsGrowthMap(Map<String, Integer> totalNewDeathsGrowthMap) {
		this.totalNewDeathsGrowthMap = totalNewDeathsGrowthMap;
	}

	public IndiaStats getIndiaStats() {
		return indiaStats;
	}

	public void setIndiaStats(IndiaStats indiaStats) {
		this.indiaStats = indiaStats;
	}
}
