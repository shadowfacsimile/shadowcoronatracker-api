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
import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.deathgrowth.CountryDeathsGrowth;
import com.shadow.coronatracker.model.deathgrowth.DeathsGrowth;
import com.shadow.coronatracker.model.response.CoronaStatsResponse;

public class CountryDeathsGrowthDataCreator implements DataCreator {

	@Override
	public void create(StatsCollection statsCollection, CoronaStatsResponse coronaDataResponse) {

		List<CountryDeathsGrowth> countryDeathsGrowthStats = new LinkedList<>();
		Map<String, Integer> totalDeathsGrowthMap = new LinkedHashMap<>();
		Map<String, Integer> totalNewDeathsGrowthMap = new LinkedHashMap<>();

		for (LocationDeaths locationDeaths : statsCollection.getLocationDeathsStats()) {
			Predicate<CountryDeathsGrowth> countryFilter = stat -> locationDeaths.getLocation().getCountry()
					.equals(stat.getCountry());
			CountryDeathsGrowth countryDeathsGrowth = countryDeathsGrowthStats.stream().filter(countryFilter)
					.findFirst().orElse(new CountryDeathsGrowth());
			List<DeathsGrowth> deathsGrowthStats = countryDeathsGrowth.getDeathsGrowths() == null ? new LinkedList<>()
					: countryDeathsGrowth.getDeathsGrowths();
			List<DeathsGrowth> updatedDeathsGrowthStats = createDeathsGrowth(deathsGrowthStats, locationDeaths,
					totalDeathsGrowthMap, totalNewDeathsGrowthMap);

			countryDeathsGrowth.setCountry(locationDeaths.getLocation().getCountry());
			countryDeathsGrowth.setDeathsGrowths(updatedDeathsGrowthStats);
			countryDeathsGrowthStats.add(countryDeathsGrowth);
		}

		statsCollection.setTotalDeathsGrowthMap(totalDeathsGrowthMap);
		statsCollection.setTotalNewDeathsGrowthMap(totalNewDeathsGrowthMap);

		coronaDataResponse.setCountryDeathsGrowthStats(countryDeathsGrowthStats.stream().distinct()
				.sorted(Comparator.comparing(CountryDeathsGrowth::getCountry)).collect(Collectors.toList()));

	}

	private List<DeathsGrowth> createDeathsGrowth(List<DeathsGrowth> deathsGrowthStats, LocationDeaths locationDeaths,
			Map<String, Integer> totalDeathsGrowthMap, Map<String, Integer> totalNewDeathsGrowthMap) {

		boolean doesDeathsGrowthExists = deathsGrowthStats.size() > 0;

		for (Entry<String, Integer> entry : locationDeaths.getDeaths().entrySet()) {
			Predicate<DeathsGrowth> dateFilter = stat -> stat.getDate().equals(entry.getKey());
			DeathsGrowth deathsGrowth = doesDeathsGrowthExists
					? deathsGrowthStats.stream().filter(dateFilter).findFirst().get()
					: new DeathsGrowth();
			deathsGrowth.setDate(entry.getKey());
			deathsGrowth.setGrowth(
					deathsGrowth.getGrowth() == null ? entry.getValue() : deathsGrowth.getGrowth() + entry.getValue());
			deathsGrowth.setDelta(deathsGrowth.getDelta() == null ? locationDeaths.getNewDeaths().get(entry.getKey())
					: deathsGrowth.getDelta() + locationDeaths.getNewDeaths().get(entry.getKey()));
			deathsGrowthStats.add(deathsGrowth);

			totalDeathsGrowthMap.put(entry.getKey(), totalDeathsGrowthMap.get(entry.getKey()) == null ? entry.getValue()
					: totalDeathsGrowthMap.get(entry.getKey()) + entry.getValue());
			totalNewDeathsGrowthMap.put(entry.getKey(), totalNewDeathsGrowthMap.get(entry.getKey()) == null
					? locationDeaths.getNewDeaths().get(entry.getKey())
					: totalNewDeathsGrowthMap.get(entry.getKey()) + locationDeaths.getNewDeaths().get(entry.getKey()));
		}

		return deathsGrowthStats.stream().distinct().collect(Collectors.toList());
	}

}
