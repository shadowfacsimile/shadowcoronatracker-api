package com.shadow.coronatracker.model.response;

import java.util.List;

import com.shadow.coronatracker.model.CoronaCountryStats;
import com.shadow.coronatracker.model.CoronaStateStats;
import com.shadow.coronatracker.model.CoronaSummaryStats;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthCountryStats;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthFactorStats;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthStats;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthCountryStats;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthStats;

public class CoronaDataResponse {

	private CoronaSummaryStats coronaSummaryStats;
	private List<CoronaCountryStats> coronaCountryStats;
	private List<CoronaStateStats> coronaStateStats;
	private List<CoronaCaseGrowthStats> coronaCaseGrowthStats;
	private List<CoronaCaseGrowthCountryStats> coronaCaseGrowthCountryStats;
	private List<CoronaCaseGrowthFactorStats> coronaCaseGrowthFactorStats;
	private List<CoronaDeathGrowthStats> coronaDeathGrowthStats;
	private List<CoronaDeathGrowthCountryStats> coronaDeathGrowthCountryStats;

	public CoronaSummaryStats getCoronaSummaryStats() {
		return coronaSummaryStats;
	}

	public void setCoronaSummaryStats(CoronaSummaryStats coronaSummaryStats) {
		this.coronaSummaryStats = coronaSummaryStats;
	}

	public List<CoronaCountryStats> getCoronaCountryStats() {
		return coronaCountryStats;
	}

	public void setCoronaCountryStats(List<CoronaCountryStats> coronaCountryStats) {
		this.coronaCountryStats = coronaCountryStats;
	}

	public List<CoronaStateStats> getCoronaStateStats() {
		return coronaStateStats;
	}

	public void setCoronaStateStats(List<CoronaStateStats> coronaStateStats) {
		this.coronaStateStats = coronaStateStats;
	}

	public List<CoronaCaseGrowthStats> getCoronaCaseGrowthStats() {
		return coronaCaseGrowthStats;
	}

	public void setCoronaCaseGrowthStats(List<CoronaCaseGrowthStats> coronaCaseGrowthStats) {
		this.coronaCaseGrowthStats = coronaCaseGrowthStats;
	}

	public List<CoronaCaseGrowthCountryStats> getCoronaCaseGrowthCountryStats() {
		return coronaCaseGrowthCountryStats;
	}

	public void setCoronaCaseGrowthCountryStats(List<CoronaCaseGrowthCountryStats> coronaCaseGrowthCountryStats) {
		this.coronaCaseGrowthCountryStats = coronaCaseGrowthCountryStats;
	}

	public List<CoronaCaseGrowthFactorStats> getCoronaCaseGrowthFactorStats() {
		return coronaCaseGrowthFactorStats;
	}

	public void setCoronaCaseGrowthFactorStats(List<CoronaCaseGrowthFactorStats> coronaCaseGrowthFactorStats) {
		this.coronaCaseGrowthFactorStats = coronaCaseGrowthFactorStats;
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
