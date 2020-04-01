package com.shadow.coronatracker.util.datacreators;

import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public interface DataCreator {

	void create(StatisticsCollection statsCollection, CoronaDataResponse coronaDataResponse);

}
