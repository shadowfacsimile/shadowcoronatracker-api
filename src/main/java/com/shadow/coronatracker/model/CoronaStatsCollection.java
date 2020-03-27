package com.shadow.coronatracker.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthStats;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthStats;

public class CoronaStatsCollection {

	private List<CoronaCasesStats> coronaCasesStats;
	private List<CoronaDeathsStats> coronaDeathsStats;
	private List<CoronaRecoveriesStats> coronaRecoveriesStats;
	private List<CoronaCountryStats> coronaCountryStats;
	private List<CoronaStateStats> coronaStateStats;
	private Map<Date, Double> coronaCasesGrowthFactors;
	private Map<Date, Integer> coronaCasesGrowth;
	private Map<String, List<CoronaCaseGrowthStats>> coronaCasesGrowthCountry;
	private Map<Date, Integer> coronaDeathsGrowth;
	private Map<String, List<CoronaDeathGrowthStats>> coronaDeathsGrowthCountry;

	public List<CoronaCasesStats> getCoronaCasesStats() {
		return coronaCasesStats;
	}

	public void setCoronaCasesStats(List<CoronaCasesStats> coronaCasesStats) {
		this.coronaCasesStats = coronaCasesStats;
	}

	public List<CoronaDeathsStats> getCoronaDeathsStats() {
		return coronaDeathsStats;
	}

	public void setCoronaDeathsStats(List<CoronaDeathsStats> coronaDeathsStats) {
		this.coronaDeathsStats = coronaDeathsStats;
	}

	public List<CoronaRecoveriesStats> getCoronaRecoveriesStats() {
		return coronaRecoveriesStats;
	}

	public void setCoronaRecoveriesStats(List<CoronaRecoveriesStats> coronaRecoveriesStats) {
		this.coronaRecoveriesStats = coronaRecoveriesStats;
	}

	public List<CoronaCountryStats> getCoronaCountryStats() {
		return coronaCountryStats;
	}

	public void setCoronaCountryStats(List<CoronaCountryStats> coronaCountryStats) {
		this.coronaCountryStats = coronaCountryStats;
	}

	public Map<Date, Double> getCoronaCasesGrowthFactors() {
		return coronaCasesGrowthFactors;
	}

	public void setCoronaCasesGrowthFactors(Map<Date, Double> coronaCasesGrowthFactors) {
		this.coronaCasesGrowthFactors = coronaCasesGrowthFactors;
	}

	public Map<Date, Integer> getCoronaCasesGrowth() {
		return coronaCasesGrowth;
	}

	public void setCoronaCasesGrowth(Map<Date, Integer> coronaCasesGrowth) {
		this.coronaCasesGrowth = coronaCasesGrowth;
	}

	public Map<String, List<CoronaCaseGrowthStats>> getCoronaCasesGrowthCountry() {
		return coronaCasesGrowthCountry;
	}

	public void setCoronaCasesGrowthCountry(Map<String, List<CoronaCaseGrowthStats>> coronaCasesGrowthCountry) {
		this.coronaCasesGrowthCountry = coronaCasesGrowthCountry;
	}

	public Map<Date, Integer> getCoronaDeathsGrowth() {
		return coronaDeathsGrowth;
	}

	public void setCoronaDeathsGrowth(Map<Date, Integer> coronaDeathsGrowth) {
		this.coronaDeathsGrowth = coronaDeathsGrowth;
	}

	public Map<String, List<CoronaDeathGrowthStats>> getCoronaDeathsGrowthCountry() {
		return coronaDeathsGrowthCountry;
	}

	public void setCoronaDeathsGrowthCountry(Map<String, List<CoronaDeathGrowthStats>> coronaDeathsGrowthCountry) {
		this.coronaDeathsGrowthCountry = coronaDeathsGrowthCountry;
	}

	public List<CoronaStateStats> getCoronaStateStats() {
		return coronaStateStats;
	}

	public void setCoronaStateStats(List<CoronaStateStats> coronaStateStats) {
		this.coronaStateStats = coronaStateStats;
	}

}
