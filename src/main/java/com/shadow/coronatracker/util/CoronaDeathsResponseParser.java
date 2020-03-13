package com.shadow.coronatracker.util;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaDeathsStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;

public class CoronaDeathsResponseParser implements ResponseParser {

	@Override
	public void parse(HttpResponse<String> response, CoronaStatsCollection coronaSummaryStats) throws IOException {

		List<CoronaDeathsStats> coronaDeathsStats = new ArrayList<>();

		for (CSVRecord record : CoronaTrackerUtil.convertResponseToCSVRecord(response)) {
			CoronaDeathsStats deathsStats = new CoronaDeathsStats();
			deathsStats.setState(record.get(0));
			deathsStats.setCountry(record.get(1));
			deathsStats.setLatitude(Float.valueOf(record.get(2)));
			deathsStats.setLongitude(Float.valueOf(record.get(3)));
			String deaths = StringUtils.isBlank(record.get(record.size() - 1)) ? "0" : record.get(record.size() - 1);
			deathsStats.setDeaths(Integer.valueOf(deaths));
			String deathsSinceYesterday = StringUtils.isBlank(record.get(record.size() - 2)) ? "0"
					: record.get(record.size() - 2);
			deathsStats.setDeathsSinceYesterday(deathsStats.getDeaths() - Integer.valueOf(deathsSinceYesterday));
			deathsStats.setFirstDeathReported(record.get(record.size() - 2).equalsIgnoreCase("0")
					&& StringUtils.isNotBlank(record.get(record.size() - 1))
					&& !record.get(record.size() - 1).equalsIgnoreCase("0"));
			coronaDeathsStats.add(deathsStats);
		}

		coronaSummaryStats.setCoronaDeathsStats(coronaDeathsStats);
	}

}
