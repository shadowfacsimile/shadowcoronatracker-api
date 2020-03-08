package com.shadow.coronatracker.util;

import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.shadow.coronatracker.model.Statistics;

public class CoronaTrackerUtil {

	public static final String CORONA_CASES_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	public static final String CORONA_DEATHS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
	public static final String CORONA_RECOVERIES_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";

	private static final Map<Statistics, String> urls = new LinkedHashMap<>();

	static {
		urls.put(Statistics.CASES, CoronaTrackerUtil.CORONA_CASES_DATA_URL);
		urls.put(Statistics.DEATHS, CoronaTrackerUtil.CORONA_DEATHS_DATA_URL);
		urls.put(Statistics.RECOVERIES, CoronaTrackerUtil.CORONA_RECOVERIES_DATA_URL);
	}

	private static final Map<Statistics, ResponseParser> parsers = new LinkedHashMap<>();

	static {
		parsers.put(Statistics.CASES, new CoronaCasesResponseParser());
		parsers.put(Statistics.DEATHS, new CoronaDeathsResponseParser());
		parsers.put(Statistics.RECOVERIES, new CoronaRecoveriesResponseParser());
	}

	public static Map<Statistics, String> getUrls() {
		return urls;
	}

	public static Map<Statistics, ResponseParser> getParsers() {
		return parsers;
	}

	public static Iterable<CSVRecord> convertResponseToCSVRecord(final HttpResponse<String> response) throws IOException {
		StringReader reader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		return records;
	}

}
