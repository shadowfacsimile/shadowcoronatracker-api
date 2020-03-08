package com.shadow.coronatracker.util;

import java.io.IOException;
import java.net.http.HttpResponse;

import com.shadow.coronatracker.model.CoronaStatsCollection;

public interface ResponseParser {

	void parse(HttpResponse<String> response, CoronaStatsCollection coronaSummaryStats) throws IOException;

}
