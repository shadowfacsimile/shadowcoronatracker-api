package com.shadow.coronatracker.util.datacreators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthCountryStats;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthStats;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaCasesGrowthCountryDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaCaseGrowthCountryStats> caseGrowthStatsList = new ArrayList<>();

		for (Entry<String, List<CoronaCaseGrowthStats>> caseGrowth : coronaStatsCollection.getCoronaCasesGrowthCountry()
				.entrySet()) {
			CoronaCaseGrowthCountryStats caseGrowthStats = new CoronaCaseGrowthCountryStats();
			caseGrowthStats.setCountry(caseGrowth.getKey());
			caseGrowthStats.setGrowthStats(caseGrowth.getValue());
			caseGrowthStatsList.add(caseGrowthStats);
		}

		coronaDataResponse.setCoronaCaseGrowthCountryStats(caseGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaCaseGrowthCountryStats::getCountry)).collect(Collectors.toList()));

	}

}
