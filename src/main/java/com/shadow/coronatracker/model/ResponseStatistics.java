package com.shadow.coronatracker.model;

import com.shadow.coronatracker.util.CoronaCasesDataCreator;
import com.shadow.coronatracker.util.CoronaCasesGrowthCountryDataCreator;
import com.shadow.coronatracker.util.CoronaCasesGrowthDataCreator;
import com.shadow.coronatracker.util.CoronaCasesGrowthFactorDataCreator;
import com.shadow.coronatracker.util.CoronaCasesSummaryDataCreator;
import com.shadow.coronatracker.util.CoronaDataCreator;
import com.shadow.coronatracker.util.CoronaDeathsGrowthCountryDataCreator;
import com.shadow.coronatracker.util.CoronaDeathsGrowthDataCreator;

public enum ResponseStatistics {

	CASES(new CoronaCasesDataCreator()), SUMMARY(new CoronaCasesSummaryDataCreator()),
	CASE_GROWTH(new CoronaCasesGrowthDataCreator()),
	CASE_GROWTH_COUNTRY(new CoronaCasesGrowthCountryDataCreator()),
	DEATH_GROWTH(new CoronaDeathsGrowthDataCreator()),
	DEATH_GROWTH_COUNTRY(new CoronaDeathsGrowthCountryDataCreator()),
	GROWTH_FACTOR(new CoronaCasesGrowthFactorDataCreator());

	private final CoronaDataCreator coronaGrowthDataCreator;

	private ResponseStatistics(CoronaDataCreator coronaGrowthDataCreator) {
		this.coronaGrowthDataCreator = coronaGrowthDataCreator;
	}

	public CoronaDataCreator getCoronaGrowthDataCreator() {
		return coronaGrowthDataCreator;
	}

}
