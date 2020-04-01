package com.shadow.coronatracker.util.datacreators;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.deathgrowth.DeathsGrowth;
import com.shadow.coronatracker.model.response.CoronaStatsResponse;

public class DeathsGrowthDataCreator implements DataCreator {

	@Override
	public void create(StatsCollection statsCollection, CoronaStatsResponse coronaDataResponse) {

		List<DeathsGrowth> deathGrowthStats = new LinkedList<>();

		for (Entry<String, Integer> entry : statsCollection.getTotalDeathsGrowthMap().entrySet()) {
			DeathsGrowth deathsGrowth = new DeathsGrowth();
			deathsGrowth.setDate(entry.getKey());
			deathsGrowth.setGrowth(entry.getValue());
			deathsGrowth.setDelta(statsCollection.getTotalNewDeathsGrowthMap().get(entry.getKey()));
			deathGrowthStats.add(deathsGrowth);
		}

		coronaDataResponse.setDeathsGrowthStats(deathGrowthStats.stream().collect(Collectors.toList()));

	}

}
