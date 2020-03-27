package com.shadow.coronatracker.model.enums;

import com.shadow.coronatracker.util.datacreators.CoronaLocationStatsDataCreator;
import com.shadow.coronatracker.util.datacreators.CoronaCasesGrowthCountryDataCreator;
import com.shadow.coronatracker.util.datacreators.CoronaCasesGrowthDataCreator;
import com.shadow.coronatracker.util.datacreators.CoronaCasesGrowthFactorDataCreator;
import com.shadow.coronatracker.util.datacreators.CoronaCasesSummaryDataCreator;
import com.shadow.coronatracker.util.datacreators.CoronaDataCreator;
import com.shadow.coronatracker.util.datacreators.CoronaDeathsGrowthCountryDataCreator;
import com.shadow.coronatracker.util.datacreators.CoronaDeathsGrowthDataCreator;

public enum ResponseStatistics {

	CASES(new CoronaLocationStatsDataCreator()), 
	SUMMARY(new CoronaCasesSummaryDataCreator()),
	CASE_GROWTH(new CoronaCasesGrowthDataCreator()),
	CASE_GROWTH_COUNTRY(new CoronaCasesGrowthCountryDataCreator()),
	DEATH_GROWTH(new CoronaDeathsGrowthDataCreator()),
	DEATH_GROWTH_COUNTRY(new CoronaDeathsGrowthCountryDataCreator()),
	GROWTH_FACTOR(new CoronaCasesGrowthFactorDataCreator());

	private final CoronaDataCreator coronaDataCreator;

	private ResponseStatistics(CoronaDataCreator coronaDataCreator) {
		this.coronaDataCreator = coronaDataCreator;
	}

	public CoronaDataCreator getCoronaDataCreator() {
		return coronaDataCreator;
	}

}
