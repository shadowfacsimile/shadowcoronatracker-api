package com.shadow.coronatracker.model.enums;

import com.shadow.coronatracker.util.parsers.IndiaStatsParser;
import com.shadow.coronatracker.util.parsers.LocationCasesResponseParser;
import com.shadow.coronatracker.util.parsers.LocationDeathsResponseParser;
import com.shadow.coronatracker.util.parsers.LocationRecoveriesResponseParser;
import com.shadow.coronatracker.util.parsers.ResponseParser;

public enum Statistics {

	CASES("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv",
			new LocationCasesResponseParser()),
	// US_CASES("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_US.csv",
	// Arrays.asList(new USCasesResponseParser())),
	DEATHS("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv",
			new LocationDeathsResponseParser()),
	// US_DEATHS("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_US.csv",
	// Arrays.asList(new USDeathsResponseParser())),
	RECOVERIES(
			"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv",
			new LocationRecoveriesResponseParser()),
	INDIA("https://api.covid19india.org/states_daily.json", new IndiaStatsParser());

	private final String url;
	private final ResponseParser parser;

	private Statistics(String url, ResponseParser parser) {
		this.url = url;
		this.parser = parser;
	}

	public String getUrl() {
		return url;
	}

	public ResponseParser getParser() {
		return parser;
	}

}
