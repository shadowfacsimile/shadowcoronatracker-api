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
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shadow.coronatracker.model.CoronaCasesStats;
import com.shadow.coronatracker.model.CoronaData;
import com.shadow.coronatracker.model.CoronaDataResponse;
import com.shadow.coronatracker.model.CoronaDeathsStats;
import com.shadow.coronatracker.model.CoronaRecoveriesStats;
import com.shadow.coronatracker.model.CoronaStatsCollection;
import com.shadow.coronatracker.model.ResponseStatistics;
import com.shadow.coronatracker.model.Statistics;
import com.shadow.coronatracker.util.CoronaTrackerUtil;

@Service
public class CoronaDataService {

	public CoronaDataResponse fetchCoronaData() throws IOException, InterruptedException {

		CoronaStatsCollection coronaStatsCollection = createCoronaStatsCollectionByFetchingData();
		createCoronaDataListFromStatsCollection(coronaStatsCollection);
		CoronaDataResponse coronaDataResponse = createCoronaDataResponse(coronaStatsCollection);

		return coronaDataResponse;
	}

	private CoronaStatsCollection createCoronaStatsCollectionByFetchingData() throws IOException, InterruptedException {

		HttpClient httpClient = HttpClient.newHttpClient();

		CoronaStatsCollection coronaStatsCollection = new CoronaStatsCollection();

		for (Statistics statistics : Statistics.values()) {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(statistics.getUrl())).build();
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			statistics.getParser().parse(statistics, CoronaTrackerUtil.convertResponseToCSVRecord(response),
					coronaStatsCollection);
		}

		return coronaStatsCollection;
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

		List<CoronaData> coronaDataList = new ArrayList<>();

		for (Entry<String, Integer> entry : casesByCountry.entrySet()) {
			CoronaData stats = new CoronaData();
			stats.setCountry(entry.getKey());
			stats.setCases(entry.getValue());
			stats.setCasesSinceYesterday(casesDeltaByCountry.get(entry.getKey()));
			stats.setDeaths(deathsByCountry.get(entry.getKey()));
			stats.setMortalityRate(stats.getCases() == 0 ? 0 : (double) stats.getDeaths() / stats.getCases());
			stats.setDeathsSinceYesterday(deathsDeltaByCountry.get(entry.getKey()));
			stats.setRecoveries(recoveriesByCountry.get(entry.getKey()));
			stats.setRecoveryRate(stats.getCases() == 0 ? 0 : (double) stats.getRecoveries() / stats.getCases());
			stats.setFirstCaseReported(firstCaseCountries.stream()
					.filter(stat -> stat.getCountry().equalsIgnoreCase(entry.getKey())).count() > 0 ? true : false);
			stats.setFirstDeathReported(firstDeathCountries.stream()
					.filter(stat -> stat.getCountry().equalsIgnoreCase(entry.getKey())).count() > 0 ? true : false);
			coronaDataList.add(stats);
		}

		coronaStatsCollection.setCoronaDataList(coronaDataList);
	}

	private CoronaDataResponse createCoronaDataResponse(final CoronaStatsCollection coronaStatsCollection) {

		CoronaDataResponse coronaDataResponse = new CoronaDataResponse();

		for (ResponseStatistics stats : ResponseStatistics.values())
			stats.getCoronaGrowthDataCreator().create(coronaStatsCollection, coronaDataResponse);

		return coronaDataResponse;
	}

}
