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
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private CoronaDataResponse coronaDataResponse;

	public CoronaDataResponse getCoronaDataResponse() {
		return coronaDataResponse;
	}

	public void setCoronaDataResponse(CoronaDataResponse coronaDataResponse) {
		this.coronaDataResponse = coronaDataResponse;
	}

	public CoronaDataResponse fetchCoronaData() {
		LOGGER.info(
				"***** Inside CoronaDataService.fetchCoronaData() / Fetching Data From Johns Hopkins CSSE Repo *****");

		CoronaStatsCollection coronaStatsCollection = createCoronaStatsCollectionByFetchingData();
		generateCountryAndStateStats(coronaStatsCollection);
		createCoronaDataResponse(coronaStatsCollection);

		LOGGER.info("***** Inside CoronaDataService.fetchCoronaData() / Data synch completed *****");

		return coronaDataResponse;
	}

	private CoronaStatsCollection createCoronaStatsCollectionByFetchingData() {

		HttpClient httpClient = HttpClient.newHttpClient();

		CoronaStatsCollection coronaStatsCollection = new CoronaStatsCollection();

		for (Statistics statistics : Statistics.values()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(statistics.getUrl())).build();

			try {
				HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
				statistics.getParsers().stream().forEach(parser -> parser.parse(statistics,
						CoronaTrackerUtil.convertResponseToCSVRecord(response), coronaStatsCollection));
				LOGGER.info(statistics.name() + " / Response code: " + response.statusCode());
			} catch (IOException | InterruptedException e) {
				LOGGER.severe("Error in processing data : " + e.getMessage());
			}
		}

		return coronaStatsCollection;
	}

	private CoronaDataResponse createCoronaDataResponse(final CoronaStatsCollection coronaStatsCollection) {

		CoronaDataResponse tempCoronaDataResponse = new CoronaDataResponse();

		for (ResponseStatistics responseStats : ResponseStatistics.values())
			responseStats.getCoronaDataCreator().create(coronaStatsCollection, tempCoronaDataResponse);

		setCoronaDataResponse(tempCoronaDataResponse);

		return coronaDataResponse;
	}

	private void generateCountryAndStateStats(final CoronaStatsCollection coronaStatsCollection) {

		coronaStatsCollection.setCoronaCountryStats(createCoronaCountryLevelStats(coronaStatsCollection));
		coronaStatsCollection.setCoronaStateStats(createCoronaStateLevelStats(coronaStatsCollection));
	}

	private List<CoronaCountryStats> createCoronaCountryLevelStats(final CoronaStatsCollection coronaStatsCollection) {

		Map<String, List<CoronaCasesStats>> casesByCountry = coronaStatsCollection.getCoronaCasesStats().stream()
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getCountry()));

		Map<String, List<CoronaDeathsStats>> deathsByCountry = coronaStatsCollection.getCoronaDeathsStats().stream()
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getCountry()));

		Map<String, List<CoronaRecoveriesStats>> recoveriesByCountry = coronaStatsCollection.getCoronaRecoveriesStats()
				.stream().collect(Collectors.groupingBy(stat -> stat.getLocation().getCountry()));

		List<CoronaCountryStats> coronaCountryStats = new ArrayList<>();

		for (Entry<String, List<CoronaCasesStats>> entry : casesByCountry.entrySet()) {
			String country = entry.getKey();
			List<CoronaCasesStats> casesStats = entry.getValue();
			List<CoronaDeathsStats> deathsStats = deathsByCountry.get(country);
			List<CoronaRecoveriesStats> recoveriesStats = recoveriesByCountry.get(country);

			CoronaCountryStats stats = new CoronaCountryStats();
			Location location = new Location();
			location.setCountry(country);
			stats.setLocation(location);
			stats.setCases(casesStats == null ? 0 : casesStats.stream().mapToInt(CoronaCasesStats::getCases).sum());
			stats.setCasesSinceYesterday(casesStats == null ? 0
					: casesStats.stream().mapToInt(CoronaCasesStats::getCasesSinceYesterday).sum());
			stats.setDeaths(
					deathsStats == null ? 0 : deathsStats.stream().mapToInt(CoronaDeathsStats::getDeaths).sum());
			stats.setDeathsSinceYesterday(deathsStats == null ? 0
					: deathsStats.stream().mapToInt(CoronaDeathsStats::getDeathsSinceYesterday).sum());
			stats.setMortalityRate(stats.getCases() == 0 ? 0 : (double) stats.getDeaths() / stats.getCases());
			stats.setRecoveries(recoveriesStats == null ? 0
					: recoveriesStats.stream().mapToInt(CoronaRecoveriesStats::getRecoveries).sum());
			stats.setRecoveryRate(stats.getCases() == 0 ? 0 : (double) stats.getRecoveries() / stats.getCases());
			stats.setFirstCaseReported(casesStats == null ? false
					: casesStats.stream().filter(CoronaCasesStats::isFirstCaseReported).count() > 0);
			stats.setFirstDeathReported(deathsStats == null ? false
					: deathsStats.stream().filter(CoronaDeathsStats::isFirstDeathReported).count() > 0);
			stats.setStatewiseDataExists(casesStats.stream().count() > 1);
			coronaCountryStats.add(stats);
		}

		return coronaCountryStats;
	}

	private List<CoronaStateStats> createCoronaStateLevelStats(final CoronaStatsCollection coronaStatsCollection) {

		Map<String, List<CoronaCasesStats>> casesByState = coronaStatsCollection.getCoronaCasesStats().stream()
				.filter(stat -> StringUtils.isNotBlank(stat.getLocation().getState()))
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getState()));

		Map<String, List<CoronaDeathsStats>> deathsByState = coronaStatsCollection.getCoronaDeathsStats().stream()
				.filter(stat -> StringUtils.isNotBlank(stat.getLocation().getState()))
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getState()));

		Map<String, List<CoronaRecoveriesStats>> recoveriesByState = coronaStatsCollection.getCoronaRecoveriesStats()
				.stream().filter(stat -> StringUtils.isNotBlank(stat.getLocation().getState()))
				.collect(Collectors.groupingBy(stat -> stat.getLocation().getState()));

		List<CoronaStateStats> coronaStateStats = new ArrayList<>();

		for (Entry<String, List<CoronaCasesStats>> entry : casesByState.entrySet()) {
			String state = entry.getKey();
			String country = entry.getValue().get(0).getLocation().getCountry();
			List<CoronaCasesStats> casesStats = entry.getValue();
			List<CoronaDeathsStats> deathsStats = deathsByState.get(state);
			List<CoronaRecoveriesStats> recoveriesStats = recoveriesByState.get(state);

			CoronaStateStats stats = new CoronaStateStats();
			Location location = new Location();
			location.setCountry(country);
			location.setState(state);
			stats.setLocation(location);
			stats.setCases(casesStats == null ? 0 : casesStats.stream().mapToInt(CoronaCasesStats::getCases).sum());
			stats.setCasesSinceYesterday(casesStats == null ? 0
					: casesStats.stream().mapToInt(CoronaCasesStats::getCasesSinceYesterday).sum());
			stats.setDeaths(
					deathsStats == null ? 0 : deathsStats.stream().mapToInt(CoronaDeathsStats::getDeaths).sum());
			stats.setMortalityRate(stats.getCases() == 0 ? 0 : (double) stats.getDeaths() / stats.getCases());
			stats.setDeathsSinceYesterday(deathsStats == null ? 0
					: deathsStats.stream().mapToInt(CoronaDeathsStats::getDeathsSinceYesterday).sum());
			stats.setRecoveries(recoveriesStats == null ? 0
					: recoveriesByState.get(state).stream().mapToInt(CoronaRecoveriesStats::getRecoveries).sum());
			stats.setRecoveryRate(stats.getCases() == 0 ? 0 : (double) stats.getRecoveries() / stats.getCases());

			coronaStateStats.add(stats);
		}

		return coronaStateStats;
	}

}
