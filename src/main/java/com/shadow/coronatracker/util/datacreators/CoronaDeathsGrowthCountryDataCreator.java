package com.shadow.coronatracker.util.datacreators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthCountryStats;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthStats;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaDeathsGrowthCountryDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaDeathGrowthCountryStats> deathGrowthStatsList = new ArrayList<>();

		for (Entry<String, List<CoronaDeathGrowthStats>> deathGrowth : coronaStatsCollection.getCoronaDeathsGrowthCountry()
				.entrySet()) {
			CoronaDeathGrowthCountryStats deathGrowthStats = new CoronaDeathGrowthCountryStats();
			deathGrowthStats.setCountry(deathGrowth.getKey());
			deathGrowthStats.setGrowthStats(deathGrowth.getValue());
			deathGrowthStatsList.add(deathGrowthStats);
		}

		coronaDataResponse.setCoronaDeathGrowthCountryStats(deathGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaDeathGrowthCountryStats::getCountry)).collect(Collectors.toList()));

	}

}
