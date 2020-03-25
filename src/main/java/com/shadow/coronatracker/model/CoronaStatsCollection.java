package com.shadow.coronatracker.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoronaStatsCollection {

	private List<CoronaCasesStats> coronaCasesStats = new ArrayList<>();
	private List<CoronaDeathsStats> coronaDeathsStats = new ArrayList<>();
	private List<CoronaRecoveriesStats> coronaRecoveriesStats = new ArrayList<>();
	private List<CoronaData> coronaDataList = new ArrayList<>();
	private Map<Date, Double> coronaCasesGrowthFactors = new LinkedHashMap<>();
	private Map<Date, Integer> coronaCasesGrowth = new LinkedHashMap<>();
	private Map<String, List<CoronaCaseGrowthStats>> coronaCasesGrowthCountry = new LinkedHashMap<>();
	private Map<Date, Integer> coronaDeathsGrowth = new LinkedHashMap<>();
	private Map<String, List<CoronaDeathGrowthStats>> coronaDeathsGrowthCountry = new LinkedHashMap<>();

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

	public List<CoronaData> getCoronaDataList() {
		return coronaDataList;
	}

	public void setCoronaDataList(List<CoronaData> coronaDataList) {
		this.coronaDataList = coronaDataList;
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

}
