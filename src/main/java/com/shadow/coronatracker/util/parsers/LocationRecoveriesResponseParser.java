package com.shadow.coronatracker.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationRecoveries;
import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class LocationRecoveriesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, StatsCollection statsCollection) {

		CSVRecord header = csvRecords.remove(0);

		List<LocationRecoveries> locationRecoveriesStats = new ArrayList<>();

		for (CSVRecord record : csvRecords) {
			int lastRecord = record.size() - 1;
			lastRecord = StringUtils.isBlank(record.get(lastRecord)) ? lastRecord - 1 : lastRecord;

			LocationRecoveries locationRecoveries = new LocationRecoveries();
			locationRecoveries.setLocation(createLocation(record));
			locationRecoveries.setRecoveries(CoronaTrackerUtil.createLocationStatsMap(header, record, false));
			locationRecoveries.setCurrDate(header.get(lastRecord));
			locationRecoveriesStats.add(locationRecoveries);
		}

		statsCollection.setLocationRecoveriesStats(locationRecoveriesStats);
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
