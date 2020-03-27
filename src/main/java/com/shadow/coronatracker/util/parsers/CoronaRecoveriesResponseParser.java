package com.shadow.coronatracker.util.parsers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaRecoveriesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.Location;
import com.shadow.coronatracker.model.enums.Statistics;

public class CoronaRecoveriesResponseParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, List<CSVRecord> csvRecords, CoronaStatsCollection coronaStatsCollection) {

		List<CoronaRecoveriesStats> coronaRecoveriesStats = new ArrayList<>();

		for (CSVRecord record : csvRecords) {
			int lastRecord = StringUtils.isBlank(record.get(record.size() - 1)) ? record.size() - 2 : record.size() - 1;

			CoronaRecoveriesStats recoveriesStats = new CoronaRecoveriesStats();
			Location location = new Location();
			location.setState(record.get(0));
			location.setCountry(record.get(1));
			location.setLatitude(Float.valueOf(record.get(2)));
			location.setLongitude(Float.valueOf(record.get(3)));
			recoveriesStats.setLocation(location);
			recoveriesStats.setRecoveries(Integer.valueOf(record.get(lastRecord)));
			coronaRecoveriesStats.add(recoveriesStats);
		}

		coronaStatsCollection.setCoronaRecoveriesStats(coronaRecoveriesStats);
	}
}
