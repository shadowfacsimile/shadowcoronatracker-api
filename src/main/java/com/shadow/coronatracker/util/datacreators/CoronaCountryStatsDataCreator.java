package com.shadow.coronatracker.util.datacreators;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaCountryStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaCountryStatsDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {

		coronaDataResponse.setCoronaCountryStats(coronaStatsCollection.getCoronaCountryStats().stream()
				.sorted(Comparator.comparingInt(CoronaCountryStats::getCases).reversed()).collect(Collectors.toList()));
	}
}
