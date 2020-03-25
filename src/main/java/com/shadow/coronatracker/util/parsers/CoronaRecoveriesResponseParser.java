package com.shadow.coronatracker.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaRecoveriesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.enums.Statistics;

public class CoronaRecoveriesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaStatsCollection) {

		List<CoronaRecoveriesStats> coronaRecoveriesStats = new ArrayList<>();

		for (CSVRecord record : csvRecords) {
			int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

			CoronaRecoveriesStats recoveriesStats = new CoronaRecoveriesStats();
			recoveriesStats.setState(record.get(0));
			recoveriesStats.setCountry(record.get(1));
			recoveriesStats.setLatitude(Float.valueOf(record.get(2)));
			recoveriesStats.setLongitude(Float.valueOf(record.get(3)));
			recoveriesStats.setRecoveries(Integer.valueOf(record.get(lastRecord)));
			coronaRecoveriesStats.add(recoveriesStats);
		}

		coronaStatsCollection.setCoronaRecoveriesStats(coronaRecoveriesStats);
	}
}
