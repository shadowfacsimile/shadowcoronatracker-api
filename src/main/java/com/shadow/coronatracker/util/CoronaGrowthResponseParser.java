package com.shadow.coronatracker.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.Statistics;

public class CoronaGrowthResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaStatsCollection)
			throws IOException {

		Map<Date, Double> growthFactorMap = new LinkedHashMap<>();

		Map<Date, Integer> casesByDateMap = new LinkedHashMap<>();

		CSVRecord record = csvRecords.get(0);

		int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

		if (statistics.equals(Statistics.GROWTH_COUNTRY)) {
			List<CSVRecord> records = csvRecords.stream().filter(rec -> rec.get(1).equalsIgnoreCase("India"))
					.collect(Collectors.toList());
			createCasesByCountryDateMap(records, casesByDateMap, lastRecord);
			coronaStatsCollection.setCoronaCasesGrowthCountry(casesByDateMap);
		} else {
			createCasesByDateMap(csvRecords, casesByDateMap, lastRecord);
			createGrowthFactorMap(growthFactorMap, casesByDateMap, lastRecord);
			coronaStatsCollection.setCoronaCasesGrowthFactors(growthFactorMap);
			coronaStatsCollection.setCoronaCasesGrowth(casesByDateMap);
		}

	}

	private void createGrowthFactorMap(Map<Date, Double> growthFactorMap, Map<Date, Integer> casesByDateMap,
			int lastRecord) {
		for (int i = 6; i <= lastRecord; i++) {
			int fetchFrom = lastRecord - i;
			Date date = fetchDate(fetchFrom);
			fetchFrom = lastRecord - i + 1;
			Date prevDate = fetchDate(fetchFrom);
			fetchFrom = lastRecord - i + 2;
			Date prevToPrevDate = fetchDate(fetchFrom);
			int demo = casesByDateMap.get(prevDate) - casesByDateMap.get(prevToPrevDate) == 0 ? 1
					: casesByDateMap.get(prevDate) - casesByDateMap.get(prevToPrevDate);
			growthFactorMap.put(date, (double) (casesByDateMap.get(date) - casesByDateMap.get(prevDate)) / demo);
		}
	}

	private void createCasesByDateMap(List<CSVRecord> csvRecords, Map<Date, Integer> casesByDateMap, int lastRecord) {
		for (CSVRecord csvrecord : csvRecords) {
			for (int i = 4; i <= lastRecord; i++) {
				int fetchFrom = lastRecord - i;
				Date date = fetchDate(fetchFrom);
				int rec = StringUtils.isBlank(csvrecord.get(i)) ? 0 : Integer.valueOf(csvrecord.get(i));
				int cases = casesByDateMap.get(date) == null ? 0 : casesByDateMap.get(date) + rec;
				casesByDateMap.put(date, cases);
			}
		}
	}

	private void createCasesByCountryDateMap(List<CSVRecord> csvRecords, Map<Date, Integer> casesByDateMap,
			int lastRecord) {

		for (int i = 4; i <= lastRecord; i++) {
			int fetchFrom = lastRecord - i;
			Date date = fetchDate(fetchFrom);
			int rec = StringUtils.isBlank(csvRecords.get(0).get(i)) ? 0 : Integer.valueOf(csvRecords.get(0).get(i));
			int cases = csvRecords.get(0).get(i) == null ? 0 : rec;
			casesByDateMap.put(date, cases);
		}
	}

	private Date fetchDate(final int fetchFrom) {

		LocalDate localDate = LocalDate.now().minusDays(fetchFrom);
		Date date = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		return date;
	}

}
