package com.shadow.coronatracker.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shadow.coronatracker.model.CoronaCasesStats;
import com.shadow.coronatracker.model.CoronaCountryStats;
import com.shadow.coronatracker.model.CoronaDeathsStats;
import com.shadow.coronatracker.model.CoronaRecoveriesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.enums.ResponseStatistics;
import com.shadow.coronatracker.model.enums.Statistics;
import com.shadow.coronatracker.model.response.CoronaDataResponse;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

@Service
public class CoronaDataService {

	private static final Logger LOGGER = Logger.getLogger(CoronaDataService.class.getName());

	public CoronaDataResponse fetchCoronaData() throws IOException, InterruptedException {

		return createCoronaDataResponse(createCoronaStatsCollectionByFetchingData());
	}

	private CoronaStatsCollection createCoronaStatsCollectionByFetchingData() throws IOException, InterruptedException {

		HttpClient httpClient = HttpClient.newHttpClient();

		CoronaStatsCollection coronaStatsCollection = new CoronaStatsCollection();

		for (Statistics statistics : Statistics.values()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(statistics.getUrl())).build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			statistics.getParsers().stream().forEach(parser -> parser.parse(statistics,
					CoronaTrackerUtil.convertResponseToCSVRecord(response), coronaStatsCollection));
			LOGGER.info(statistics.name() + " / Response code: " + response.statusCode());
		}

		createCoronaDataListFromStatsCollection(coronaStatsCollection);

		return coronaStatsCollection;
	}

	private CoronaDataResponse createCoronaDataResponse(final CoronaStatsCollection coronaStatsCollection) {

		CoronaDataResponse coronaDataResponse = new CoronaDataResponse();

		for (ResponseStatistics responseStats : ResponseStatistics.values())
			responseStats.getCoronaGrowthDataCreator().create(coronaStatsCollection, coronaDataResponse);

		return coronaDataResponse;
	}

	private void createCoronaDataListFromStatsCollection(final CoronaStatsCollection coronaStatsCollection) {

		Map<String, Integer> casesByCountry = coronaStatsCollection.getCoronaCasesStats().stream().collect(
				Collectors.groupingBy(CoronaCasesStats::getCountry, Collectors.summingInt(CoronaCasesStats::getCases)));

		Map<String, Integer> casesDeltaByCountry = coronaStatsCollection.getCoronaCasesStats().stream()
				.collect(Collectors.groupingBy(CoronaCasesStats::getCountry,
						Collectors.summingInt(CoronaCasesStats::getCasesSinceYesterday)));

		Map<String, Integer> deathsByCountry = coronaStatsCollection.getCoronaDeathsStats().stream().collect(Collectors
				.groupingBy(CoronaDeathsStats::getCountry, Collectors.summingInt(CoronaDeathsStats::getDeaths)));

		Map<String, Integer> deathsDeltaByCountry = coronaStatsCollection.getCoronaDeathsStats().stream()
				.collect(Collectors.groupingBy(CoronaDeathsStats::getCountry,
						Collectors.summingInt(CoronaDeathsStats::getDeathsSinceYesterday)));

		Map<String, Integer> recoveriesByCountry = coronaStatsCollection.getCoronaRecoveriesStats().stream()
				.collect(Collectors.groupingBy(CoronaRecoveriesStats::getCountry,
						Collectors.summingInt(CoronaRecoveriesStats::getRecoveries)));

		List<CoronaCasesStats> firstCaseCountries = coronaStatsCollection.getCoronaCasesStats().stream()
				.filter(CoronaCasesStats::isFirstCaseReported).collect(Collectors.toList());

		List<CoronaDeathsStats> firstDeathCountries = coronaStatsCollection.getCoronaDeathsStats().stream()
				.filter(CoronaDeathsStats::isFirstDeathReported).collect(Collectors.toList());

		List<CoronaCountryStats> coronaCountryStats = new ArrayList<>();

		for (Entry<String, Integer> entry : casesByCountry.entrySet()) {
			CoronaCountryStats stats = new CoronaCountryStats();
			stats.setCountry(entry.getKey());
			stats.setCases(entry.getValue());
			stats.setCasesSinceYesterday(casesDeltaByCountry.get(entry.getKey()));
			stats.setDeaths(deathsByCountry.get(entry.getKey()));
			stats.setMortalityRate(stats.getCases() == 0 ? 0 : (double) stats.getDeaths() / stats.getCases());
			stats.setDeathsSinceYesterday(deathsDeltaByCountry.get(entry.getKey()));
			stats.setRecoveries(
					recoveriesByCountry.get(entry.getKey()) == null ? 0 : recoveriesByCountry.get(entry.getKey()));
			stats.setRecoveryRate(stats.getCases() == 0 ? 0 : (double) stats.getRecoveries() / stats.getCases());
			stats.setFirstCaseReported(firstCaseCountries.stream()
					.filter(stat -> stat.getCountry().equalsIgnoreCase(entry.getKey())).count() > 0 ? true : false);
			stats.setFirstDeathReported(firstDeathCountries.stream()
					.filter(stat -> stat.getCountry().equalsIgnoreCase(entry.getKey())).count() > 0 ? true : false);
			coronaCountryStats.add(stats);
		}

		coronaStatsCollection.setCoronaCountryStats(coronaCountryStats);
	}

}
