package com.shadow.coronatracker.util.parsers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;

import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.LocationCases;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class USCasesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, StatisticsCollection statsCollection) {

		List<String> filterCountries = CoronaTrackerUtil.fetchDuplicateCountries(csvRecords);

		CSVRecord header = csvRecords.remove(0);

		List<LocationCases> locationCasesStats = statsCollection.getLocationCasesStats().stream()
				.filter(stat -> stat.getLocation().getCountry() != "US").collect(Collectors.toList());

		csvRecords.stream().forEach(record -> locationCasesStats
				.add(locationCasesMapper(statsCollection, filterCountries, header, record)));

		statsCollection.setLocationCasesStats(locationCasesStats);
	}

	private LocationCases locationCasesMapper(StatisticsCollection statsCollection, List<String> filterCountries,
			CSVRecord header, CSVRecord record) {
		int lastRecord = CoronaTrackerUtil.fetchLastNonblankRecordIndex(record);

		Location location = createLocation(record);
		LocationCases locationCases = statsCollection.getLocationCasesStats().stream()
				.filter(stat -> stat.getLocation().equals(location)).findFirst().orElse(new LocationCases());
		locationCases.setLocation(location);
		locationCases.setCases(createCasesMap(header, record, false, locationCases));
		locationCases.setNewCases(createNewCasesMap(header, record, true, locationCases));
		locationCases.setCurrDate(header.get(lastRecord));
		locationCases.setFirstCase(CoronaTrackerUtil.isThisFirstReportedItem(filterCountries, record, lastRecord));
		return locationCases;
	}

	private Location createLocation(CSVRecord record) {
		Location location = new Location();
		location.setState(record.get(6));
		location.setCountry(record.get(7));
		return location;
	}

	private Map<String, Integer> createCasesMap(CSVRecord header, CSVRecord record, boolean isDelta,
			LocationCases locationCases) {

		Map<String, Integer> map = locationCases.getCases() == null ? new HashMap<>() : locationCases.getCases();

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

	private Map<String, Integer> createNewCasesMap(CSVRecord header, CSVRecord record, boolean isDelta,
			LocationCases locationCases) {

		Map<String, Integer> map = locationCases.getNewCases() == null ? new HashMap<>() : locationCases.getNewCases();

		for (int i = 12; i < record.size(); i++) {
			Integer curr = Integer.valueOf(record.get(i));
			Integer prev = Integer.valueOf(record.get(i - 1));
			Integer value = isDelta ? curr - prev : curr;
			map.put(header.get(i), value);
		}

		return map;
	}

}
