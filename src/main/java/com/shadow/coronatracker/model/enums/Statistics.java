package com.shadow.coronatracker.model.enums;

import java.util.Arrays;
import java.util.List;

import com.shadow.coronatracker.util.parsers.CoronaCasesGrowthResponseParser;
import com.shadow.coronatracker.util.parsers.CoronaCasesResponseParser;
import com.shadow.coronatracker.util.parsers.CoronaDeathsGrowthResponseParser;
import com.shadow.coronatracker.util.parsers.CoronaDeathsResponseParser;
import com.shadow.coronatracker.util.parsers.CoronaRecoveriesResponseParser;
import com.shadow.coronatracker.util.parsers.ResponseParser;

public enum Statistics {

	CASES("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv",
			Arrays.asList(new CoronaCasesResponseParser(), new CoronaCasesGrowthResponseParser())),
	DEATHS("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv",
			Arrays.asList(new CoronaDeathsResponseParser(), new CoronaDeathsGrowthResponseParser())),
	RECOVERIES(
			"https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv",
			Arrays.asList(new CoronaRecoveriesResponseParser()));

	private final String url;
	private final List<ResponseParser> parsers;

	private Statistics(String url, List<ResponseParser> parsers) {
		this.url = url;
		this.parsers = parsers;
	}

	public String getUrl() {
		return url;
	}

	public List<ResponseParser> getParsers() {
		return parsers;
	}

}
