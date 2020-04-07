package com.shadow.coronatracker.util.datacreators;

import java.util.List;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.LocationDeaths;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.response.CoronaDataResponse;
import com.shadow.coronatracker.model.summary.Summary;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class SummaryDataCreator implements DataCreator {

	@Override
	public void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse) {

		Summary summary = new Summary();
		summary.setTotalCases(fetchTotalCasesToday(statsCollection));
		summary.setTotalNewCases(fetchTotalNewCasesToday(statsCollection));
		summary.setTotalDeaths(fetchTotalDeathsToday(statsCollection));
		summary.setTotalNewDeaths(fetchTotalNewDeathsToday(statsCollection));
		summary.setTotalRecoveries(fetchTotalRecoveriesToday(statsCollection));
		summary.setMortalityRate(CoronaTrackerUtil.calculateRate(summary.getTotalDeaths(), summary.getTotalCases()));
		summary.setRecoveryRate(CoronaTrackerUtil.calculateRate(summary.getTotalRecoveries(), summary.getTotalCases()));
		summary.setCountriesWithFirstCase(fetchCountriesWithFirstCase(statsCollection));
		summary.setCountriesWithFirstDeath(fetchCountriesWithFirstDeath(statsCollection));

		coronaDataResponse.setSummaryStats(summary);
	}

	private Integer fetchTotalRecoveriesToday(StatisticsCollection statsCollection) {
		return statsCollection.getLocationRecoveriesStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getRecoveries().get(stat.getCurrDate())));
	}

	private Integer fetchTotalNewDeathsToday(StatisticsCollection statsCollection) {
		return statsCollection.getLocationDeathsStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getNewDeaths().get(stat.getCurrDate())));
	}

	private Integer fetchTotalDeathsToday(StatisticsCollection statsCollection) {
		return statsCollection.getLocationDeathsStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getDeaths().get(stat.getCurrDate())));
	}

	private Integer fetchTotalNewCasesToday(StatisticsCollection statsCollection) {
		return statsCollection.getLocationCasesStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getNewCases().get(stat.getCurrDate())));
	}

	private Integer fetchTotalCasesToday(StatisticsCollection statsCollection) {
		return statsCollection.getLocationCasesStats().stream()
				.collect(Collectors.summingInt(stat -> stat.getCases().get(stat.getCurrDate())));
	}

	private List<String> fetchCountriesWithFirstDeath(StatisticsCollection statsCollection) {
		return statsCollection.getLocationDeathsStats().stream().filter(LocationDeaths::isFirstDeath)
				.map(stat -> stat.getLocation().getCountry()).sorted().distinct().collect(Collectors.toList());
	}

	private List<String> fetchCountriesWithFirstCase(StatisticsCollection statsCollection) {
		return statsCollection.getLocationCasesStats().stream().filter(LocationCases::isFirstCase)
				.map(stat -> stat.getLocation().getCountry()).sorted().distinct().collect(Collectors.toList());
	}

}
