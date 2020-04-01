package com.shadow.coronatracker.util.datacreators;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.casegrowth.CasesGrowth;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CasesGrowthDataCreator implements DataCreator {

	@Override
	public void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse) {

		List<CasesGrowth> caseGrowthStats = new LinkedList<>();

		statsCollection.getTotalCasesGrowthMap().entrySet().stream()
				.forEach(entry -> caseGrowthMapper(statsCollection, caseGrowthStats, entry));

		coronaDataResponse.setCasesGrowthStats(caseGrowthStats.stream().collect(Collectors.toList()));
	}

	private void caseGrowthMapper(StatisticsCollection statsCollection, List<CasesGrowth> caseGrowthStats,
			Entry<String, Integer> entry) {
		CasesGrowth casesGrowth = new CasesGrowth();
		casesGrowth.setDate(entry.getKey());
		casesGrowth.setGrowth(entry.getValue());
		casesGrowth.setDelta(statsCollection.getTotalNewCasesGrowthMap().get(entry.getKey()));
		caseGrowthStats.add(casesGrowth);
	}

}
