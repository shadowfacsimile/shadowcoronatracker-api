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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.shadow.coronatracker.model.CoronaCasesStats;
import com.shadow.coronatracker.model.CoronaCountryStats;
import com.shadow.coronatracker.model.CoronaDeathsStats;
import com.shadow.coronatracker.model.CoronaRecoveriesStats;
import com.shadow.coronatracker.model.CoronaStateStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.Location;
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
			responseStats.getCoronaDataCreator().create(coronaStatsCollection, coronaDataResponse);

		return coronaDataResponse;
	}

	private void createCoronaDataListFromStatsCollection(final CoronaStatsCollection coronaStatsCollection) {

		List<CoronaCountryStats> coronaCountryStats = new ArrayList<>();

		createCoronaCountryLevelStats(coronaStatsCollection, coronaCountryStats);

		List<CoronaStateStats> coronaStateStats = new ArrayList<>();

		createCoronaStateLevelStats(coronaStatsCollection, coronaStateStats);

		coronaStatsCollection.setCoronaCountryStats(coronaCountryStats);
		coronaStatsCollection.setCoronaStateStats(coronaStateStats);
	}

	private void createCoronaCountryLevelStats(final CoronaStatsCollection coronaStatsCollection,
			List<CoronaCountryStats> coronaCountryStats) {

		Map<String, List<CoronaCasesStats>> casesByCountry = coronaStatsCollection.getCoronaCasesStats().stream()
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getCountry()));

		Map<String, List<CoronaDeathsStats>> deathsByCountry = coronaStatsCollection.getCoronaDeathsStats().stream()
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getCountry()));

		Map<String, List<CoronaRecoveriesStats>> recoveriesByCountry = coronaStatsCollection.getCoronaRecoveriesStats()
				.stream().collect(Collectors.groupingBy(stat -> stat.getLocation().getCountry()));

		for (Entry<String, List<CoronaCasesStats>> entry : casesByCountry.entrySet()) {
			CoronaCountryStats stats = new CoronaCountryStats();
			Location location = new Location();
			location.setCountry(entry.getKey());
			stats.setLocation(location);
			stats.setCases(entry.getValue().stream().mapToInt(CoronaCasesStats::getCases).sum());
			stats.setCasesSinceYesterday(
					entry.getValue().stream().mapToInt(CoronaCasesStats::getCasesSinceYesterday).sum());
			stats.setDeaths(deathsByCountry.get(entry.getKey()).stream().mapToInt(CoronaDeathsStats::getDeaths).sum());
			stats.setMortalityRate(stats.getCases() == 0 ? 0 : (double) stats.getDeaths() / stats.getCases());
			stats.setDeathsSinceYesterday(deathsByCountry.get(entry.getKey()).stream()
					.mapToInt(CoronaDeathsStats::getDeathsSinceYesterday).sum());
			stats.setRecoveries(recoveriesByCountry.get(entry.getKey()) == null ? 0
					: recoveriesByCountry.get(entry.getKey()).stream().mapToInt(CoronaRecoveriesStats::getRecoveries)
							.sum());
			stats.setRecoveryRate(stats.getCases() == 0 ? 0 : (double) stats.getRecoveries() / stats.getCases());
			stats.setFirstCaseReported(
					entry.getValue().stream().filter(CoronaCasesStats::isFirstCaseReported).count() > 0 ? true : false);
			stats.setFirstDeathReported(deathsByCountry.get(entry.getKey()).stream()
					.filter(CoronaDeathsStats::isFirstDeathReported).count() > 0 ? true : false);
			stats.setStatewiseDataExists(entry.getValue().stream().count() > 1);
			coronaCountryStats.add(stats);
		}
	}

	private void createCoronaStateLevelStats(final CoronaStatsCollection coronaStatsCollection,
			List<CoronaStateStats> coronaStateStats) {

		Map<String, List<CoronaCasesStats>> casesByState = coronaStatsCollection.getCoronaCasesStats().stream()
				.filter(stat -> StringUtils.isNotBlank(stat.getLocation().getState()))
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getState()));

		Map<String, List<CoronaDeathsStats>> deathsByState = coronaStatsCollection.getCoronaDeathsStats().stream()
				.filter(stat -> StringUtils.isNotBlank(stat.getLocation().getState()))
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getState()));

		Map<String, List<CoronaRecoveriesStats>> recoveriesByState = coronaStatsCollection.getCoronaRecoveriesStats()
				.stream().filter(stat -> StringUtils.isNotBlank(stat.getLocation().getState()))
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getState()));

		for (Entry<String, List<CoronaCasesStats>> entry : casesByState.entrySet()) {
			CoronaStateStats stats = new CoronaStateStats();
			Location location = new Location();
			location.setCountry(entry.getValue().get(0).getLocation().getCountry());
			location.setState(entry.getKey());
			stats.setLocation(location);
			stats.setCases(entry.getValue().stream().mapToInt(CoronaCasesStats::getCases).sum());
			stats.setCasesSinceYesterday(
					entry.getValue().stream().mapToInt(CoronaCasesStats::getCasesSinceYesterday).sum());
			stats.setDeaths(deathsByState.get(entry.getKey()).stream().mapToInt(CoronaDeathsStats::getDeaths).sum());
			stats.setMortalityRate(stats.getCases() == 0 ? 0 : (double) stats.getDeaths() / stats.getCases());
			stats.setDeathsSinceYesterday(deathsByState.get(entry.getKey()).stream()
					.mapToInt(CoronaDeathsStats::getDeathsSinceYesterday).sum());
			stats.setRecoveries(recoveriesByState.get(entry.getKey()) == null ? 0
					: recoveriesByState.get(entry.getKey()).stream().mapToInt(CoronaRecoveriesStats::getRecoveries)
							.sum());
			stats.setRecoveryRate(stats.getCases() == 0 ? 0 : (double) stats.getRecoveries() / stats.getCases());

			coronaStateStats.add(stats);
		}
	}

}
