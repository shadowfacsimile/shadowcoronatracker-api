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

import com.shadow.coronatracker.model.CoronaCountryStats;
import com.shadow.coronatracker.model.CoronaStateStats;
import com.shadow.coronatracker.model.CoronaSummaryStats;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowth;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthCountry;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthFactor;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowth;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthCountry;
import com.shadow.coronatracker.service.CoronaDataService;

@RestController
@RequestMapping("/api")
public class CoronaRestService {

	private static final Logger LOGGER = Logger.getLogger(CoronaRestService.class.getName());

	@Autowired
	private CoronaDataService coronaDataService;

	@GetMapping("/stats/summary")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public CoronaSummaryStats getCoronaSummaryStats() {
		LOGGER.info("Inside getCoronaSummaryStats()");
		CoronaSummaryStats coronaSummaryStats = coronaDataService.getCoronaDataResponse().getCoronaSummaryStats();
		return coronaSummaryStats == null ? coronaDataService.fetchCoronaData().getCoronaSummaryStats()
				: coronaSummaryStats;
	}

	@GetMapping(value = { "/stats/countries", "/stats/countries/{country}" })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaCountryStats> getCoronaCountriesStats(
			@PathVariable(name = "country", required = false) String country) {
		LOGGER.info("Inside getCoronaCountriesStats()");

		List<CoronaCountryStats> coronaCountriesStats = coronaDataService.getCoronaDataResponse()
				.getCoronaCountriesStats();
		coronaCountriesStats = (coronaCountriesStats == null)
				? coronaDataService.fetchCoronaData().getCoronaCountriesStats()
				: coronaCountriesStats;
		List<CoronaCountryStats> filteredCoronaCountriesStats = (country == null) ? coronaCountriesStats
				: coronaCountriesStats.stream()
						.filter(stats -> stats.getLocation().getCountry().equalsIgnoreCase(country))
						.collect(Collectors.toList());
		return filteredCoronaCountriesStats;
	}

	@GetMapping(value = { "/stats/states", "/stats/states/{state}" })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaStateStats> getCoronaStatesStats(@PathVariable(name = "state", required = false) String state) {
		LOGGER.info("Inside getCoronaStatesStats()");

		List<CoronaStateStats> coronaStatesStats = coronaDataService.getCoronaDataResponse().getCoronaStatesStats();
		coronaStatesStats = (coronaStatesStats == null) ? coronaDataService.fetchCoronaData().getCoronaStatesStats()
				: coronaStatesStats;
		List<CoronaStateStats> filteredCoronaStatesStats = (state == null) ? coronaStatesStats
				: coronaStatesStats.stream().filter(stats -> stats.getLocation().getState().equalsIgnoreCase(state))
						.collect(Collectors.toList());
		return filteredCoronaStatesStats;
	}

	@GetMapping("/growth/cases")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaCaseGrowth> getCoronaCaseGrowthStats() {
		LOGGER.info("Inside getCoronaCaseGrowthStats()");
		List<CoronaCaseGrowth> coronaCaseGrowthStats = coronaDataService.getCoronaDataResponse()
				.getCoronaCaseGrowthStats();
		return coronaCaseGrowthStats == null ? coronaDataService.fetchCoronaData().getCoronaCaseGrowthStats()
				: coronaCaseGrowthStats;
	}

	@GetMapping(value = { "/growth/cases/countries", "/growth/cases/countries/{country}" })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaCaseGrowthCountry> getCoronaCaseGrowthCountriesStats(
			@PathVariable(name = "country", required = false) String country) {
		LOGGER.info("Inside getCoronaCaseGrowthCountryStats()");
		List<CoronaCaseGrowthCountry> coronaCaseGrowthCountriesStats = coronaDataService.getCoronaDataResponse()
				.getCoronaCaseGrowthCountriesStats();
		coronaCaseGrowthCountriesStats = (coronaCaseGrowthCountriesStats == null)
				? coronaDataService.fetchCoronaData().getCoronaCaseGrowthCountriesStats()
				: coronaCaseGrowthCountriesStats;

		List<CoronaCaseGrowthCountry> filteredCoronaCaseGrowthCountriesStats = (country == null)
				? coronaCaseGrowthCountriesStats
				: coronaCaseGrowthCountriesStats.stream().filter(stats -> stats.getCountry().equalsIgnoreCase(country))
						.collect(Collectors.toList());
		return filteredCoronaCaseGrowthCountriesStats;
	}

	@GetMapping("/growth/factors")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaCaseGrowthFactor> getCoronaCaseGrowthFactorStats() {
		LOGGER.info("Inside getCoronaCaseGrowthFactorStats()");
		List<CoronaCaseGrowthFactor> coronaCaseGrowthFactorStats = coronaDataService.getCoronaDataResponse()
				.getCoronaCaseGrowthFactorStats();
		return coronaCaseGrowthFactorStats == null
				? coronaDataService.fetchCoronaData().getCoronaCaseGrowthFactorStats()
				: coronaCaseGrowthFactorStats;
	}

	@GetMapping("/growth/deaths")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaDeathGrowth> getCoronaDeathGrowthStats() {
		LOGGER.info("Inside getCoronaDeathGrowthStats()");
		List<CoronaDeathGrowth> coronaDeathGrowthStats = coronaDataService.getCoronaDataResponse()
				.getCoronaDeathGrowthStats();
		return coronaDeathGrowthStats == null ? coronaDataService.fetchCoronaData().getCoronaDeathGrowthStats()
				: coronaDeathGrowthStats;
	}

	@GetMapping(value = { "/growth/deaths/countries", "/growth/deaths/countries/{country}" })
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaDeathGrowthCountry> getCoronaDeathGrowthCountriesStats(
			@PathVariable(name = "country", required = false) String country) {
		LOGGER.info("Inside getCoronaDeathGrowthCountryStats()");
		List<CoronaDeathGrowthCountry> coronaDeathGrowthCountriesStats = coronaDataService.getCoronaDataResponse()
				.getCoronaDeathGrowthCountriesStats();
		coronaDeathGrowthCountriesStats = (coronaDeathGrowthCountriesStats == null)
				? coronaDataService.fetchCoronaData().getCoronaDeathGrowthCountriesStats()
				: coronaDeathGrowthCountriesStats;

		List<CoronaDeathGrowthCountry> filteredCoronaDeathGrowthCountriesStats = (country == null)
				? coronaDeathGrowthCountriesStats
				: coronaDeathGrowthCountriesStats.stream().filter(stats -> stats.getCountry().equalsIgnoreCase(country))
						.collect(Collectors.toList());
		return filteredCoronaDeathGrowthCountriesStats;
	}

}
