package com.shadow.coronatracker.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.Statistics;

public interface ResponseParser {

	void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaSummaryStats)
			throws IOException;

}
