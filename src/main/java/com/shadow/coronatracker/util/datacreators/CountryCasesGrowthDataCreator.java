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
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.casegrowth.CasesGrowth;
import com.shadow.coronatracker.model.casegrowth.CountryCasesGrowth;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CountryCasesGrowthDataCreator implements DataCreator {

	@Override
	public void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse) {

		List<CountryCasesGrowth> countryCasesGrowthStats = new LinkedList<>();
		Map<String, Integer> totalCasesGrowthMap = new LinkedHashMap<>();
		Map<String, Integer> totalNewCasesGrowthMap = new LinkedHashMap<>();

		for (LocationCases locationCases : statsCollection.getLocationCasesStats()) {
			Predicate<CountryCasesGrowth> countryFilter = stat -> locationCases.getLocation().getCountry()
					.equals(stat.getCountry());
			CountryCasesGrowth countryCasesGrowth = fetchCountryCasesGrowthIfExists(countryCasesGrowthStats,
					countryFilter);
			List<CasesGrowth> casesGrowthStats = fetchCasesGrowthStatsIfExists(countryCasesGrowth);
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

	private List<CasesGrowth> fetchCasesGrowthStatsIfExists(CountryCasesGrowth countryCasesGrowth) {
		return countryCasesGrowth.getCasesGrowths() == null ? new LinkedList<>() : countryCasesGrowth.getCasesGrowths();
	}

	private CountryCasesGrowth fetchCountryCasesGrowthIfExists(List<CountryCasesGrowth> countryCasesGrowthStats,
			Predicate<CountryCasesGrowth> countryFilter) {
		return countryCasesGrowthStats.stream().filter(countryFilter).findFirst().orElse(new CountryCasesGrowth());
	}

	private List<CasesGrowth> createCasesGrowth(List<CasesGrowth> casesGrowthStats, LocationCases locationCases,
			Map<String, Integer> totalCasesGrowthMap, Map<String, Integer> totalNewCasesGrowthMap) {

		boolean doesCasesGrowthExists = casesGrowthStats.size() > 0;

		for (Entry<String, Integer> entry : locationCases.getCases().entrySet()) {
			Predicate<CasesGrowth> dateFilter = stat -> stat.getDate().equals(entry.getKey());
			CasesGrowth casesGrowth = fetchCasesGrowthIfExists(casesGrowthStats, doesCasesGrowthExists, dateFilter);
			casesGrowth.setDate(entry.getKey());
			casesGrowth.setGrowth(fetchCummulativeCaseGrowth(entry, casesGrowth));
			casesGrowth.setDelta(fetchCummulativeDeltaGrowth(locationCases, entry, casesGrowth));
			casesGrowthStats.add(casesGrowth);

			createEntryInTotalCasesGrowthMap(totalCasesGrowthMap, entry);
			createEntryInTotalNewCasesGrowthMap(locationCases, totalNewCasesGrowthMap, entry);
		}

		return casesGrowthStats.stream().distinct().collect(Collectors.toList());
	}

	private void createEntryInTotalNewCasesGrowthMap(LocationCases locationCases,
			Map<String, Integer> totalNewCasesGrowthMap, Entry<String, Integer> entry) {
		totalNewCasesGrowthMap.put(entry.getKey(),
				totalNewCasesGrowthMap.get(entry.getKey()) == null ? locationCases.getNewCases().get(entry.getKey())
						: totalNewCasesGrowthMap.get(entry.getKey()) + locationCases.getNewCases().get(entry.getKey()));
	}

	private void createEntryInTotalCasesGrowthMap(Map<String, Integer> totalCasesGrowthMap,
			Entry<String, Integer> entry) {
		totalCasesGrowthMap.put(entry.getKey(), totalCasesGrowthMap.get(entry.getKey()) == null ? entry.getValue()
				: totalCasesGrowthMap.get(entry.getKey()) + entry.getValue());
	}

	private int fetchCummulativeDeltaGrowth(LocationCases locationCases, Entry<String, Integer> entry,
			CasesGrowth casesGrowth) {
		return casesGrowth.getDelta() == null ? locationCases.getNewCases().get(entry.getKey())
				: casesGrowth.getDelta() + locationCases.getNewCases().get(entry.getKey());
	}

	private int fetchCummulativeCaseGrowth(Entry<String, Integer> entry, CasesGrowth casesGrowth) {
		return casesGrowth.getGrowth() == null ? entry.getValue() : casesGrowth.getGrowth() + entry.getValue();
	}

	private CasesGrowth fetchCasesGrowthIfExists(List<CasesGrowth> casesGrowthStats, boolean doesCasesGrowthExists,
			Predicate<CasesGrowth> dateFilter) {
		return doesCasesGrowthExists ? casesGrowthStats.stream().filter(dateFilter).findFirst().get()
				: new CasesGrowth();
	}

}
