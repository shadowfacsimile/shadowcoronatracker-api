package com.shadow.coronatracker.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaCaseGrowthStats;
import com.shadow.coronatracker.model.CoronaDataResponse;
import com.shadow.coronatracker.model.CoronaStatsCollection;

public class CoronaCasesGrowthDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaCaseGrowthStats> caseGrowthStatsList = new ArrayList<>();

		for (Entry<Date, Integer> caseGrowth : coronaStatsCollection.getCoronaCasesGrowth().entrySet()) {
			CoronaCaseGrowthStats caseGrowthStats = new CoronaCaseGrowthStats();
			caseGrowthStats.setDate(caseGrowth.getKey());
			caseGrowthStats.setGrowth(caseGrowth.getValue());
			caseGrowthStatsList.add(caseGrowthStats);
		}

		coronaDataResponse.setCoronaCaseGrowthStats(caseGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaCaseGrowthStats::getDate)).collect(Collectors.toList()));
	}

}
