package com.shadow.coronatracker.util.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaCasesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.enums.Statistics;

public class CoronaCasesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaStatsCollection) {

		List<CoronaCasesStats> coronaCasesStats = new ArrayList<>();

		List<String> countries = csvRecords.stream().map(stat -> stat.get(1)).collect(Collectors.toList());
		List<String> filterCountries = countries.stream().filter(i -> Collections.frequency(countries, i) > 1)
				.collect(Collectors.toList());

		for (CSVRecord record : csvRecords) {
			int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

			CoronaCasesStats casesStats = new CoronaCasesStats();
			Location location = new Location();
			location.setState(record.get(0));
			location.setCountry(record.get(1));
			location.setLatitude(Float.valueOf(record.get(2)));
			location.setLongitude(Float.valueOf(record.get(3)));
			casesStats.setLocation(location);
			casesStats.setCases(Integer.valueOf(record.get(lastRecord)));
			casesStats.setCasesSinceYesterday(casesStats.getCases() - Integer.valueOf(record.get(lastRecord - 1)));
			casesStats.setFirstCaseReported(
					!filterCountries.contains(record.get(1)) && record.get(lastRecord - 1).equalsIgnoreCase("0")
							&& !record.get(lastRecord).equalsIgnoreCase("0"));
			coronaCasesStats.add(casesStats);
		}

		coronaStatsCollection.setCoronaCasesStats(coronaCasesStats);
	}
}
