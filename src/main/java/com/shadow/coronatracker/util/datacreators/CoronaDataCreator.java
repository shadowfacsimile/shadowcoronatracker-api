package com.shadow.coronatracker.util.datacreators;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.response.CoronaDataResponse;

public interface CoronaDataCreator {

	void create(final CoronaStatsCollection coronaStatsCollection, final CoronaDataResponse coronaDataResponse);

}
