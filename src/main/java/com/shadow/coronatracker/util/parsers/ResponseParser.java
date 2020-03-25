package com.shadow.coronatracker.util.parsers;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.enums.Statistics;

public interface ResponseParser {

	void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaSummaryStats);

}
