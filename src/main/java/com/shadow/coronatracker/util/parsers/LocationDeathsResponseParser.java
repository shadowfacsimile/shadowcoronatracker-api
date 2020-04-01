package com.shadow.coronatracker.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationDeaths;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class LocationDeathsResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, StatisticsCollection statsCollection) {

		List<String> filterCountries = CoronaTrackerUtil.fetchDuplicateCountries(csvRecords);

		CSVRecord header = csvRecords.remove(0);

		List<LocationDeaths> locationDeathsStats = new ArrayList<>();

		csvRecords.stream()
				.forEach(record -> locationDeathsStats.add(locationDeathsMapper(filterCountries, header, record)));

		statsCollection.setLocationDeathsStats(locationDeathsStats);
	}

	private LocationDeaths locationDeathsMapper(List<String> filterCountries, CSVRecord header, CSVRecord record) {
		int lastRecord = CoronaTrackerUtil.fetchLastNonblankRecordIndex(record);

		LocationDeaths locationDeaths = new LocationDeaths();
		locationDeaths.setLocation(createLocation(record));
		locationDeaths.setDeaths(CoronaTrackerUtil.createLocationStatsMap(header, record, false));
		locationDeaths.setNewDeaths(CoronaTrackerUtil.createLocationStatsMap(header, record, true));
		locationDeaths.setCurrDate(header.get(lastRecord));
		locationDeaths.setFirstDeath(isThisFirstReportedDeath(filterCountries, record, lastRecord));
		return locationDeaths;
	}

	private boolean isThisFirstReportedDeath(List<String> filterCountries, CSVRecord record, int lastRecord) {
		return !filterCountries.contains(record.get(1)) && record.get(lastRecord - 1).equals("0")
				&& !record.get(lastRecord).equals("0");
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
