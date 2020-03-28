package com.shadow.coronatracker.model.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.shadow.coronatracker.model.CoronaCountryStats;
import com.shadow.coronatracker.model.CoronaStateStats;
import com.shadow.coronatracker.model.CoronaSummaryStats;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowth;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthCountry;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthFactor;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowth;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthCountry;

@Component
public class CoronaDataResponse {

	private CoronaSummaryStats coronaSummaryStats;
	private List<CoronaCountryStats> coronaCountriesStats;
	private List<CoronaStateStats> coronaStatesStats;
	private List<CoronaCaseGrowth> coronaCaseGrowthStats;
	private List<CoronaCaseGrowthCountry> coronaCaseGrowthCountriesStats;
	private List<CoronaCaseGrowthFactor> coronaCaseGrowthFactorStats;
	private List<CoronaDeathGrowth> coronaDeathGrowthStats;
	private List<CoronaDeathGrowthCountry> coronaDeathGrowthCountriesStats;

	public CoronaSummaryStats getCoronaSummaryStats() {
		return coronaSummaryStats;
	}

	public void setCoronaSummaryStats(CoronaSummaryStats coronaSummaryStats) {
		this.coronaSummaryStats = coronaSummaryStats;
	}

	public List<CoronaCountryStats> getCoronaCountriesStats() {
		return coronaCountriesStats;
	}

	public void setCoronaCountriesStats(List<CoronaCountryStats> coronaCountriesStats) {
		this.coronaCountriesStats = coronaCountriesStats;
	}

	public List<CoronaStateStats> getCoronaStatesStats() {
		return coronaStatesStats;
	}

	public void setCoronaStatesStats(List<CoronaStateStats> coronaStatesStats) {
		this.coronaStatesStats = coronaStatesStats;
	}

	public List<CoronaCaseGrowth> getCoronaCaseGrowthStats() {
		return coronaCaseGrowthStats;
	}

	public void setCoronaCaseGrowthStats(List<CoronaCaseGrowth> coronaCaseGrowthStats) {
		this.coronaCaseGrowthStats = coronaCaseGrowthStats;
	}

	public List<CoronaCaseGrowthCountry> getCoronaCaseGrowthCountriesStats() {
		return coronaCaseGrowthCountriesStats;
	}

	public void setCoronaCaseGrowthCountriesStats(List<CoronaCaseGrowthCountry> coronaCaseGrowthCountriesStats) {
		this.coronaCaseGrowthCountriesStats = coronaCaseGrowthCountriesStats;
	}

	public List<CoronaCaseGrowthFactor> getCoronaCaseGrowthFactorStats() {
		return coronaCaseGrowthFactorStats;
	}

	public void setCoronaCaseGrowthFactorStats(List<CoronaCaseGrowthFactor> coronaCaseGrowthFactorStats) {
		this.coronaCaseGrowthFactorStats = coronaCaseGrowthFactorStats;
	}

	public List<CoronaDeathGrowth> getCoronaDeathGrowthStats() {
		return coronaDeathGrowthStats;
	}

	public void setCoronaDeathGrowthStats(List<CoronaDeathGrowth> coronaDeathGrowthStats) {
		this.coronaDeathGrowthStats = coronaDeathGrowthStats;
	}

	public List<CoronaDeathGrowthCountry> getCoronaDeathGrowthCountriesStats() {
		return coronaDeathGrowthCountriesStats;
	}

	public void setCoronaDeathGrowthCountriesStats(List<CoronaDeathGrowthCountry> coronaDeathGrowthCountriesStats) {
		this.coronaDeathGrowthCountriesStats = coronaDeathGrowthCountriesStats;
	}

}
