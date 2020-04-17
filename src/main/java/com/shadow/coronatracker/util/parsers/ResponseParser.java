package com.shadow.coronatracker.util.parsers;

import java.net.http.HttpResponse;

import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.enums.Statistics;

public interface ResponseParser {

	void parse(Statistics statistics, HttpResponse<String> response, StatisticsCollection statsCollection);

}
