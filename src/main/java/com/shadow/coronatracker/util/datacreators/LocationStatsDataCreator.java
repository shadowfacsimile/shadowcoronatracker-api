package com.shadow.coronatracker.util.datacreators;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.LocationDeaths;
import com.shadow.coronatracker.model.LocationRecoveries;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.response.CoronaDataResponse;
import com.shadow.coronatracker.model.summary.CountrySummary;
import com.shadow.coronatracker.model.summary.StateSummary;

public class LocationStatsDataCreator implements DataCreator {

	@Override
	public void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse) {

		createCountryLevelStats(statsCollection);
		createStateLevelStats(statsCollection);

		coronaDataResponse.setCountrySummaryStats(statsCollection.getCountrySummaryStats().stream().distinct()
				.sorted(Comparator.comparingInt(CountrySummary::getCases).reversed()).collect(Collectors.toList()));

		coronaDataResponse.setStateSummaryStats(statsCollection.getStateSummaryStats().stream().distinct()
				.sorted(Comparator.comparingInt(StateSummary::getCases).reversed()).collect(Collectors.toList()));

	}

	private void createCountryLevelStats(StatisticsCollection statsCollection) {

		List<CountrySummary> countrySummaryStats = new LinkedList<>();

		statsCollection.getLocationCasesStats().stream().forEach(
				locationCases -> countrySummaryStatsMapper(statsCollection, countrySummaryStats, locationCases));

		statsCollection.setCountrySummaryStats(countrySummaryStats);
	}

	private void countrySummaryStatsMapper(StatisticsCollection statsCollection,
			List<CountrySummary> countrySummaryStats, LocationCases locationCases) {
		LocationDeaths locationDeaths = fetchLocationDeathsForThisLocation(statsCollection, locationCases);
		LocationRecoveries locationRecoveries = fetchLocationRecoveriesForThisLocation(statsCollection, locationCases);

		CountrySummary countrySummary = fetchCountrySummaryStatsIfAlreadyExists(countrySummaryStats, locationCases);
		countrySummary.setLocation(createLocation(locationCases, false));
		countrySummary.setCases(fetchCummulativeCasesForCountry(locationCases, countrySummary));
		countrySummary.setNewCases(fetchCummulativeNewCasesForCountry(locationCases, countrySummary));
		countrySummary.setDeaths(fetchCummulativeDeathsForCountry(locationCases, locationDeaths, countrySummary));
		countrySummary.setNewDeaths(fetchCummulativeNewDeathsForCountry(locationCases, locationDeaths, countrySummary));
		countrySummary
				.setRecoveries(fetchCummulativeRecoveriesForCountry(locationCases, locationRecoveries, countrySummary));
		countrySummary.setStatewiseDataExists(doesStatewiseDataExistForThisCountry(statsCollection, locationCases));
		countrySummary.setMortalityRate(calcualteMortalityRateForCountry(countrySummary));
		countrySummary.setRecoveryRate(calculateRecoveryRateForCountry(countrySummary));
		countrySummaryStats.add(countrySummary);
	}

	private double calculateRecoveryRateForCountry(CountrySummary countrySummary) {
		return countrySummary.getCases() == 0 ? 0 : (double) countrySummary.getRecoveries() / countrySummary.getCases();
	}

	private double calcualteMortalityRateForCountry(CountrySummary countrySummary) {
		return countrySummary.getCases() == 0 ? 0 : (double) countrySummary.getDeaths() / countrySummary.getCases();
	}

	private boolean doesStatewiseDataExistForThisCountry(final StatisticsCollection statsCollection,
			LocationCases locationCases) {
		return statsCollection.getLocationCasesStats().stream()
				.filter(stats -> stats.getLocation().getCountry().equals(locationCases.getLocation().getCountry()))
				.count() > 1;
	}

	private int fetchCummulativeRecoveriesForCountry(LocationCases locationCases, LocationRecoveries locationRecoveries,
			CountrySummary countrySummary) {
		return locationRecoveries == null ? 0
				: countrySummary.getRecoveries() + locationRecoveries.getRecoveries().get(locationCases.getCurrDate());
	}

	private int fetchCummulativeNewDeathsForCountry(LocationCases locationCases, LocationDeaths locationDeaths,
			CountrySummary countrySummary) {
		return locationDeaths == null ? 0
				: countrySummary.getNewDeaths() + locationDeaths.getNewDeaths().get(locationCases.getCurrDate());
	}

	private int fetchCummulativeDeathsForCountry(LocationCases locationCases, LocationDeaths locationDeaths,
			CountrySummary countrySummary) {
		return locationDeaths == null ? 0
				: countrySummary.getDeaths() + locationDeaths.getDeaths().get(locationCases.getCurrDate());
	}

	private int fetchCummulativeNewCasesForCountry(LocationCases locationCases, CountrySummary countrySummary) {
		return countrySummary.getNewCases() + locationCases.getNewCases().get(locationCases.getCurrDate());
	}

	private int fetchCummulativeCasesForCountry(LocationCases locationCases, CountrySummary countrySummary) {
		return countrySummary.getCases() + locationCases.getCases().get(locationCases.getCurrDate());
	}

	private LocationRecoveries fetchLocationRecoveriesForThisLocation(final StatisticsCollection statsCollection,
			LocationCases locationCases) {
		return statsCollection.getLocationRecoveriesStats().stream()
				.filter(stats -> stats.getLocation().equals(locationCases.getLocation())).findFirst().orElse(null);
	}

	private LocationDeaths fetchLocationDeathsForThisLocation(final StatisticsCollection statsCollection,
			LocationCases locationCases) {
		return statsCollection.getLocationDeathsStats().stream()
				.filter(stats -> stats.getLocation().equals(locationCases.getLocation())).findFirst().orElse(null);
	}

	private CountrySummary fetchCountrySummaryStatsIfAlreadyExists(List<CountrySummary> countrySummaryStats,
			LocationCases locationCases) {
		return countrySummaryStats.stream()
				.filter(stat -> locationCases.getLocation().getCountry().equals(stat.getLocation().getCountry()))
				.findFirst().orElse(new CountrySummary());
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
		stateSummary
				.setDeaths(locationDeaths == null ? 0 : locationDeaths.getDeaths().get(locationCases.getCurrDate()));
		stateSummary.setNewDeaths(
				locationDeaths == null ? 0 : locationDeaths.getNewDeaths().get(locationCases.getCurrDate()));
		stateSummary.setRecoveries(
				locationRecoveries == null ? 0 : locationRecoveries.getRecoveries().get(locationCases.getCurrDate()));
		stateSummary.setMortalityRate(
				stateSummary.getCases() == 0 ? 0 : (double) stateSummary.getDeaths() / stateSummary.getCases());
		stateSummary.setRecoveryRate(
				stateSummary.getCases() == 0 ? 0 : (double) stateSummary.getRecoveries() / stateSummary.getCases());
		stateSummaryStats.add(stateSummary);
	}

	private Location createLocation(LocationCases locationCases, boolean isStateLevel) {
		Location location = new Location();
		location.setCountry(locationCases.getLocation().getCountry());

		if (isStateLevel)
			location.setState(locationCases.getLocation().getState());

		return location;
	}

}
