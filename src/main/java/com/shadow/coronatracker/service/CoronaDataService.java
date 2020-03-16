package com.shadow.coronatracker.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.shadow.coronatracker.model.CoronaCaseGrowthStats;
import com.shadow.coronatracker.model.CoronaCasesStats;
import com.shadow.coronatracker.model.CoronaData;
import com.shadow.coronatracker.model.CoronaDataResponse;
import com.shadow.coronatracker.model.CoronaDeathsStats;
import com.shadow.coronatracker.model.CoronaRecoveriesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.CoronaSummaryStats;
import com.shadow.coronatracker.model.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

@Service
public class CoronaDataService {

	private static final Logger LOGGER = Logger.getLogger(CoronaDataService.class);

	public CoronaDataResponse fetchCoronaData() throws IOException, InterruptedException {

		CoronaStatsCollection coronaStatsCollection = fetchCoronaStatsCollection();

		CoronaDataResponse coronaDataResponse = createCoronaDataResponse(
				createCoronaDataListFromStatsCollection(coronaStatsCollection));

		coronaDataResponse.setCoronaCaseGrowthStats(createCoronaCaseGrowthDataResponse(coronaStatsCollection));

		return coronaDataResponse;
	}

	private CoronaStatsCollection fetchCoronaStatsCollection() throws IOException, InterruptedException {

		HttpClient httpClient = HttpClient.newHttpClient();

		CoronaStatsCollection coronaStatsCollection = new CoronaStatsCollection();

		for (Statistics statistics : Statistics.values()) {
			LOGGER.info("Statistics: " + statistics);

			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(statistics.getUrl())).build();
			LOGGER.info("Connecting to: " + statistics.getUrl());

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			LOGGER.info("Response code " + response.statusCode());

			statistics.getParser().parse(response, coronaStatsCollection);
		}

		return coronaStatsCollection;
	}

	private List<CoronaData> createCoronaDataListFromStatsCollection(
			final CoronaStatsCollection coronaStatsCollection) {

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

		List<CoronaData> coronaDataList = new ArrayList<>();

		for (Entry<String, Integer> entry : casesByCountry.entrySet()) {
			CoronaData stats = new CoronaData();
			stats.setCountry(entry.getKey());
			stats.setCases(entry.getValue());
			stats.setCasesSinceYesterday(casesDeltaByCountry.get(entry.getKey()));
			stats.setDeaths(deathsByCountry.get(entry.getKey()));
			stats.setMortalityRate((double) stats.getDeaths() / stats.getCases());
			stats.setDeathsSinceYesterday(deathsDeltaByCountry.get(entry.getKey()));
			stats.setRecoveries(recoveriesByCountry.get(entry.getKey()));
			stats.setRecoveryRate((double) stats.getRecoveries() / stats.getCases());
			stats.setFirstCaseReported(firstCaseCountries.stream()
					.filter(stat -> stat.getCountry().equalsIgnoreCase(entry.getKey())).count() > 0 ? true : false);
			stats.setFirstDeathReported(firstDeathCountries.stream()
					.filter(stat -> stat.getCountry().equalsIgnoreCase(entry.getKey())).count() > 0 ? true : false);
			coronaDataList.add(stats);
		}

		return coronaDataList;
	}

	private CoronaDataResponse createCoronaDataResponse(final List<CoronaData> coronaDataList) {

		CoronaDataResponse coronaDataResponse = new CoronaDataResponse();
		coronaDataResponse.setCoronaStats(coronaDataList.stream()
				.sorted(Comparator.comparingInt(CoronaData::getCases).reversed()).collect(Collectors.toList()));
		coronaDataResponse.setCoronaSummaryStats(createCoronaSummaryStats(coronaDataList));

		return coronaDataResponse;
	}

	private CoronaSummaryStats createCoronaSummaryStats(final List<CoronaData> coronaDataList) {

		CoronaSummaryStats coronaSummaryStats = new CoronaSummaryStats();
		coronaSummaryStats.setTotalCases(coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getCases)));
		coronaSummaryStats.setTotalCasesSinceYesterday(
				coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getCasesSinceYesterday)));
		coronaSummaryStats
				.setTotalDeaths(coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getDeaths)));
		coronaSummaryStats.setTotalDeathsSinceYesterday(
				coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getDeathsSinceYesterday)));
		coronaSummaryStats
				.setTotalRecoveries(coronaDataList.stream().collect(Collectors.summingInt(CoronaData::getRecoveries)));
		coronaSummaryStats
				.setMortalityRate((double) coronaSummaryStats.getTotalDeaths() / coronaSummaryStats.getTotalCases());
		coronaSummaryStats
				.setRecoveryRate((double) coronaSummaryStats.getTotalRecoveries() / coronaSummaryStats.getTotalCases());
		coronaSummaryStats.setCountriesWithFirstCase(coronaDataList.stream().filter(stat -> stat.isFirstCaseReported())
				.filter(stat -> !CoronaTrackerUtil.filterCountries.contains(stat.getCountry()))
				.map(stat -> stat.getCountry()).collect(Collectors.toList()));
		coronaSummaryStats
				.setCountriesWithFirstDeath(coronaDataList.stream().filter(stat -> stat.isFirstDeathReported())
						.filter(stat -> !CoronaTrackerUtil.filterCountries.contains(stat.getCountry()))
						.map(stat -> stat.getCountry()).collect(Collectors.toList()));

		return coronaSummaryStats;
	}

	private List<CoronaCaseGrowthStats> createCoronaCaseGrowthDataResponse(
			final CoronaStatsCollection fetchCoronaStatsCollection) {

		List<CoronaCaseGrowthStats> caseGrowthStatsList = new ArrayList<>();

		for (Entry<Date, Double> caseGrowth : fetchCoronaStatsCollection.getCoronaCasesGrowthFactors().entrySet()) {
			CoronaCaseGrowthStats caseGrowthStats = new CoronaCaseGrowthStats();
			caseGrowthStats.setDate(caseGrowth.getKey());
			caseGrowthStats.setGrowthFactor(caseGrowth.getValue());
			caseGrowthStatsList.add(caseGrowthStats);
		}

		return caseGrowthStatsList.stream().sorted(Comparator.comparing(CoronaCaseGrowthStats::getDate).reversed())
				.collect(Collectors.toList());
	}

}
