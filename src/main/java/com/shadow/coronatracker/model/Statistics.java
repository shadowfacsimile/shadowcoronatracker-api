package com.shadow.coronatracker.model;

import com.shadow.coronatracker.util.CoronaCasesResponseParser;
import com.shadow.coronatracker.util.CoronaDeathsResponseParser;
import com.shadow.coronatracker.util.CoronaGrowthResponseParser;
import com.shadow.coronatracker.util.CoronaRecoveriesResponseParser;
import com.shadow.coronatracker.util.ResponseParser;

public enum Statistics {

	CASES("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv",
			new CoronaCasesResponseParser()),
	DEATHS("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv",
			new CoronaDeathsResponseParser()),
	RECOVERIES(
			"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv",
			new CoronaRecoveriesResponseParser()),
	CASE_GROWTH(
			"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv",
			new CoronaGrowthResponseParser()),
	CASE_GROWTH_COUNTRY(
			"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv",
			new CoronaGrowthResponseParser()),
	DEATH_GROWTH(
			"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv",
			new CoronaGrowthResponseParser()),
	DEATH_GROWTH_COUNTRY(
			"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv",
			new CoronaGrowthResponseParser());

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
