package com.shadow.coronatracker.util;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaStatsCollection;

public class CoronaGrowthResponseParser implements ResponseParser {

	@Override
	public void parse(HttpResponse<String> response, CoronaStatsCollection coronaStatsCollection) throws IOException {

		Map<Date, Double> growthFactorMap = new LinkedHashMap<>();

		Map<Date, Integer> casesByDateMap = new LinkedHashMap<>();

		CSVRecord record = CoronaTrackerUtil.convertResponseToCSVRecord(response).iterator().next();

		int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

		for (CSVRecord csvrecord : CoronaTrackerUtil.convertResponseToCSVRecord(response)) {
			for (int i = 5; i <= lastRecord; i++) {
				int fetchFrom = lastRecord - i + 1;
				Date date = fetchDate(fetchFrom);
				int cases = casesByDateMap.get(date) == null ? 0
						: casesByDateMap.get(date) + Integer.valueOf(csvrecord.get(i));
				casesByDateMap.put(date, cases);
			}
		}

		for (int i = 6; i <= lastRecord; i++) {
			int fetchFrom = lastRecord - i + 1;
			Date date = fetchDate(fetchFrom);
			fetchFrom = lastRecord - i + 2;
			Date prevDate = fetchDate(fetchFrom);
			growthFactorMap.put(date, (double) casesByDateMap.get(date) / casesByDateMap.get(prevDate));
		}

		coronaStatsCollection.setCoronaCasesGrowthFactors(growthFactorMap);
	}

	private Date fetchDate(final int fetchFrom) {
		
		LocalDate localDate = LocalDate.now().minusDays(fetchFrom);
		Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		
		return date;
	}

}
