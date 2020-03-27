package com.shadow.coronatracker.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaDeathsStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.enums.Statistics;

public class CoronaDeathsResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaStatsCollection) {
		
		List<CoronaDeathsStats> coronaDeathsStats = new ArrayList<>();

		for (CSVRecord record : csvRecords) {
			int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

			CoronaDeathsStats deathsStats = new CoronaDeathsStats();
			Location location = new Location();
			location.setState(record.get(0));
			location.setCountry(record.get(1));
			location.setLatitude(Float.valueOf(record.get(2)));
			location.setLongitude(Float.valueOf(record.get(3)));
			deathsStats.setLocation(location);
			deathsStats.setDeaths(Integer.valueOf(record.get(lastRecord)));
			deathsStats.setDeathsSinceYesterday(deathsStats.getDeaths() - Integer.valueOf(record.get(lastRecord - 1)));
			deathsStats.setFirstDeathReported(
					record.get(lastRecord - 1).equalsIgnoreCase("0") && !record.get(lastRecord).equalsIgnoreCase("0"));
			coronaDeathsStats.add(deathsStats);
		}

		coronaStatsCollection.setCoronaDeathsStats(coronaDeathsStats);
	}
}
