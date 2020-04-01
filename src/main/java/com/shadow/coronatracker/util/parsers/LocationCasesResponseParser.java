package com.shadow.coronatracker.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class LocationCasesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, StatsCollection statsCollection) {

		List<String> filterCountries = CoronaTrackerUtil.fetchDuplicateCountries(csvRecords);

		CSVRecord header = csvRecords.remove(0);

		List<LocationCases> locationCasesStats = new ArrayList<>();

		for (CSVRecord record : csvRecords) {
			int lastRecord = record.size() - 1;
			lastRecord = StringUtils.isBlank(record.get(lastRecord)) ? lastRecord - 1 : lastRecord;

			LocationCases locationCases = new LocationCases();
			locationCases.setLocation(createLocation(record));
			locationCases.setCases(CoronaTrackerUtil.createLocationStatsMap(header, record, false));
			locationCases.setNewCases(CoronaTrackerUtil.createLocationStatsMap(header, record, true));
			locationCases.setCurrDate(header.get(lastRecord));
			locationCases.setCasesReported(!filterCountries.contains(record.get(1))
					&& record.get(lastRecord - 1).equals("0") && !record.get(lastRecord).equals("0"));
			locationCasesStats.add(locationCases);
		}

		statsCollection.setLocationCasesStats(locationCasesStats);
	}

	private Location createLocation(CSVRecord record) {
		Location location = new Location();
		location.setState(record.get(0));
		location.setCountry(record.get(1));
		location.setLatitude(Float.valueOf(record.get(2)));
		location.setLongitude(Float.valueOf(record.get(3)));
		return location;
	}

}
