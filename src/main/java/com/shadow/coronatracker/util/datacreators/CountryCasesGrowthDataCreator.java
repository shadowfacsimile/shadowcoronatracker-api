package com.shadow.coronatracker.util.datacreators;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.casegrowth.CasesGrowth;
import com.shadow.coronatracker.model.casegrowth.CountryCasesGrowth;
import com.shadow.coronatracker.model.response.CoronaStatsResponse;

public class CountryCasesGrowthDataCreator implements DataCreator {

	@Override
	public void create(StatsCollection statsCollection, CoronaStatsResponse coronaDataResponse) {

		List<CountryCasesGrowth> countryCasesGrowthStats = new LinkedList<>();
		Map<String, Integer> totalCasesGrowthMap = new LinkedHashMap<>();
		Map<String, Integer> totalNewCasesGrowthMap = new LinkedHashMap<>();

		for (LocationCases locationCases : statsCollection.getLocationCasesStats()) {
			Predicate<CountryCasesGrowth> countryFilter = stat -> locationCases.getLocation().getCountry()
					.equals(stat.getCountry());
			CountryCasesGrowth countryCasesGrowth = countryCasesGrowthStats.stream().filter(countryFilter).findFirst()
					.orElse(new CountryCasesGrowth());
			List<CasesGrowth> casesGrowthStats = countryCasesGrowth.getCasesGrowths() == null ? new LinkedList<>()
					: countryCasesGrowth.getCasesGrowths();
			List<CasesGrowth> updatedCasesGrowthStats = createCasesGrowth(casesGrowthStats, locationCases,
					totalCasesGrowthMap, totalNewCasesGrowthMap);

			countryCasesGrowth.setCountry(locationCases.getLocation().getCountry());
			countryCasesGrowth.setCasesGrowths(updatedCasesGrowthStats);
			countryCasesGrowthStats.add(countryCasesGrowth);
		}

		statsCollection.setTotalCasesGrowthMap(totalCasesGrowthMap);
		statsCollection.setTotalNewCasesGrowthMap(totalNewCasesGrowthMap);

		coronaDataResponse.setCountryCasesGrowthStats(countryCasesGrowthStats.stream().distinct()
				.sorted(Comparator.comparing(CountryCasesGrowth::getCountry)).collect(Collectors.toList()));

	}

	private List<CasesGrowth> createCasesGrowth(List<CasesGrowth> casesGrowthStats, LocationCases locationCases,
			Map<String, Integer> totalCasesGrowthMap, Map<String, Integer> totalNewCasesGrowthMap) {

		boolean doesCasesGrowthExists = casesGrowthStats.size() > 0;

		for (Entry<String, Integer> entry : locationCases.getCases().entrySet()) {
			Predicate<CasesGrowth> dateFilter = stat -> stat.getDate().equals(entry.getKey());
			CasesGrowth casesGrowth = doesCasesGrowthExists
					? casesGrowthStats.stream().filter(dateFilter).findFirst().get()
					: new CasesGrowth();
			casesGrowth.setDate(entry.getKey());
			casesGrowth.setGrowth(
					casesGrowth.getGrowth() == null ? entry.getValue() : casesGrowth.getGrowth() + entry.getValue());
			casesGrowth.setDelta(casesGrowth.getDelta() == null ? locationCases.getNewCases().get(entry.getKey())
					: casesGrowth.getDelta() + locationCases.getNewCases().get(entry.getKey()));
			casesGrowthStats.add(casesGrowth);

			totalCasesGrowthMap.put(entry.getKey(), totalCasesGrowthMap.get(entry.getKey()) == null ? entry.getValue()
					: totalCasesGrowthMap.get(entry.getKey()) + entry.getValue());
			totalNewCasesGrowthMap.put(entry.getKey(), totalNewCasesGrowthMap.get(entry.getKey()) == null
					? locationCases.getNewCases().get(entry.getKey())
					: totalNewCasesGrowthMap.get(entry.getKey()) + locationCases.getNewCases().get(entry.getKey()));
		}

		return casesGrowthStats.stream().distinct().collect(Collectors.toList());
	}

}
