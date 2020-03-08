package com.shadow.coronatracker.util;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import com.shadow.coronatracker.model.CoronaRecoveriesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;

public class CoronaRecoveriesResponseParser implements ResponseParser {

	@Override
	public void parse(HttpResponse<String> response, CoronaStatsCollection coronaSummaryStats) throws IOException {

		List<CoronaRecoveriesStats> coronaRecoveriesStats = new ArrayList<>();

		for (CSVRecord record : CoronaTrackerUtil.convertResponseToCSVRecord(response)) {
			CoronaRecoveriesStats recoveriesStats = new CoronaRecoveriesStats();
			recoveriesStats.setState(record.get(0));
			recoveriesStats.setCountry(record.get(1).equalsIgnoreCase("Others") ? "Diamond Princess Cruise" : record.get(1));
			recoveriesStats.setLatitude(Float.valueOf(record.get(2)));
			recoveriesStats.setLongitude(Float.valueOf(record.get(3)));
			String recoveries = StringUtils.isBlank(record.get(record.size() - 1)) ? "0" : record.get(record.size() - 1);
			recoveriesStats.setRecoveries(Integer.valueOf(recoveries));
			coronaRecoveriesStats.add(recoveriesStats);
		}

		coronaSummaryStats.setCoronaRecoveriesStats(coronaRecoveriesStats);

	}
}
