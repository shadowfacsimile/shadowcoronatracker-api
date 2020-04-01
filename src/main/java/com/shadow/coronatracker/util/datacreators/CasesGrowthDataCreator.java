package com.shadow.coronatracker.util.datacreators;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.casegrowth.CasesGrowth;
import com.shadow.coronatracker.model.response.CoronaStatsResponse;

public class CasesGrowthDataCreator implements DataCreator {

	@Override
	public void create(StatsCollection statsCollection, CoronaStatsResponse coronaDataResponse) {

		List<CasesGrowth> caseGrowthStats = new LinkedList<>();

		for (Entry<String, Integer> entry : statsCollection.getTotalCasesGrowthMap().entrySet()) {
			CasesGrowth casesGrowth = new CasesGrowth();
			casesGrowth.setDate(entry.getKey());
			casesGrowth.setGrowth(entry.getValue());
			casesGrowth.setDelta(statsCollection.getTotalNewCasesGrowthMap().get(entry.getKey()));
			caseGrowthStats.add(casesGrowth);
		}

		coronaDataResponse.setCasesGrowthStats(caseGrowthStats.stream().collect(Collectors.toList()));

	}

}
