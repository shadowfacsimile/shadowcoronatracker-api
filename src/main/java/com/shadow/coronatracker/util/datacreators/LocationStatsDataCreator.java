package com.shadow.coronatracker.util.datacreators;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.LocationDeaths;
import com.shadow.coronatracker.model.LocationRecoveries;
import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.response.CoronaStatsResponse;
import com.shadow.coronatracker.model.summary.CountrySummary;
import com.shadow.coronatracker.model.summary.StateSummary;

public class LocationStatsDataCreator implements DataCreator {

	@Override
	public void create(StatsCollection statsCollection, CoronaStatsResponse coronaDataResponse) {

		createCountryLevelStats(statsCollection);
		createStateLevelStats(statsCollection);

		coronaDataResponse.setCountrySummaryStats(statsCollection.getCountrySummaryStats().stream().distinct()
				.sorted(Comparator.comparingInt(CountrySummary::getCases).reversed()).collect(Collectors.toList()));

		coronaDataResponse.setStateSummaryStats(statsCollection.getStateSummaryStats().stream().distinct()
				.sorted(Comparator.comparingInt(StateSummary::getCases).reversed()).collect(Collectors.toList()));

	}

	private void createCountryLevelStats(final StatsCollection statsCollection) {

		List<CountrySummary> countrySummaryStats = new LinkedList<>();

		for (LocationCases locationCases : statsCollection.getLocationCasesStats()) {
			CountrySummary countrySummary = countrySummaryStats.stream()
					.filter(stat -> locationCases.getLocation().getCountry().equals(stat.getLocation().getCountry()))
					.findFirst().orElse(new CountrySummary());
			LocationDeaths locationDeaths = statsCollection.getLocationDeathsStats().stream()
					.filter(stats -> stats.getLocation().equals(locationCases.getLocation())).findFirst().orElse(null);
			LocationRecoveries locationRecoveries = statsCollection.getLocationRecoveriesStats().stream()
					.filter(stats -> stats.getLocation().equals(locationCases.getLocation())).findFirst().orElse(null);

			countrySummary.setLocation(createLocation(locationCases, false));
			countrySummary
					.setCases(countrySummary.getCases() + locationCases.getCases().get(locationCases.getCurrDate()));
			countrySummary.setNewCases(
					countrySummary.getNewCases() + locationCases.getNewCases().get(locationCases.getCurrDate()));
			countrySummary.setDeaths(locationDeaths == null ? 0
					: countrySummary.getDeaths() + locationDeaths.getDeaths().get(locationCases.getCurrDate()));
			countrySummary.setNewDeaths(locationDeaths == null ? 0
					: countrySummary.getNewDeaths() + locationDeaths.getNewDeaths().get(locationCases.getCurrDate()));
			countrySummary.setRecoveries(locationRecoveries == null ? 0
					: countrySummary.getRecoveries()
							+ locationRecoveries.getRecoveries().get(locationCases.getCurrDate()));
			countrySummary.setStatewiseDataExists(statsCollection.getLocationCasesStats().stream()
					.filter(stats -> stats.getLocation().getCountry().equals(locationCases.getLocation().getCountry()))
					.count() > 1);
			countrySummary.setMortalityRate(countrySummary.getCases() == 0 ? 0
					: (double) countrySummary.getDeaths() / countrySummary.getCases());
			countrySummary.setRecoveryRate(countrySummary.getCases() == 0 ? 0
					: (double) countrySummary.getRecoveries() / countrySummary.getCases());
			countrySummaryStats.add(countrySummary);
		}

		statsCollection.setCountrySummaryStats(countrySummaryStats);
	}

	private void createStateLevelStats(final StatsCollection statsCollection) {

		List<StateSummary> stateSummaryStats = new LinkedList<>();

		for (LocationCases locationCases : statsCollection.getLocationCasesStats()) {

			LocationDeaths locationDeaths = statsCollection.getLocationDeathsStats().stream()
					.filter(stats -> stats.getLocation().equals(locationCases.getLocation())).findFirst().orElse(null);
			LocationRecoveries locationRecoveries = statsCollection.getLocationRecoveriesStats().stream()
					.filter(stats -> stats.getLocation().equals(locationCases.getLocation())).findFirst().orElse(null);

			StateSummary stateSummary = new StateSummary();
			stateSummary.setLocation(createLocation(locationCases, true));
			stateSummary.setCases(locationCases.getCases().get(locationCases.getCurrDate()));
			stateSummary.setNewCases(locationCases.getNewCases().get(locationCases.getCurrDate()));
			stateSummary.setDeaths(
					locationDeaths == null ? 0 : locationDeaths.getDeaths().get(locationCases.getCurrDate()));
			stateSummary.setNewDeaths(
					locationDeaths == null ? 0 : locationDeaths.getNewDeaths().get(locationCases.getCurrDate()));
			stateSummary.setRecoveries(locationRecoveries == null ? 0
					: locationRecoveries.getRecoveries().get(locationCases.getCurrDate()));
			stateSummary.setMortalityRate(
					stateSummary.getCases() == 0 ? 0 : (double) stateSummary.getDeaths() / stateSummary.getCases());
			stateSummary.setRecoveryRate(
					stateSummary.getCases() == 0 ? 0 : (double) stateSummary.getRecoveries() / stateSummary.getCases());
			stateSummaryStats.add(stateSummary);
		}

		statsCollection.setStateSummaryStats(stateSummaryStats);
	}

	private Location createLocation(LocationCases locationCases, boolean isStateLevel) {
		Location location = new Location();
		location.setCountry(locationCases.getLocation().getCountry());

		if (isStateLevel)
			location.setState(locationCases.getLocation().getState());

		return location;
	}

}
