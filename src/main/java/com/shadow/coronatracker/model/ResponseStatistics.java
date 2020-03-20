package com.shadow.coronatracker.model;

import com.shadow.coronatracker.util.CoronaCasesDataCreator;
import com.shadow.coronatracker.util.CoronaCasesGrowthCountryDataCreator;
import com.shadow.coronatracker.util.CoronaCasesGrowthDataCreator;
import com.shadow.coronatracker.util.CoronaCasesGrowthFactorDataCreator;
import com.shadow.coronatracker.util.CoronaCasesSummaryDataCreator;
import com.shadow.coronatracker.util.CoronaDataCreator;

public enum ResponseStatistics {

	CASES(new CoronaCasesDataCreator()), SUMMARY(new CoronaCasesSummaryDataCreator()),
	GROWTH(new CoronaCasesGrowthDataCreator()), GROWTH_FACTOR(new CoronaCasesGrowthFactorDataCreator()),
	GROWTH_COUNTRY(new CoronaCasesGrowthCountryDataCreator());

	private final CoronaDataCreator coronaGrowthDataCreator;

	private ResponseStatistics(CoronaDataCreator coronaGrowthDataCreator) {
		this.coronaGrowthDataCreator = coronaGrowthDataCreator;
	}

	public CoronaDataCreator getCoronaGrowthDataCreator() {
		return coronaGrowthDataCreator;
	}

}
