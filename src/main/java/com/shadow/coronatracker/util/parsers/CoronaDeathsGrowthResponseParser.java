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
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowth;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

public class CoronaDeathsGrowthResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaStatsCollection) {

		Map<Date, Integer> deathsByDateMap = new LinkedHashMap<>();
		Map<String, List<CoronaDeathGrowth>> deathGrowthByCountryMap = new LinkedHashMap<>();

		CSVRecord record = csvRecords.get(0);

		int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

		createDeathsByCountryDateMap(csvRecords, deathGrowthByCountryMap, lastRecord);
		coronaStatsCollection.setCoronaDeathsGrowthCountry(deathGrowthByCountryMap);
		createDeathsByDateMap(csvRecords, deathsByDateMap, lastRecord);
		coronaStatsCollection.setCoronaDeathsGrowth(deathsByDateMap);
	}

	private void createDeathsByDateMap(List<CSVRecord> csvRecords, Map<Date, Integer> deathsByDateMap, int lastRecord) {
		for (CSVRecord csvrecord : csvRecords) {
			for (int i = 4; i <= lastRecord; i++) {
				int fetchFrom = lastRecord - i;
				Date date = CoronaTrackerUtil.fetchDate(fetchFrom);
				int rec = StringUtils.isBlank(csvrecord.get(i)) ? 0 : Integer.valueOf(csvrecord.get(i));
				int deaths = deathsByDateMap.get(date) == null ? 0 : deathsByDateMap.get(date) + rec;
				deathsByDateMap.put(date, deaths);
			}
		}
	}

	private void createDeathsByCountryDateMap(List<CSVRecord> csvRecords,
			Map<String, List<CoronaDeathGrowth>> growthByCountryMap, int lastRecord) {

		for (CSVRecord csvrecord : csvRecords) {
			List<CoronaDeathGrowth> statsList = new LinkedList<>();

			int delta = 0;

			for (int i = 4; i <= lastRecord; i++) {
				CoronaDeathGrowth stats = new CoronaDeathGrowth();
				int fetchFrom = lastRecord - i;
				Date date = CoronaTrackerUtil.fetchDate(fetchFrom);
				int deaths = 0;
				if (growthByCountryMap.containsKey(csvrecord.get(1))) {
					long otherdeaths = growthByCountryMap.get(csvrecord.get(1)).stream()
							.filter(c -> c.getDate().compareTo(date) == 0)
							.collect(Collectors.summarizingInt(CoronaDeathGrowth::getGrowth)).getSum();
					deaths = (int) (otherdeaths
							+ (StringUtils.isBlank(csvrecord.get(i)) ? 0 : Integer.valueOf(csvrecord.get(i))));
				} else {
					deaths = StringUtils.isBlank(csvrecord.get(i)) ? 0 : Integer.valueOf(csvrecord.get(i));
				}
				stats.setDate(date);
				stats.setGrowth(deaths);
				stats.setDelta(deaths - delta);
				statsList.add(stats);
				delta = deaths;
			}

			growthByCountryMap.put(csvrecord.get(1), statsList);
		}
	}
}
