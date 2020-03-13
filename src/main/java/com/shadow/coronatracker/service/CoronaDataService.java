package com.shadow.coronatracker.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.shadow.coronatracker.model.CoronaCasesStats;
import com.shadow.coronatracker.model.CoronaDeathsStats;
import com.shadow.coronatracker.model.CoronaRecoveriesStats;
import com.shadow.coronatracker.model.CoronaStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.CoronaStatsResponse;
import com.shadow.coronatracker.model.CoronaSummaryStats;
import com.shadow.coronatracker.model.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

@Service
public class CoronaDataService {

	private static final Logger LOGGER = Logger.getLogger(CoronaDataService.class);

	public CoronaStatsResponse fetchCoronaData() throws IOException, InterruptedException {

		HttpClient httpClient = HttpClient.newHttpClient();

		CoronaStatsCollection coronaStatsCollection = new CoronaStatsCollection();

		for (Statistics statistics : Statistics.values()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(statistics.getUrl())).build();
			LOGGER.info("Connecting to: " + statistics.getUrl());
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			LOGGER.info("Response received " + response);
			statistics.getParser().parse(response, coronaStatsCollection);
		}

		return createCoronaStatsFromCollection(coronaStatsCollection);
	}

	private CoronaStatsResponse createCoronaStatsFromCollection(final CoronaStatsCollection coronaStatsCollection) {

		List<CoronaStats> coronaStats = new ArrayList<>();

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

		for (Entry<String, Integer> entry : casesByCountry.entrySet()) {
			CoronaStats stats = new CoronaStats();
			stats.setCountry(entry.getKey());
			stats.setCases(entry.getValue());
			stats.setCasesSinceYesterday(casesDeltaByCountry.get(entry.getKey()));
			stats.setDeaths(deathsByCountry.get(entry.getKey()));
			stats.setMortalityRate(CoronaTrackerUtil.f.format(100.0 * stats.getDeaths() / stats.getCases()) + "%");
			stats.setDeathsSinceYesterday(deathsDeltaByCountry.get(entry.getKey()));
			stats.setRecoveries(recoveriesByCountry.get(entry.getKey()));
			stats.setRecoveryRate(CoronaTrackerUtil.f.format(100.0 * stats.getRecoveries() / stats.getCases()) + "%");
			stats.setFirstCaseReported(firstCaseCountries.stream()
					.filter(stat -> stat.getCountry().equalsIgnoreCase(entry.getKey())).count() > 0 ? true : false);
			stats.setFirstDeathReported(firstDeathCountries.stream()
					.filter(stat -> stat.getCountry().equalsIgnoreCase(entry.getKey())).count() > 0 ? true : false);
			coronaStats.add(stats);
		}

		return createCoronaStatsResponse(coronaStats);
	}

	private CoronaStatsResponse createCoronaStatsResponse(final List<CoronaStats> coronaStats) {

		CoronaSummaryStats coronaSummaryStats = new CoronaSummaryStats();
		coronaSummaryStats.setTotalCases(coronaStats.stream().collect(Collectors.summingInt(CoronaStats::getCases)));
		coronaSummaryStats.setTotalCasesSinceYesterday(
				coronaStats.stream().collect(Collectors.summingInt(CoronaStats::getCasesSinceYesterday)));
		coronaSummaryStats.setTotalDeaths(coronaStats.stream().collect(Collectors.summingInt(CoronaStats::getDeaths)));
		coronaSummaryStats.setTotalDeathsSinceYesterday(
				coronaStats.stream().collect(Collectors.summingInt(CoronaStats::getDeathsSinceYesterday)));
		coronaSummaryStats
				.setTotalRecoveries(coronaStats.stream().collect(Collectors.summingInt(CoronaStats::getRecoveries)));
		coronaSummaryStats.setMortalityRate(CoronaTrackerUtil.f
				.format(100.0 * coronaSummaryStats.getTotalDeaths() / coronaSummaryStats.getTotalCases()) + "%");
		coronaSummaryStats.setRecoveryRate(CoronaTrackerUtil.f
				.format(100.0 * coronaSummaryStats.getTotalRecoveries() / coronaSummaryStats.getTotalCases()) + "%");
		coronaSummaryStats.setCountriesWithFirstCase(coronaStats.stream().filter(stat -> stat.isFirstCaseReported())
				.filter(stat -> !CoronaTrackerUtil.filterCountries.contains(stat.getCountry()))
				.map(stat -> stat.getCountry()).collect(Collectors.toList()));
		coronaSummaryStats.setCountriesWithFirstDeath(coronaStats.stream().filter(stat -> stat.isFirstDeathReported())
				.filter(stat -> !CoronaTrackerUtil.filterCountries.contains(stat.getCountry()))
				.map(stat -> stat.getCountry()).collect(Collectors.toList()));

		CoronaStatsResponse coronaStatsResponse = new CoronaStatsResponse();
		coronaStatsResponse.setCoronaStats(coronaStats.stream()
				.sorted(Comparator.comparingInt(CoronaStats::getCases).reversed()).collect(Collectors.toList()));
		coronaStatsResponse.setCoronaSummaryStats(coronaSummaryStats);

		return coronaStatsResponse;
	}

}
