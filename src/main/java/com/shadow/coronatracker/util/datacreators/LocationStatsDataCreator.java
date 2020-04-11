package com.shadow.coronatracker.util.datacreators;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.LocationDeaths;
import com.shadow.coronatracker.model.LocationRecoveries;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.response.CoronaDataResponse;
import com.shadow.coronatracker.model.summary.CountrySummary;
import com.shadow.coronatracker.model.summary.StateSummary;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class LocationStatsDataCreator implements DataCreator {

	@Override
	public void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse) {

		createStateLevelStats(statsCollection);
		createCountryLevelStats(statsCollection);

		coronaDataResponse.setCountrySummaryStats(statsCollection.getCountrySummaryStats().stream().distinct()
				.sorted(Comparator.comparingInt(CountrySummary::getCases).reversed()).collect(Collectors.toList()));

		coronaDataResponse.setStateSummaryStats(statsCollection.getStateSummaryStats().stream().distinct()
				.sorted(Comparator.comparingInt(StateSummary::getCases).reversed()).collect(Collectors.toList()));
	}

	private void createStateLevelStats(StatisticsCollection statsCollection) {

		List<StateSummary> stateSummaryStats = new LinkedList<>();

		statsCollection.getLocationCasesStats().stream()
				.forEach(locationCases -> stateSummaryStatsMapper(statsCollection, stateSummaryStats, locationCases));

		statsCollection.setStateSummaryStats(stateSummaryStats);
	}

	private void stateSummaryStatsMapper(StatisticsCollection statsCollection, List<StateSummary> stateSummaryStats,
			LocationCases locationCases) {
		LocationDeaths locationDeaths = fetchLocationDeathsForThisLocation(statsCollection, locationCases);
		LocationRecoveries locationRecoveries = fetchLocationRecoveriesForThisLocation(statsCollection, locationCases);

		StateSummary stateSummary = new StateSummary();
		stateSummary.setLocation(createLocation(locationCases, true));
		stateSummary.setCases(locationCases.getCases().get(locationCases.getCurrDate()));
		stateSummary.setNewCases(locationCases.getNewCases().get(locationCases.getCurrDate()));
		stateSummary.setDeaths(fetchLocationDeathsOfToday(locationCases, locationDeaths));
		stateSummary.setNewDeaths(fetchLocationNewDeathsOfToday(locationCases, locationDeaths));
		stateSummary.setRecoveries(fetchLocationRecoveriesOfToday(locationCases, locationRecoveries));
		stateSummary
				.setMortalityRate(CoronaTrackerUtil.calculateRate(stateSummary.getDeaths(), stateSummary.getCases()));
		stateSummary.setRecoveryRate(
				CoronaTrackerUtil.calculateRate(stateSummary.getRecoveries(), stateSummary.getCases()));
		stateSummaryStats.add(stateSummary);
	}

	private int fetchLocationRecoveriesOfToday(LocationCases locationCases, LocationRecoveries locationRecoveries) {
		return locationRecoveries == null ? 0 : locationRecoveries.getRecoveries().get(locationCases.getCurrDate());
	}

	private int fetchLocationNewDeathsOfToday(LocationCases locationCases, LocationDeaths locationDeaths) {
		return locationDeaths == null ? 0 : locationDeaths.getNewDeaths().get(locationCases.getCurrDate());
	}

	private int fetchLocationDeathsOfToday(LocationCases locationCases, LocationDeaths locationDeaths) {
		return locationDeaths == null ? 0 : locationDeaths.getDeaths().get(locationCases.getCurrDate());
	}

	private void createCountryLevelStats(StatisticsCollection statsCollection) {

		List<CountrySummary> countrySummaryStats = new LinkedList<>();
		List<StateSummary> stateSummaryStats = statsCollection.getStateSummaryStats();

		statsCollection.getLocationCasesStats().stream().forEach(
				locationCases -> countrySummaryStatsMapper(locationCases, countrySummaryStats, stateSummaryStats));

		statsCollection.setCountrySummaryStats(countrySummaryStats);
	}

	private void countrySummaryStatsMapper(LocationCases locationCases, List<CountrySummary> countrySummaryStats,
			List<StateSummary> stateSummaryStats) {

		Predicate<StateSummary> locationFilter = stat -> stat.getLocation().getCountry()
				.equals(locationCases.getLocation().getCountry());

		CountrySummary countrySummary = new CountrySummary();
		countrySummary.setLocation(createLocation(locationCases, false));
		countrySummary
				.setCases(stateSummaryStats.stream().filter(locationFilter).mapToInt(StateSummary::getCases).sum());
		countrySummary.setNewCases(
				stateSummaryStats.stream().filter(locationFilter).mapToInt(StateSummary::getNewCases).sum());
		countrySummary
				.setDeaths(stateSummaryStats.stream().filter(locationFilter).mapToInt(StateSummary::getDeaths).sum());
		countrySummary.setNewDeaths(
				stateSummaryStats.stream().filter(locationFilter).mapToInt(StateSummary::getNewDeaths).sum());
		countrySummary.setRecoveries(
				stateSummaryStats.stream().filter(locationFilter).mapToInt(StateSummary::getRecoveries).sum());
		countrySummary.setStatewiseDataExists(stateSummaryStats.stream().filter(locationFilter).count() > 1);
		countrySummary.setMortalityRate(
				CoronaTrackerUtil.calculateRate(countrySummary.getDeaths(), countrySummary.getCases()));
		countrySummary.setRecoveryRate(
				CoronaTrackerUtil.calculateRate(countrySummary.getRecoveries(), countrySummary.getCases()));
		countrySummaryStats.add(countrySummary);
	}

	private LocationRecoveries fetchLocationRecoveriesForThisLocation(final StatisticsCollection statsCollection,
			LocationCases locationCases) {
		
		Predicate<LocationRecoveries> locationFilter = stats -> stats.getLocation().equals(locationCases.getLocation());
		return statsCollection.getLocationRecoveriesStats().stream().filter(locationFilter).findFirst().orElse(null);
	}

	private LocationDeaths fetchLocationDeathsForThisLocation(final StatisticsCollection statsCollection,
			LocationCases locationCases) {
		
		Predicate<LocationDeaths> locationFilter = stats -> stats.getLocation().equals(locationCases.getLocation());
		return statsCollection.getLocationDeathsStats().stream()
				.filter(locationFilter).findFirst().orElse(null);
	}

	private Location createLocation(LocationCases locationCases, boolean isStateLevel) {
		
		Location location = new Location();
		location.setCountry(locationCases.getLocation().getCountry());

		if (isStateLevel)
			location.setState(locationCases.getLocation().getState());

		return location;
	}

}
