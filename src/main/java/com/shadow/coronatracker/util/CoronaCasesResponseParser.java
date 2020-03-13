package com.shadow.coronatracker.util;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaCasesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;

public class CoronaCasesResponseParser implements ResponseParser {

	@Override
	public void parse(HttpResponse<String> response, CoronaStatsCollection coronaSummaryStats) throws IOException {

		List<CoronaCasesStats> coronaCasesStats = new ArrayList<>();

		for (CSVRecord record : CoronaTrackerUtil.convertResponseToCSVRecord(response)) {
			CoronaCasesStats casesStats = new CoronaCasesStats();
			casesStats.setState(record.get(0));
			casesStats.setCountry(record.get(1));
			casesStats.setLatitude(Float.valueOf(record.get(2)));
			casesStats.setLongitude(Float.valueOf(record.get(3)));
			String cases = StringUtils.isBlank(record.get(record.size() - 1)) ? "0" : record.get(record.size() - 1);
			casesStats.setCases(Integer.valueOf(cases));
			String casesSinceYesterday = StringUtils.isBlank(record.get(record.size() - 2)) ? "0"
					: record.get(record.size() - 2);
			casesStats.setCasesSinceYesterday(casesStats.getCases() - Integer.valueOf(casesSinceYesterday));
			casesStats.setFirstCaseReported(record.get(record.size() - 2).equalsIgnoreCase("0")
					&& StringUtils.isNotBlank(record.get(record.size() - 1))
					&& !record.get(record.size() - 1).equalsIgnoreCase("0"));
			coronaCasesStats.add(casesStats);
		}

		coronaSummaryStats.setCoronaCasesStats(coronaCasesStats);
	}

}
