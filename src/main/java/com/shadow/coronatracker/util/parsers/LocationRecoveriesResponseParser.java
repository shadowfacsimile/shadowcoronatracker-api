package com.shadow.coronatracker.util.parsers;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationRecoveries;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class LocationRecoveriesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, HttpResponse<String> response, StatisticsCollection statsCollection) {

		List<CSVRecord> csvRecords = CoronaTrackerUtil.convertResponseToCSVRecord(response);

		CSVRecord header = csvRecords.remove(0);

		List<LocationRecoveries> locationRecoveriesStats = new ArrayList<>();

		csvRecords.stream().forEach(record -> locationRecoveriesStats.add(locationRecoveriesMapper(header, record)));

		statsCollection.setLocationRecoveriesStats(locationRecoveriesStats);
	}

	private LocationRecoveries locationRecoveriesMapper(CSVRecord header, CSVRecord record) {
		int lastRecord = CoronaTrackerUtil.fetchLastNonblankRecordIndex(record);

		LocationRecoveries locationRecoveries = new LocationRecoveries();
		locationRecoveries.setLocation(createLocation(record));
		locationRecoveries.setRecoveries(CoronaTrackerUtil.createLocationStatsMap(header, record, false));
		locationRecoveries.setCurrDate(header.get(lastRecord));
		return locationRecoveries;
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
