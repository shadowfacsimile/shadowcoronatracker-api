package com.shadow.coronatracker.util.datacreators;

import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public class IndiaStatsDataCreator implements DataCreator {

	@Override
	public void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse) {
		
		coronaDataResponse.setIndiaStats(statsCollection.getIndiaStats());

	}

}
