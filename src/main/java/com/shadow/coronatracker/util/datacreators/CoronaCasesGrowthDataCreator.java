package com.shadow.coronatracker.util.datacreators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowth;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaCasesGrowthDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaCaseGrowth> caseGrowthStatsList = new ArrayList<>();
		
		int delta = 0;
		
		for (Entry<Date, Integer> caseGrowth : coronaStatsCollection.getCoronaCasesGrowth().entrySet()) {
			CoronaCaseGrowth caseGrowthStats = new CoronaCaseGrowth();
			caseGrowthStats.setDate(caseGrowth.getKey());
			caseGrowthStats.setGrowth(caseGrowth.getValue());
			caseGrowthStats.setDelta(caseGrowth.getValue() - delta);
			caseGrowthStatsList.add(caseGrowthStats);
			delta = caseGrowth.getValue();
		}

		coronaDataResponse.setCoronaCaseGrowthStats(caseGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaCaseGrowth::getDate)).collect(Collectors.toList()));
	}

}
