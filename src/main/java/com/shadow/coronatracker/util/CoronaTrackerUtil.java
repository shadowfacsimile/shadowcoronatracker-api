package com.shadow.coronatracker.util;

import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CoronaTrackerUtil {

	public static final List<String> filterCountries = Arrays.asList("Australia", "Hong Kong SAR",
			"Iran (Islamic Republic of)", "Republic of Korea", "Mainland China", "US");

	public static final DecimalFormat f = new DecimalFormat("#0.00");

	public static Iterable<CSVRecord> convertResponseToCSVRecord(final HttpResponse<String> response)
			throws IOException {
		StringReader reader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		return records;
	}

}
