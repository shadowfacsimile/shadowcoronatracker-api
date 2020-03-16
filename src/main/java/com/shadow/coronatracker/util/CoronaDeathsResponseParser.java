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
	public void parse(HttpResponse<String> response, CoronaStatsCollection coronaStatsCollection) throws IOException {

		List<CoronaDeathsStats> coronaDeathsStats = new ArrayList<>();

		for (CSVRecord record : CoronaTrackerUtil.convertResponseToCSVRecord(response)) {
			int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

			CoronaDeathsStats deathsStats = new CoronaDeathsStats();
			deathsStats.setState(record.get(0));
			deathsStats.setCountry(record.get(1));
			deathsStats.setLatitude(Float.valueOf(record.get(2)));
			deathsStats.setLongitude(Float.valueOf(record.get(3)));
			deathsStats.setDeaths(Integer.valueOf(record.get(lastRecord)));
			deathsStats.setDeathsSinceYesterday(deathsStats.getDeaths() - Integer.valueOf(record.get(lastRecord - 1)));
			deathsStats.setFirstDeathReported(
					record.get(lastRecord - 1).equalsIgnoreCase("0") && !record.get(lastRecord).equalsIgnoreCase("0"));
			coronaDeathsStats.add(deathsStats);
		}

		coronaStatsCollection.setCoronaDeathsStats(coronaDeathsStats);
	}

}
