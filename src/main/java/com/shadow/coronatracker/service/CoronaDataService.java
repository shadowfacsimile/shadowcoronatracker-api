package com.shadow.coronatracker.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shadow.coronatracker.model.StatisticsCollection;
import com.shadow.coronatracker.model.enums.ResponseStatistics;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.model.response.CoronaDataResponse;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

@Service
public class CoronaDataService {

	private static final Logger LOGGER = Logger.getLogger(CoronaDataService.class.getName());

	@Autowired
	private CoronaDataResponse coronaDataResponse;

	public CoronaDataResponse getCoronaDataResponse() {
		return coronaDataResponse;
	}

	/*
	 * Fetch data from Johns Hopkins CSSE github repo. The data is in comma
	 * separated format. JHCSSE has individual CSV files for confirmed cases,
	 * confirmed deaths and confirmed recoveries. After fetch, process the data and
	 * create the response object.
	 */
	public CoronaDataResponse fetchCoronaDataFromJHCSSE() {
		LOGGER.info(
				" Inside CoronaDataService.fetchCoronaDataFromJHCSSE() / Fetching Data From Johns Hopkins CSSE Repo ");

		StatisticsCollection statisticsCollection = fetchDataAndCreateStatisticsCollection();
		createCoronaDataResponseFromStatisticsCollection(statisticsCollection);

		LOGGER.info(" Inside CoronaDataService.fetchCoronaDataFromJHCSSE() / Data synch completed ");

		return coronaDataResponse;
	}

	/*
	 * Each CSV file is parsed and the results are collected in the StatsCollection
	 * object. The Statistics enum has both the URL of the CSV file and the
	 * ResponseParser implementation reference.
	 */
	public StatisticsCollection fetchDataAndCreateStatisticsCollection() {

		HttpClient httpClient = HttpClient.newHttpClient();

		StatisticsCollection statisticsCollection = new StatisticsCollection();

		for (Statistics statistics : Statistics.values()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(statistics.getUrl())).build();

			try {
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
				statistics.getParser().parse(statistics, response, statisticsCollection);
				LOGGER.info(statistics.name() + " / Response code: " + response.statusCode());
			} catch (IOException | InterruptedException e) {
				LOGGER.severe("Error in processing data : " + e.getMessage());
			}

		}

		return statisticsCollection;
	}

	/*
	 * The StatsCollection object has the raw parsed information from the CSV. This
	 * is converted to meaningful statistics. The ResponseStatistics enum has
	 * reference to the DataCreator implementation for respective statistics.
	 */
	private CoronaDataResponse createCoronaDataResponseFromStatisticsCollection(
			final StatisticsCollection statisticsCollection) {

		CoronaDataResponse tempCoronaStatsResponse = new CoronaDataResponse();

		List.of(ResponseStatistics.values()).stream().forEach(
				responseStats -> responseStats.getDataCreator().create(statisticsCollection, tempCoronaStatsResponse));

		this.coronaDataResponse = tempCoronaStatsResponse;

		return coronaDataResponse;
	}

}
