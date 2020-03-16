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
	public void parse(HttpResponse<String> response, CoronaStatsCollection coronaStatsCollection) throws IOException {

		List<CoronaRecoveriesStats> coronaRecoveriesStats = new ArrayList<>();

		for (CSVRecord record : CoronaTrackerUtil.convertResponseToCSVRecord(response)) {
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
