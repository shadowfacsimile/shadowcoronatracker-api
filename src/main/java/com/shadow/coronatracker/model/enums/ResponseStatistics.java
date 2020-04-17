package com.shadow.coronatracker.model.enums;

import com.shadow.coronatracker.util.datacreators.CasesGrowthDataCreator;
import com.shadow.coronatracker.util.datacreators.CountryCasesGrowthDataCreator;
import com.shadow.coronatracker.util.datacreators.CountryDeathsGrowthDataCreator;
import com.shadow.coronatracker.util.datacreators.DataCreator;
import com.shadow.coronatracker.util.datacreators.DeathsGrowthDataCreator;
import com.shadow.coronatracker.util.datacreators.IndiaStatsDataCreator;
import com.shadow.coronatracker.util.datacreators.LocationStatsDataCreator;
import com.shadow.coronatracker.util.datacreators.SummaryDataCreator;

public enum ResponseStatistics {

	SUMMARY(new SummaryDataCreator()), 
	LOCATION(new LocationStatsDataCreator()),
	CASE_GROWTH_COUNTRY(new CountryCasesGrowthDataCreator()), 
	CASE_GROWTH(new CasesGrowthDataCreator()),
	DEATH_GROWTH_COUNTRY(new CountryDeathsGrowthDataCreator()), 
	DEATH_GROWTH(new DeathsGrowthDataCreator()),
	INDIA_STATS(new IndiaStatsDataCreator());

	private final DataCreator dataCreator;

	private ResponseStatistics(DataCreator dataCreator) {
		this.dataCreator = dataCreator;
	}

	public DataCreator getDataCreator() {
		return dataCreator;
	}

}
