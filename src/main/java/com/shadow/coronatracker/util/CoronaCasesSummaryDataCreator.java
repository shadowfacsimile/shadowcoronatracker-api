package com.shadow.coronatracker.util;

import java.util.List;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaData;
import com.shadow.coronatracker.model.CoronaDataResponse;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.CoronaSummaryStats;

public class CoronaCasesSummaryDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaData> coronaDataList = coronaStatsCollection.getCoronaDataList();

		CoronaSummaryStats coronaSummaryStats = new CoronaSummaryStats();

		coronaSummaryStats.setTotalCases(coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getCases)));
		coronaSummaryStats.setTotalCasesSinceYesterday(
				coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getCasesSinceYesterday)));
		coronaSummaryStats
				.setTotalDeaths(coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getDeaths)));
		coronaSummaryStats.setTotalDeathsSinceYesterday(
				coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getDeathsSinceYesterday)));
		coronaSummaryStats
				.setTotalRecoveries(coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getRecoveries)));
		coronaSummaryStats
				.setMortalityRate((double) coronaSummaryStats.getTotalDeaths() / coronaSummaryStats.getTotalCases());
		coronaSummaryStats
				.setRecoveryRate((double) coronaSummaryStats.getTotalRecoveries() / coronaSummaryStats.getTotalCases());
		coronaSummaryStats.setCountriesWithFirstCase(coronaDataList.stream().filter(stat -> stat.isFirstCaseReported())
				.filter(stat -> !CoronaTrackerUtil.filterCountries.contains(stat.getCountry()))
				.map(stat -> stat.getCountry()).collect(Collectors.toList()));
		coronaSummaryStats
				.setCountriesWithFirstDeath(coronaDataList.stream().filter(stat -> stat.isFirstDeathReported())
						.filter(stat -> !CoronaTrackerUtil.filterCountries.contains(stat.getCountry()))
						.map(stat -> stat.getCountry()).collect(Collectors.toList()));

		coronaDataResponse.setCoronaSummaryStats(coronaSummaryStats);

	}

}
