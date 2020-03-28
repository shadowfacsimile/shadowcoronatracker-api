package com.shadow.coronatracker.util.datacreators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthCountry;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowth;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaCasesGrowthCountryDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaCaseGrowthCountry> caseGrowthStatsList = new ArrayList<>();

		for (Entry<String, List<CoronaCaseGrowth>> caseGrowth : coronaStatsCollection.getCoronaCasesGrowthCountry()
				.entrySet()) {
			CoronaCaseGrowthCountry caseGrowthStats = new CoronaCaseGrowthCountry();
			caseGrowthStats.setCountry(caseGrowth.getKey());
			caseGrowthStats.setGrowthStats(caseGrowth.getValue());
			caseGrowthStatsList.add(caseGrowthStats);
		}

		coronaDataResponse.setCoronaCaseGrowthCountriesStats(caseGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaCaseGrowthCountry::getCountry)).collect(Collectors.toList()));

	}

}
