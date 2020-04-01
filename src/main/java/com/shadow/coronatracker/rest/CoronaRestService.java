package com.shadow.coronatracker.rest;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.coronatracker.model.casegrowth.CasesGrowth;
import com.shadow.coronatracker.model.casegrowth.CountryCasesGrowth;
import com.shadow.coronatracker.model.deathgrowth.CountryDeathsGrowth;
import com.shadow.coronatracker.model.deathgrowth.DeathsGrowth;
import com.shadow.coronatracker.model.summary.CountrySummary;
import com.shadow.coronatracker.model.summary.StateSummary;
import com.shadow.coronatracker.model.summary.Summary;
import com.shadow.coronatracker.service.CoronaDataService;

@RestController
@RequestMapping("/api")
public class CoronaRestService {

	private static final Logger LOGGER = Logger.getLogger(CoronaRestService.class.getName());

	@Autowired
	private CoronaDataService coronaDataService;

	@GetMapping("/stats/summary")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public Summary getSummaryStats() {
		LOGGER.info("Inside getSummaryStats()");

		Summary summaryStats = coronaDataService.getCoronaDataResponse().getSummaryStats();

		return summaryStats == null ? coronaDataService.fetchCoronaDataFromJHCSSE().getSummaryStats() : summaryStats;
	}

	@GetMapping(value = { "/stats/countries", "/stats/countries/{country}" })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CountrySummary> getCountrySummaryStats(
			@PathVariable(name = "country", required = false) String country) {
		LOGGER.info("Inside getCountrySummaryStats()");

		List<CountrySummary> countriesStats = coronaDataService.getCoronaDataResponse().getCountrySummaryStats();

		return (countriesStats == null) ? coronaDataService.fetchCoronaDataFromJHCSSE().getCountrySummaryStats()
				: (country == null) ? countriesStats
						: countriesStats.stream()
								.filter(stats -> stats.getLocation().getCountry().equalsIgnoreCase(country))
								.collect(Collectors.toList());
	}

	@GetMapping(value = { "/stats/states", "/stats/states/{state}" })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<StateSummary> getStateSummaryStats(@PathVariable(name = "state", required = false) String state) {
		LOGGER.info("Inside getStateSummaryStats()");

		List<StateSummary> statesStats = coronaDataService.getCoronaDataResponse().getStateSummaryStats();

		return (statesStats == null) ? coronaDataService.fetchCoronaDataFromJHCSSE().getStateSummaryStats()
				: (state == null) ? statesStats
						: statesStats.stream().filter(stats -> stats.getLocation().getState().equalsIgnoreCase(state))
								.collect(Collectors.toList());
	}

	@GetMapping("/growth/cases")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CasesGrowth> getCasesGrowthStats() {
		LOGGER.info("Inside getCasesGrowthStats()");

		List<CasesGrowth> caseGrowthStats = coronaDataService.getCoronaDataResponse().getCasesGrowthStats();

		return caseGrowthStats == null ? coronaDataService.fetchCoronaDataFromJHCSSE().getCasesGrowthStats()
				: caseGrowthStats;
	}

	@GetMapping(value = { "/growth/cases/countries", "/growth/cases/countries/{country}" })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CountryCasesGrowth> getCountryCasesGrowthStats(
			@PathVariable(name = "country", required = false) String country) {
		LOGGER.info("Inside getCountryCasesGrowthStats()");

		List<CountryCasesGrowth> caseGrowthCountriesStats = coronaDataService.getCoronaDataResponse()
				.getCountryCasesGrowthStats();

		return (caseGrowthCountriesStats == null)
				? coronaDataService.fetchCoronaDataFromJHCSSE().getCountryCasesGrowthStats()
				: (country == null) ? caseGrowthCountriesStats
						: caseGrowthCountriesStats.stream()
								.filter(stats -> stats.getCountry().equalsIgnoreCase(country))
								.collect(Collectors.toList());
	}

	@GetMapping("/growth/deaths")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<DeathsGrowth> getDeathsGrowthStats() {
		LOGGER.info("Inside getDeathsGrowthStats()");

		List<DeathsGrowth> deathGrowthStats = coronaDataService.getCoronaDataResponse().getDeathsGrowthStats();

		return deathGrowthStats == null ? coronaDataService.fetchCoronaDataFromJHCSSE().getDeathsGrowthStats()
				: deathGrowthStats;
	}

	@GetMapping(value = { "/growth/deaths/countries", "/growth/deaths/countries/{country}" })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CountryDeathsGrowth> getCountryDeathsGrowthStats(
			@PathVariable(name = "country", required = false) String country) {
		LOGGER.info("Inside getCountryDeathsGrowthStats()");

		List<CountryDeathsGrowth> deathGrowthCountriesStats = coronaDataService.getCoronaDataResponse()
				.getCountryDeathsGrowthStats();

		return (deathGrowthCountriesStats == null)
				? coronaDataService.fetchCoronaDataFromJHCSSE().getCountryDeathsGrowthStats()
				: (country == null) ? deathGrowthCountriesStats
						: deathGrowthCountriesStats.stream()
								.filter(stats -> stats.getCountry().equalsIgnoreCase(country))
								.collect(Collectors.toList());
	}

}
