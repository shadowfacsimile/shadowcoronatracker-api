package com.shadow.coronatracker.util.parsers;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowth;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class CoronaCasesGrowthResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaStatsCollection) {

		Map<Date, Double> growthFactorMap = new LinkedHashMap<>();
		Map<Date, Integer> casesByDateMap = new LinkedHashMap<>();
		Map<String, List<CoronaCaseGrowth>> caseGrowthByCountryMap = new LinkedHashMap<>();

		CSVRecord record = csvRecords.get(0);

		int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

		createCasesByCountryDateMap(csvRecords, caseGrowthByCountryMap, lastRecord);
		coronaStatsCollection.setCoronaCasesGrowthCountry(caseGrowthByCountryMap);
		createCasesByDateMap(csvRecords, casesByDateMap, lastRecord);
		createGrowthFactorMap(growthFactorMap, casesByDateMap, lastRecord);
		coronaStatsCollection.setCoronaCasesGrowthFactors(growthFactorMap);
		coronaStatsCollection.setCoronaCasesGrowth(casesByDateMap);
	}

	private void createGrowthFactorMap(Map<Date, Double> growthFactorMap, Map<Date, Integer> casesByDateMap,
			int lastRecord) {
		for (int i = 6; i <= lastRecord; i++) {
			int fetchFrom = lastRecord - i;
			Date date = CoronaTrackerUtil.fetchDate(fetchFrom);
			fetchFrom = lastRecord - i + 1;
			Date prevDate = CoronaTrackerUtil.fetchDate(fetchFrom);
			fetchFrom = lastRecord - i + 2;
			Date prevToPrevDate = CoronaTrackerUtil.fetchDate(fetchFrom);
			int demo = casesByDateMap.get(prevDate) - casesByDateMap.get(prevToPrevDate) == 0 ? 1
					: casesByDateMap.get(prevDate) - casesByDateMap.get(prevToPrevDate);
			growthFactorMap.put(date, (double) (casesByDateMap.get(date) - casesByDateMap.get(prevDate)) / demo);
		}
	}

	private void createCasesByDateMap(List<CSVRecord> csvRecords, Map<Date, Integer> casesByDateMap, int lastRecord) {
		for (CSVRecord csvrecord : csvRecords) {
			for (int i = 4; i <= lastRecord; i++) {
				int fetchFrom = lastRecord - i;
				Date date = CoronaTrackerUtil.fetchDate(fetchFrom);
				int rec = StringUtils.isBlank(csvrecord.get(i)) ? 0 : Integer.valueOf(csvrecord.get(i));
				int cases = casesByDateMap.get(date) == null ? 0 : casesByDateMap.get(date) + rec;
				casesByDateMap.put(date, cases);
			}
		}
	}

	private void createCasesByCountryDateMap(List<CSVRecord> csvRecords,
			Map<String, List<CoronaCaseGrowth>> growthByCountryMap, int lastRecord) {

		for (CSVRecord csvrecord : csvRecords) {
			List<CoronaCaseGrowth> statsList = new LinkedList<>();

			int delta = 0;

			for (int i = 4; i <= lastRecord; i++) {
				CoronaCaseGrowth stats = new CoronaCaseGrowth();
				int fetchFrom = lastRecord - i;
				Date date = CoronaTrackerUtil.fetchDate(fetchFrom);
				int cases = 0;
				if (growthByCountryMap.containsKey(csvrecord.get(1))) {
					long othercases = growthByCountryMap.get(csvrecord.get(1)).stream()
							.filter(c -> c.getDate().compareTo(date) == 0)
							.collect(Collectors.summarizingInt(CoronaCaseGrowth::getGrowth)).getSum();
					cases = (int) (othercases
							+ (StringUtils.isBlank(csvrecord.get(i)) ? 0 : Integer.valueOf(csvrecord.get(i))));
				} else {
					cases = StringUtils.isBlank(csvrecord.get(i)) ? 0 : Integer.valueOf(csvrecord.get(i));
				}
				stats.setDate(date);
				stats.setGrowth(cases);
				stats.setDelta(cases - delta);
				statsList.add(stats);
				delta = cases;
			}

			growthByCountryMap.put(csvrecord.get(1), statsList);
		}
	}
}
