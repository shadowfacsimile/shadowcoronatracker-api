package com.shadow.coronatracker.util.datacreators;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.LocationDeaths;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.deathgrowth.CountryDeathsGrowth;
import com.shadow.coronatracker.model.deathgrowth.DeathsGrowth;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CountryDeathsGrowthDataCreator implements DataCreator {

	@Override
	public void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse) {

		Map<String, Integer> totalDeathsGrowthMap = new LinkedHashMap<>();
		Map<String, Integer> totalNewDeathsGrowthMap = new LinkedHashMap<>();

		List<CountryDeathsGrowth> countryDeathsGrowthStats = new LinkedList<>();

		for (LocationDeaths locationDeaths : statsCollection.getLocationDeathsStats()) {
			String country = locationDeaths.getLocation().getCountry();
			CountryDeathsGrowth countryDeathsGrowth = fetchCountryDeathsGrowthIfExists(country,
					countryDeathsGrowthStats);
			List<DeathsGrowth> deathsGrowthStats = fetchDeathsGrowthStatsIfExists(countryDeathsGrowth);
			List<DeathsGrowth> updatedDeathsGrowthStats = createOrUpdateDeathsGrowth(deathsGrowthStats, locationDeaths,
					totalDeathsGrowthMap, totalNewDeathsGrowthMap);
			countryDeathsGrowthMapper(locationDeaths, countryDeathsGrowth, updatedDeathsGrowthStats);
			countryDeathsGrowthStats.add(countryDeathsGrowth);
		}

		statsCollection.setTotalDeathsGrowthMap(totalDeathsGrowthMap);
		statsCollection.setTotalNewDeathsGrowthMap(totalNewDeathsGrowthMap);

		coronaDataResponse.setCountryDeathsGrowthStats(countryDeathsGrowthStats.stream().distinct()
				.sorted(Comparator.comparing(CountryDeathsGrowth::getCountry)).collect(Collectors.toList()));
	}

	private void countryDeathsGrowthMapper(LocationDeaths locationDeaths, CountryDeathsGrowth countryDeathsGrowth,
			List<DeathsGrowth> updatedDeathsGrowthStats) {
		countryDeathsGrowth.setCountry(locationDeaths.getLocation().getCountry());
		countryDeathsGrowth.setDeathsGrowths(updatedDeathsGrowthStats);
	}

	private List<DeathsGrowth> fetchDeathsGrowthStatsIfExists(CountryDeathsGrowth countryDeathsGrowth) {
		return countryDeathsGrowth.getDeathsGrowths() == null ? new LinkedList<>()
				: countryDeathsGrowth.getDeathsGrowths();
	}

	private CountryDeathsGrowth fetchCountryDeathsGrowthIfExists(String country,
			List<CountryDeathsGrowth> countryDeathsGrowthStats) {
		Predicate<CountryDeathsGrowth> countryFilter = stat -> country.equals(stat.getCountry());
		return countryDeathsGrowthStats.stream().filter(countryFilter).findFirst().orElse(new CountryDeathsGrowth());
	}

	private List<DeathsGrowth> createOrUpdateDeathsGrowth(List<DeathsGrowth> deathsGrowthStats,
			LocationDeaths locationDeaths, Map<String, Integer> totalDeathsGrowthMap,
			Map<String, Integer> totalNewDeathsGrowthMap) {

		boolean doesDeathsGrowthExists = deathsGrowthStats.size() > 0;

		for (Entry<String, Integer> entry : locationDeaths.getDeaths().entrySet()) {
			String date = entry.getKey();
			Integer deaths = entry.getValue();
			Integer newDeaths = locationDeaths.getNewDeaths().get(date);

			DeathsGrowth deathsGrowth = fetchDeathsGrowthIfExists(deathsGrowthStats, doesDeathsGrowthExists, date);
			deathsGrowthMapper(date, deaths, newDeaths, deathsGrowth);
			deathsGrowthStats.add(deathsGrowth);

			createEntryInTotalDeathsGrowthMap(date, deaths, totalDeathsGrowthMap);
			createEntryInTotalNewDeathsGrowthMap(date, newDeaths, totalNewDeathsGrowthMap);
		}

		return deathsGrowthStats.stream().distinct().collect(Collectors.toList());
	}

	private void deathsGrowthMapper(String date, Integer deaths, Integer newDeaths, DeathsGrowth deathsGrowth) {
		deathsGrowth.setDate(date);
		deathsGrowth.setGrowth(fetchCummulativeDeathGrowth(deaths, deathsGrowth));
		deathsGrowth.setDelta(fetchCummulativeDeltaGrowth(newDeaths, deathsGrowth));
	}

	private DeathsGrowth fetchDeathsGrowthIfExists(List<DeathsGrowth> deathsGrowthStats, boolean doesDeathsGrowthExists,
			String date) {
		Predicate<DeathsGrowth> dateFilter = stat -> stat.getDate().equals(date);
		return doesDeathsGrowthExists ? deathsGrowthStats.stream().filter(dateFilter).findFirst().get()
				: new DeathsGrowth();
	}

	private int fetchCummulativeDeathGrowth(Integer accumulatedDeaths, DeathsGrowth deathsGrowth) {
		return deathsGrowth.getGrowth() == null ? accumulatedDeaths : accumulatedDeaths + deathsGrowth.getGrowth();
	}

	private int fetchCummulativeDeltaGrowth(Integer accumulatedDelta, DeathsGrowth deathsGrowth) {
		return deathsGrowth.getDelta() == null ? accumulatedDelta : accumulatedDelta + deathsGrowth.getDelta();
	}

	private void createEntryInTotalDeathsGrowthMap(String date, Integer deaths,
			Map<String, Integer> totalDeathsGrowthMap) {
		Integer accumulatedDeaths = totalDeathsGrowthMap.get(date);
		totalDeathsGrowthMap.put(date, accumulatedDeaths == null ? deaths : accumulatedDeaths + deaths);
	}

	private void createEntryInTotalNewDeathsGrowthMap(String date, Integer newDeaths,
			Map<String, Integer> totalNewDeathsGrowthMap) {
		Integer accumulatedNewDeaths = totalNewDeathsGrowthMap.get(date);
		totalNewDeathsGrowthMap.put(date, accumulatedNewDeaths == null ? newDeaths : accumulatedNewDeaths + newDeaths);
	}

}
