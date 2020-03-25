package com.shadow.coronatracker.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaCasesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.enums.Statistics;

public class CoronaCasesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaStatsCollection) {

		List<CoronaCasesStats> coronaCasesStats = new ArrayList<>();

		for (CSVRecord record : csvRecords) {
			int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

			CoronaCasesStats casesStats = new CoronaCasesStats();
			casesStats.setState(record.get(0));
			casesStats.setCountry(record.get(1));
			casesStats.setLatitude(Float.valueOf(record.get(2)));
			casesStats.setLongitude(Float.valueOf(record.get(3)));
			casesStats.setCases(Integer.valueOf(record.get(lastRecord)));
			casesStats.setCasesSinceYesterday(casesStats.getCases() - Integer.valueOf(record.get(lastRecord - 1)));
			casesStats.setFirstCaseReported(
					record.get(lastRecord - 1).equalsIgnoreCase("0") && !record.get(lastRecord).equalsIgnoreCase("0"));
			coronaCasesStats.add(casesStats);
		}

		coronaStatsCollection.setCoronaCasesStats(coronaCasesStats);
	}
}
