package com.shadow.coronatracker.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaCaseGrowthFactorStats;
import com.shadow.coronatracker.model.CoronaDataResponse;
import com.shadow.coronatracker.model.CoronaStatsCollection;

public class CoronaCasesGrowthFactorDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaCaseGrowthFactorStats> caseGrowthStatsList = new ArrayList<>();

		for (Entry<Date, Double> caseGrowth : coronaStatsCollection.getCoronaCasesGrowthFactors().entrySet()) {
			CoronaCaseGrowthFactorStats caseGrowthStats = new CoronaCaseGrowthFactorStats();
			caseGrowthStats.setDate(caseGrowth.getKey());
			caseGrowthStats.setGrowthFactor(caseGrowth.getValue());
			caseGrowthStatsList.add(caseGrowthStats);
		}

		coronaDataResponse.setCoronaCaseGrowthFactorStats(caseGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaCaseGrowthFactorStats::getDate)).collect(Collectors.toList()));
	}

}
