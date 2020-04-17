package com.shadow.coronatracker.util.parsers;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class LocationCasesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, HttpResponse<String> response, StatisticsCollection statsCollection) {

		List<CSVRecord> csvRecords = CoronaTrackerUtil.convertResponseToCSVRecord(response);
		List<String> filterCountries = CoronaTrackerUtil.fetchDuplicateCountries(csvRecords);

		CSVRecord header = csvRecords.remove(0);

		List<LocationCases> locationCasesStats = new ArrayList<>();

		csvRecords.stream()
				.forEach(record -> locationCasesStats.add(locationCasesMapper(filterCountries, header, record)));

		statsCollection.setLocationCasesStats(locationCasesStats);
	}

	private LocationCases locationCasesMapper(List<String> filterCountries, CSVRecord header, CSVRecord record) {
		int lastRecord = CoronaTrackerUtil.fetchLastNonblankRecordIndex(record);

		LocationCases locationCases = new LocationCases();
		locationCases.setLocation(createLocation(record));
		locationCases.setCases(CoronaTrackerUtil.createLocationStatsMap(header, record, false));
		locationCases.setNewCases(CoronaTrackerUtil.createLocationStatsMap(header, record, true));
		locationCases.setCurrDate(header.get(lastRecord));
		locationCases.setFirstCase(CoronaTrackerUtil.isThisFirstReportedItem(filterCountries, record, lastRecord));
		return locationCases;
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
