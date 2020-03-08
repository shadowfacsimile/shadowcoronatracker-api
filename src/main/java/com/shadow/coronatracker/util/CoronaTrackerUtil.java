package com.shadow.coronatracker.util;

import java.io.IOException;
import java.io.StringReader;
import java.net.http.HttpResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CoronaTrackerUtil {

	public static Iterable<CSVRecord> convertResponseToCSVRecord(final HttpResponse<String> response)
			throws IOException {
		StringReader reader = new StringReader(response.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		return records;
	}

}
