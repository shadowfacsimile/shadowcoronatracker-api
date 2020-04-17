package com.shadow.coronatracker.model.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.shadow.coronatracker.model.IndiaStats;
import com.shadow.coronatracker.model.casegrowth.CasesGrowth;
import com.shadow.coronatracker.model.casegrowth.CountryCasesGrowth;
import com.shadow.coronatracker.model.deathgrowth.CountryDeathsGrowth;
import com.shadow.coronatracker.model.deathgrowth.DeathsGrowth;
import com.shadow.coronatracker.model.summary.CountrySummary;
import com.shadow.coronatracker.model.summary.StateSummary;
import com.shadow.coronatracker.model.summary.Summary;

@Component
public class CoronaDataResponse {

	private Summary summaryStats;
	private List<CountrySummary> countrySummaryStats;
	private List<StateSummary> stateSummaryStats;
	private List<CasesGrowth> casesGrowthStats;
	private List<CountryCasesGrowth> countryCasesGrowthStats;
	private List<DeathsGrowth> deathsGrowthStats;
	private List<CountryDeathsGrowth> countryDeathsGrowthStats;
	private IndiaStats indiaStats;

	public Summary getSummaryStats() {
		return summaryStats;
	}

	public void setSummaryStats(Summary summaryStats) {
		this.summaryStats = summaryStats;
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

	public List<CasesGrowth> getCasesGrowthStats() {
		return casesGrowthStats;
	}

	public void setCasesGrowthStats(List<CasesGrowth> casesGrowthStats) {
		this.casesGrowthStats = casesGrowthStats;
	}

	public List<CountryCasesGrowth> getCountryCasesGrowthStats() {
		return countryCasesGrowthStats;
	}

	public void setCountryCasesGrowthStats(List<CountryCasesGrowth> countryCasesGrowthStats) {
		this.countryCasesGrowthStats = countryCasesGrowthStats;
	}

	public List<DeathsGrowth> getDeathsGrowthStats() {
		return deathsGrowthStats;
	}

	public void setDeathsGrowthStats(List<DeathsGrowth> deathsGrowthStats) {
		this.deathsGrowthStats = deathsGrowthStats;
	}

	public List<CountryDeathsGrowth> getCountryDeathsGrowthStats() {
		return countryDeathsGrowthStats;
	}

	public void setCountryDeathsGrowthStats(List<CountryDeathsGrowth> countryDeathsGrowthStats) {
		this.countryDeathsGrowthStats = countryDeathsGrowthStats;
	}

	public IndiaStats getIndiaStats() {
		return indiaStats;
	}

	public void setIndiaStats(IndiaStats indiaStats) {
		this.indiaStats = indiaStats;
	}

}
