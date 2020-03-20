package com.shadow.coronatracker.util;

import com.shadow.coronatracker.model.CoronaDataResponse;
import com.shadow.coronatracker.model.CoronaStatsCollection;

public interface CoronaDataCreator {

	void create(final CoronaStatsCollection coronaStatsCollection, final CoronaDataResponse coronaDataResponse);

}
