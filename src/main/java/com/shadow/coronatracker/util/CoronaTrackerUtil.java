package com.shadow.coronatracker.util;

import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CoronaTrackerUtil {

	private static final Logger LOGGER = Logger.getLogger(CoronaTrackerUtil.class.getName());

	public static List<CSVRecord> convertResponseToCSVRecord(final HttpResponse<String> response) {
		StringReader reader = new StringReader(response.body());

		List<CSVRecord> csvRecords = null;
		Iterable<CSVRecord> records = null;
		
		try {
			records = CSVFormat.DEFAULT.withSkipHeaderRecord().parse(reader);
			csvRecords = StreamSupport.stream(records.spliterator(), false).collect(Collectors.toList());
		} catch (IOException e) {
			LOGGER.severe("Error in processing data : " + e.getMessage());
		}

		return csvRecords;
	}

	public static Map<String, Integer> createLocationStatsMap(CSVRecord header, CSVRecord record, boolean isNew) {

		Map<String, Integer> map = new LinkedHashMap<>();

		for (int i = 5; i < record.size(); i++) {
			Integer value = isNew ? Integer.valueOf(record.get(i)) - Integer.valueOf(record.get(i - 1))
					: Integer.valueOf(record.get(i));
			map.put(header.get(i), value);
		}

		return map;
	}

	public static List<String> fetchDuplicateCountries(List<CSVRecord> csvRecords) {
		
		List<String> countries = csvRecords.stream().map(stat -> stat.get(1)).collect(Collectors.toList());

		List<String> filterCountries = countries.stream().filter(i -> Collections.frequency(countries, i) > 1)
				.collect(Collectors.toList());

		return filterCountries;
	}
}
