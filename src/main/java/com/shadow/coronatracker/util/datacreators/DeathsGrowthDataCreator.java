package com.shadow.coronatracker.util.datacreators;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.deathgrowth.DeathsGrowth;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class DeathsGrowthDataCreator implements DataCreator {

	@Override
	public void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse) {

		List<DeathsGrowth> deathGrowthStats = new LinkedList<>();

		statsCollection.getTotalDeathsGrowthMap().entrySet().stream()
				.forEach(entry -> deathGrowthMapper(statsCollection, deathGrowthStats, entry));

		coronaDataResponse.setDeathsGrowthStats(deathGrowthStats.stream().collect(Collectors.toList()));
	}

	private void deathGrowthMapper(StatisticsCollection statsCollection, List<DeathsGrowth> deathGrowthStats,
			Entry<String, Integer> entry) {
		DeathsGrowth deathsGrowth = new DeathsGrowth();
		deathsGrowth.setDate(entry.getKey());
		deathsGrowth.setGrowth(entry.getValue());
		deathsGrowth.setDelta(statsCollection.getTotalNewDeathsGrowthMap().get(entry.getKey()));
		deathGrowthStats.add(deathsGrowth);
	}

}
