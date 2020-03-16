package com.shadow.coronatracker.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shadow.coronatracker.model.CoronaDataResponse;
import com.shadow.coronatracker.service.CoronaDataService;

@RestController
@RequestMapping("/api")
public class CoronaRestService {

	@Autowired
	private CoronaDataService coronaDataService;

	@GetMapping("/stats")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public CoronaDataResponse getCoronaCasesStats() throws IOException, InterruptedException {
		return coronaDataService.fetchCoronaData();
	}

}
