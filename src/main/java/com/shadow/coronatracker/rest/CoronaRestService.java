package com.shadow.coronatracker.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.coronatracker.model.CoronaCountryStats;
import com.shadow.coronatracker.model.CoronaStateStats;
import com.shadow.coronatracker.model.CoronaSummaryStats;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthCountry;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowthFactor;
import com.shadow.coronatracker.model.casegrowth.CoronaCaseGrowth;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowthCountry;
import com.shadow.coronatracker.model.deathgrowth.CoronaDeathGrowth;
import com.shadow.coronatracker.service.CoronaDataService;

@RestController
@RequestMapping("/api")
public class CoronaRestService {

	@Autowired
	private CoronaDataService coronaDataService;

	@GetMapping("/stats/summary")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public CoronaSummaryStats getCoronaSummaryStats() {
		return coronaDataService.getCoronaSummaryStats();
	}

	@GetMapping("/stats/countries")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaCountryStats> getCoronaCountriesStats() {
		return coronaDataService.getCoronaCountriesStats();
	}

	@GetMapping("/stats/states")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaStateStats> getCoronaStatesStats() {
		return coronaDataService.getCoronaStatesStats();
	}

	@GetMapping("/growth/cases/all")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaCaseGrowth> getCoronaCaseGrowthStats() {
		return coronaDataService.getCoronaCaseGrowthStats();
	}

	@GetMapping("/growth/cases/countries")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaCaseGrowthCountry> getCoronaCaseGrowthCountriesStats() {
		return coronaDataService.getCoronaCaseGrowthCountriesStats();
	}

	@GetMapping("/growth/factors/all")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaCaseGrowthFactor> getCoronaCaseGrowthFactorStats() {
		return coronaDataService.getCoronaCaseGrowthFactorStats();
	}

	@GetMapping("/growth/deaths/all")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaDeathGrowth> getCoronaDeathGrowthStats() {
		return coronaDataService.getCoronaDeathGrowthStats();
	}

	@GetMapping("/growth/deaths/countries")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public List<CoronaDeathGrowthCountry> getCoronaDeathGrowthCountriesStats() {
		return coronaDataService.getCoronaDeathGrowthCountriesStats();
	}

}
