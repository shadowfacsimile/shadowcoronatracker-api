package com.shadow.coronatracker.util.datacreators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthCountry;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowth;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class CoronaDeathsGrowthCountryDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {
		List<CoronaDeathGrowthCountry> deathGrowthStatsList = new ArrayList<>();

		for (Entry<String, List<CoronaDeathGrowth>> deathGrowth : coronaStatsCollection.getCoronaDeathsGrowthCountry()
				.entrySet()) {
			CoronaDeathGrowthCountry deathGrowthStats = new CoronaDeathGrowthCountry();
			deathGrowthStats.setCountry(deathGrowth.getKey());
			deathGrowthStats.setGrowthStats(deathGrowth.getValue());
			deathGrowthStatsList.add(deathGrowthStats);
		}

		coronaDataResponse.setCoronaDeathGrowthCountriesStats(deathGrowthStatsList.stream()
				.sorted(Comparator.comparing(CoronaDeathGrowthCountry::getCountry)).collect(Collectors.toList()));

	}

}
