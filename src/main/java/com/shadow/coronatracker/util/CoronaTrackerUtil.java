package com.shadow.coronatracker.util;

import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CoronaTrackerUtil {

	public static final List<String> filterCountries = Arrays.asList("Australia", "Hong Kong SAR",
			"Iran (Islamic Republic of)", "Republic of Korea", "Mainland China", "US", "France", "Canada", "UK",
			"United States", "United Kingdom");

	public static List<CSVRecord> convertResponseToCSVRecord(final HttpResponse<String> response) {
		StringReader reader = new StringReader(response.body());
		List<CSVRecord> csvRecords = null;
		Iterable<CSVRecord> records = null;
		try {
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
			csvRecords = StreamSupport.stream(records.spliterator(), false).collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return csvRecords;
	}

	public static Date fetchDate(final int fetchFrom) {

		LocalDate localDate = LocalDate.now().minusDays(fetchFrom);
		Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		return date;
	}

}
