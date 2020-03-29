package com.shadow.coronatracker.util.datacreators;

import java.util.List;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaCountryStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.CoronaSummaryStats;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaCasesSummaryDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {

		List<CoronaCountryStats> coronaDataList = coronaStatsCollection.getCoronaCountryStats();

		CoronaSummaryStats coronaSummaryStats = new CoronaSummaryStats();

		coronaSummaryStats
				.setTotalCases(coronaDataList.stream().collect(Collectors.summingInt(CoronaCountryStats::getCases)));
		coronaSummaryStats.setTotalCasesSinceYesterday(
				coronaDataList.stream().collect(Collectors.summingInt(CoronaCountryStats::getCasesSinceYesterday)));
		coronaSummaryStats
				.setTotalDeaths(coronaDataList.stream().collect(Collectors.summingInt(CoronaCountryStats::getDeaths)));
		coronaSummaryStats.setTotalDeathsSinceYesterday(
				coronaDataList.stream().collect(Collectors.summingInt(CoronaCountryStats::getDeathsSinceYesterday)));
		coronaSummaryStats.setTotalRecoveries(
				coronaDataList.stream().collect(Collectors.summingInt(CoronaCountryStats::getRecoveries)));
		coronaSummaryStats
				.setMortalityRate((double) coronaSummaryStats.getTotalDeaths() / coronaSummaryStats.getTotalCases());
		coronaSummaryStats
				.setRecoveryRate((double) coronaSummaryStats.getTotalRecoveries() / coronaSummaryStats.getTotalCases());
		coronaSummaryStats.setCountriesWithFirstCase(coronaDataList.stream().filter(stat -> stat.isFirstCaseReported())
				.map(stat -> stat.getLocation().getCountry()).collect(Collectors.toList()));
		coronaSummaryStats
				.setCountriesWithFirstDeath(coronaDataList.stream().filter(stat -> stat.isFirstDeathReported())
						.map(stat -> stat.getLocation().getCountry()).collect(Collectors.toList()));

		coronaDataResponse.setCoronaSummaryStats(coronaSummaryStats);
	}

}
