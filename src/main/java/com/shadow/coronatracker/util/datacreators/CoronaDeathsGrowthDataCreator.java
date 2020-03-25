package com.shadow.coronatracker.util.datacreators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthStats;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaDeathsGrowthDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaDeathGrowthStats> deathGrowthStatsList = new ArrayList<>();

		int delta = 0;

		for (Entry<Date, Integer> deathGrowth : coronaStatsCollection.getCoronaDeathsGrowth().entrySet()) {
			CoronaDeathGrowthStats deathGrowthStats = new CoronaDeathGrowthStats();
			deathGrowthStats.setDate(deathGrowth.getKey());
			deathGrowthStats.setGrowth(deathGrowth.getValue());
			deathGrowthStats.setDelta(deathGrowth.getValue() - delta);
			deathGrowthStatsList.add(deathGrowthStats);
			delta = deathGrowth.getValue();
		}

		coronaDataResponse.setCoronaDeathGrowthStats(deathGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaDeathGrowthStats::getDate)).collect(Collectors.toList()));
	}

}
