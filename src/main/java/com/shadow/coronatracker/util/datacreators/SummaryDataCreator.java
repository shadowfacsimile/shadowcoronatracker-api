package com.shadow.coronatracker.util.datacreators;

import java.util.stream.Collectors;

import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.LocationDeaths;
import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.response.CoronaStatsResponse;
import com.shadow.coronatracker.model.summary.Summary;

public class SummaryDataCreator implements DataCreator {

	@Override
	public void create(StatsCollection statsCollection, CoronaStatsResponse coronaDataResponse) {

		Summary summary = new Summary();

		summary.setTotalCases(statsCollection.getLocationCasesStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getCases().get(stat.getCurrDate()))));
		summary.setTotalNewCases(statsCollection.getLocationCasesStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getNewCases().get(stat.getCurrDate()))));
		summary.setTotalDeaths(statsCollection.getLocationDeathsStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getDeaths().get(stat.getCurrDate()))));
		summary.setTotalNewDeaths(statsCollection.getLocationDeathsStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getNewDeaths().get(stat.getCurrDate()))));
		summary.setTotalRecoveries(statsCollection.getLocationRecoveriesStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getRecoveries().get(stat.getCurrDate()))));
		summary.setMortalityRate(
				summary.getTotalCases() == 0 ? 0 : (double) summary.getTotalDeaths() / summary.getTotalCases());
		summary.setRecoveryRate(
				summary.getTotalCases() == 0 ? 0 : (double) summary.getTotalRecoveries() / summary.getTotalCases());
		summary.setCountriesWithFirstCase(
				statsCollection.getLocationCasesStats().stream().filter(LocationCases::isCasesReported)
						.map(stat -> stat.getLocation().getCountry()).sorted().distinct().collect(Collectors.toList()));
		summary.setCountriesWithFirstDeath(
				statsCollection.getLocationDeathsStats().stream().filter(LocationDeaths::isDeathsReported)
						.map(stat -> stat.getLocation().getCountry()).sorted().distinct().collect(Collectors.toList()));

		coronaDataResponse.setSummaryStats(summary);
	}

}
