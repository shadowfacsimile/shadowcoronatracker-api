package com.shadow.coronatracker.util.parsers;

import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shadow.coronatracker.model.IndiaStats;
import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.enums.Statistics;

public class IndiaStatsParser implements ResponseParser {

	@Override
	public void parse(Statistics statistics, HttpResponse<String> response, StatisticsCollection statsCollection) {

		JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

		Gson gson = new Gson();
		IndiaStats indiaStats = gson.fromJson(jsonObject, IndiaStats.class);

		statsCollection.setIndiaStats(indiaStats);
	}

}
