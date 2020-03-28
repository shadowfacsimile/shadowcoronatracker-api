package com.shadow.coronatracker.util.datacreators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthFactor;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaCasesGrowthFactorDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaCaseGrowthFactor> caseGrowthStatsList = new ArrayList<>();

		for (Entry<Date, Double> caseGrowth : coronaStatsCollection.getCoronaCasesGrowthFactors().entrySet()) {
			CoronaCaseGrowthFactor caseGrowthStats = new CoronaCaseGrowthFactor();
			caseGrowthStats.setDate(caseGrowth.getKey());
			caseGrowthStats.setGrowthFactor(caseGrowth.getValue());
			caseGrowthStatsList.add(caseGrowthStats);
		}

		coronaDataResponse.setCoronaCaseGrowthFactorStats(caseGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaCaseGrowthFactor::getDate)).collect(Collectors.toList()));
	}

}
