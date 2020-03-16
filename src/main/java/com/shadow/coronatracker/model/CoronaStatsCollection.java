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
	private Map<Date, Double> coronaCasesGrowthFactors = new LinkedHashMap<>();

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

	public Map<Date, Double> getCoronaCasesGrowthFactors() {
		return coronaCasesGrowthFactors;
	}

	public void setCoronaCasesGrowthFactors(Map<Date, Double> coronaCasesGrowthFactors) {
		this.coronaCasesGrowthFactors = coronaCasesGrowthFactors;
	}

}
