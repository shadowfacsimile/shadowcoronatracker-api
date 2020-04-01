package com.shadow.coronatracker.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.coronatracker.model.StatsCollection;
import com.shadow.coronatracker.model.enums.ResponseStatistics;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.model.response.CoronaStatsResponse;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

@Service
public class CoronaDataService {

	private static final Logger LOGGER = Logger.getLogger(CoronaDataService.class.getName());

	@Autowired
	private CoronaStatsResponse coronaDataResponse;

	public CoronaStatsResponse getCoronaDataResponse() {
		return coronaDataResponse;
	}

	public void setCoronaDataResponse(CoronaStatsResponse coronaDataResponse) {
		this.coronaDataResponse = coronaDataResponse;
	}

	public CoronaStatsResponse fetchCoronaData() {
		LOGGER.info(" Inside CoronaDataService.fetchCoronaData() / Fetching Data From Johns Hopkins CSSE Repo ");

		StatsCollection statsCollection = createStatsCollectionByFetchingData();
		createCoronaStatsResponse(statsCollection);

		LOGGER.info(" Inside CoronaDataService.fetchCoronaData() / Data synch completed ");

		return coronaDataResponse;
	}

	public StatsCollection createStatsCollectionByFetchingData() {

		HttpClient httpClient = HttpClient.newHttpClient();

		StatsCollection statsCollection = new StatsCollection();

		for (Statistics statistics : Statistics.values()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(statistics.getUrl())).build();

			try {
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
				statistics.getParsers().stream().forEach(parser -> parser.parse(statistics,
						CoronaTrackerUtil.convertResponseToCSVRecord(response), statsCollection));
				LOGGER.info(statistics.name() + " / Response code: " + response.statusCode());
			} catch (IOException | InterruptedException e) {
				LOGGER.severe("Error in processing data : " + e.getMessage());
			}

		}

		return statsCollection;
	}

	private CoronaStatsResponse createCoronaStatsResponse(final StatsCollection statsCollection) {

		CoronaStatsResponse tempCoronaStatsResponse = new CoronaStatsResponse();

		for (ResponseStatistics responseStats : ResponseStatistics.values())
			responseStats.getCoronaDataCreator().create(statsCollection, tempCoronaStatsResponse);

		setCoronaDataResponse(tempCoronaStatsResponse);

		return coronaDataResponse;
	}

}
