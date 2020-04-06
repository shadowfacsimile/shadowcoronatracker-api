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

		Map<String, Integer> totalCasesGrowthMap = new LinkedHashMap<>();
		Map<String, Integer> totalNewCasesGrowthMap = new LinkedHashMap<>();

		List<CountryCasesGrowth> countryCasesGrowthStats = new LinkedList<>();

		for (LocationCases locationCases : statsCollection.getLocationCasesStats()) {
			String country = locationCases.getLocation().getCountry();
			CountryCasesGrowth countryCasesGrowth = fetchCountryCasesGrowthIfExists(country,
					countryCasesGrowthStats);
			List<CasesGrowth> casesGrowthStats = fetchCasesGrowthStatsIfExists(countryCasesGrowth);
			List<CasesGrowth> updatedCasesGrowthStats = createOrUpdateCasesGrowth(casesGrowthStats, locationCases,
					totalCasesGrowthMap, totalNewCasesGrowthMap);
			countryCasesGrowthMapper(locationCases, countryCasesGrowth, updatedCasesGrowthStats);
			countryCasesGrowthStats.add(countryCasesGrowth);
		}

		statsCollection.setTotalCasesGrowthMap(totalCasesGrowthMap);
		statsCollection.setTotalNewCasesGrowthMap(totalNewCasesGrowthMap);

		coronaDataResponse.setCountryCasesGrowthStats(countryCasesGrowthStats.stream().distinct()
				.sorted(Comparator.comparing(CountryCasesGrowth::getCountry)).collect(Collectors.toList()));
	}

	private void countryCasesGrowthMapper(LocationCases locationCases, CountryCasesGrowth countryCasesGrowth,
			List<CasesGrowth> updatedCasesGrowthStats) {
		countryCasesGrowth.setCountry(locationCases.getLocation().getCountry());
		countryCasesGrowth.setCasesGrowths(updatedCasesGrowthStats);
	}

	private List<CasesGrowth> fetchCasesGrowthStatsIfExists(CountryCasesGrowth countryCasesGrowth) {
		return countryCasesGrowth.getCasesGrowths() == null ? new LinkedList<>()
				: countryCasesGrowth.getCasesGrowths();
	}

	private CountryCasesGrowth fetchCountryCasesGrowthIfExists(String country,
			List<CountryCasesGrowth> countryCasesGrowthStats) {
		Predicate<CountryCasesGrowth> countryFilter = stat -> country.equals(stat.getCountry());
		return countryCasesGrowthStats.stream().filter(countryFilter).findFirst().orElse(new CountryCasesGrowth());
	}

	private List<CasesGrowth> createOrUpdateCasesGrowth(List<CasesGrowth> casesGrowthStats,
			LocationCases locationCases, Map<String, Integer> totalCasesGrowthMap,
			Map<String, Integer> totalNewCasesGrowthMap) {

		boolean doesCasesGrowthExists = casesGrowthStats.size() > 0;

		for (Entry<String, Integer> entry : locationCases.getCases().entrySet()) {
			String date = entry.getKey();
			Integer cases = entry.getValue();
			Integer newCases = locationCases.getNewCases().get(date);

			CasesGrowth casesGrowth = fetchCasesGrowthIfExists(casesGrowthStats, doesCasesGrowthExists, date);
			casesGrowthMapper(date, cases, newCases, casesGrowth);
			casesGrowthStats.add(casesGrowth);

			createEntryInTotalCasesGrowthMap(date, cases, totalCasesGrowthMap);
			createEntryInTotalNewCasesGrowthMap(date, newCases, totalNewCasesGrowthMap);
		}

		return casesGrowthStats.stream().distinct().collect(Collectors.toList());
	}

	private void casesGrowthMapper(String date, Integer cases, Integer newCases, CasesGrowth casesGrowth) {
		casesGrowth.setDate(date);
		casesGrowth.setGrowth(fetchCummulativeCaseGrowth(cases, casesGrowth));
		casesGrowth.setDelta(fetchCummulativeDeltaGrowth(newCases, casesGrowth));
	}

	private CasesGrowth fetchCasesGrowthIfExists(List<CasesGrowth> casesGrowthStats, boolean doesCasesGrowthExists,
			String date) {
		Predicate<CasesGrowth> dateFilter = stat -> stat.getDate().equals(date);
		return doesCasesGrowthExists ? casesGrowthStats.stream().filter(dateFilter).findFirst().get()
				: new CasesGrowth();
	}

	private int fetchCummulativeCaseGrowth(Integer accumulatedCases, CasesGrowth casesGrowth) {
		return casesGrowth.getGrowth() == null ? accumulatedCases : accumulatedCases + casesGrowth.getGrowth();
	}

	private int fetchCummulativeDeltaGrowth(Integer accumulatedDelta, CasesGrowth casesGrowth) {
		return casesGrowth.getDelta() == null ? accumulatedDelta : accumulatedDelta + casesGrowth.getDelta();
	}

	private void createEntryInTotalCasesGrowthMap(String date, Integer cases,
			Map<String, Integer> totalCasesGrowthMap) {
		Integer accumulatedCases = totalCasesGrowthMap.get(date);
		totalCasesGrowthMap.put(date, accumulatedCases == null ? cases : accumulatedCases + cases);
	}

	private void createEntryInTotalNewCasesGrowthMap(String date, Integer newCases,
			Map<String, Integer> totalNewCasesGrowthMap) {
		Integer accumulatedNewCases = totalNewCasesGrowthMap.get(date);
		totalNewCasesGrowthMap.put(date, accumulatedNewCases == null ? newCases : accumulatedNewCases + newCases);
	}

}
