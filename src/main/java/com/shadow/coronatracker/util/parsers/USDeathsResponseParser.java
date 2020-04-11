package com.shadow.coronatracker.util.parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationDeaths;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class USDeathsResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, StatisticsCollection statsCollection) {

		List<String> filterCountries = CoronaTrackerUtil.fetchDuplicateCountries(csvRecords);

		CSVRecord header = csvRecords.remove(0);

		List<LocationDeaths> locationDeathsStats = statsCollection.getLocationDeathsStats().stream()
				.filter(stat -> stat.getLocation().getCountry() != "US").collect(Collectors.toList());

		csvRecords.stream().forEach(record -> locationDeathsStats
				.add(locationDeathsMapper(statsCollection, filterCountries, header, record)));

		statsCollection.setLocationDeathsStats(locationDeathsStats);
	}

	private LocationDeaths locationDeathsMapper(StatisticsCollection statsCollection, List<String> filterCountries,
			CSVRecord header, CSVRecord record) {
		int lastRecord = CoronaTrackerUtil.fetchLastNonblankRecordIndex(record);

		Location location = createLocation(record);
		LocationDeaths locationDeaths = statsCollection.getLocationDeathsStats().stream()
				.filter(stat -> stat.getLocation().equals(location)).findFirst().orElse(new LocationDeaths());
		locationDeaths.setLocation(location);
		locationDeaths.setDeaths(createDeathsMap(header, record, false, locationDeaths));
		locationDeaths.setNewDeaths(createNewDeathsMap(header, record, true, locationDeaths));
		locationDeaths.setCurrDate(header.get(lastRecord));
		locationDeaths.setFirstDeath(CoronaTrackerUtil.isThisFirstReportedItem(filterCountries, record, lastRecord));
		return locationDeaths;
	}

	private Location createLocation(CSVRecord record) {
		Location location = new Location();
		location.setState(record.get(6));
		location.setCountry(record.get(7));
		return location;
	}

	private Map<String, Integer> createDeathsMap(CSVRecord header, CSVRecord record, boolean isDelta,
			LocationDeaths locationDeaths) {

		Map<String, Integer> map = locationDeaths.getDeaths() == null ? new HashMap<>() : locationDeaths.getDeaths();

		for (int i = 12; i < record.size(); i++) {
			Integer curr = map.get(header.get(i)) == null ? Integer.valueOf(record.get(i))
					: map.get(header.get(i)) + Integer.valueOf(record.get(i));
			Integer prev = map.get(header.get(i)) == null ? Integer.valueOf(record.get(i - 1))
					: map.get(header.get(i)) + Integer.valueOf(record.get(i - 1));
			Integer value = isDelta ? curr - prev : curr;
			map.put(header.get(i), value);
		}

		return map;
	}

	private Map<String, Integer> createNewDeathsMap(CSVRecord header, CSVRecord record, boolean isDelta,
			LocationDeaths locationDeaths) {

		Map<String, Integer> map = locationDeaths.getNewDeaths() == null ? new HashMap<>()
				: locationDeaths.getNewDeaths();

		for (int i = 12; i < record.size(); i++) {
			Integer curr = Integer.valueOf(record.get(i));
			Integer prev = Integer.valueOf(record.get(i - 1));
			Integer value = isDelta ? curr - prev : curr;
			map.put(header.get(i), value);
		}

		return map;
	}

}
