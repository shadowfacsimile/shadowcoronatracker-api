package com.shadow.coronatracker.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

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
	 * confirmed deaths and confirmed recoveries. After fetch, the data is stored
	 * locally for processing.
	 */
	public void fetchCoronaDataFromJHCSSE() {
		LOGGER.info(
				" Inside CoronaDataService.fetchCoronaDataFromJHCSSE() / Fetching Data From Johns Hopkins CSSE Repo ");

		HttpClient httpClient = HttpClient.newHttpClient();

		for (Statistics statistics : Statistics.values()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(statistics.getUrl())).build();

			try {
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
				Files.write(Paths.get("src/main/resources/coronafiles/Corona_" + statistics.name() + ".csv"), response.body().getBytes(),
						StandardOpenOption.CREATE);
				LOGGER.info(statistics.name() + " / Response code: " + response.statusCode());
			} catch (IOException | InterruptedException e) {
				LOGGER.severe("Error in processing data : " + e.getMessage());
			}
		}

		LOGGER.info(" Inside CoronaDataService.fetchCoronaDataFromJHCSSE() / Data synch completed ");
	}

	/*
	 * Local CSV files are used for statistics processing and creation of
	 * CoronaDataResponse.
	 */
	public CoronaDataResponse fetchCoronaData() {
		LOGGER.info(" Inside CoronaDataService.fetchCoronaData() / Statistics Processing ");

		StatisticsCollection statisticsCollection = fetchDataAndCreateStatisticsCollection();
		createCoronaDataResponseFromStatisticsCollection(statisticsCollection);

		LOGGER.info(" Inside CoronaDataService.fetchCoronaData() / Statistics Processing Complete ");

		return coronaDataResponse;
	}

	/*
	 * Each CSV file is parsed and the results are collected in the StatsCollection
	 * object. The Statistics enum has both the URL of the CSV file and the
	 * ResponseParser implementation reference.
	 */
	public StatisticsCollection fetchDataAndCreateStatisticsCollection() {

		StatisticsCollection statisticsCollection = new StatisticsCollection();

		for (Statistics statistics : Statistics.values()) {
			String filePath = "src/main/resources/coronafiles/Corona_" + statistics.name() + ".csv";
			String content = readFile(filePath);
			statistics.getParsers().stream().forEach(parser -> parser.parse(statistics,
					CoronaTrackerUtil.convertResponseToCSVRecord(content), statisticsCollection));
		}

		return statisticsCollection;
	}

	/*
	 * Read contents of local CSV files.
	 */
	private static String readFile(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();

		try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		} catch (IOException e) {
			LOGGER.severe("Error in processing data : " + e.getMessage());
		}

		return contentBuilder.toString();
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
