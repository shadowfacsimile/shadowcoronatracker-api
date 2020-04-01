package com.shadow.coronatracker.util.datacreators;

import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.response.CoronaStatsResponse;

public interface DataCreator {

	void create(StatsCollection statsCollection, CoronaStatsResponse coronaDataResponse);

}
