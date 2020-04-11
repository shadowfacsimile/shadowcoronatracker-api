package com.shadow.coronatracker.scheduler;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shadow.coronatracker.service.CoronaDataService;

@Component
public class CoronaDataScheduler {

	private static final Logger LOGGER = Logger.getLogger(CoronaDataScheduler.class.getName());

	@Autowired
	private CoronaDataService coronaDataService;

	@Scheduled(cron = "0 0 0/1 * * ?")
	public void syncCoronaData() {
		LOGGER.info(
				"***** Inside CoronaDataScheduler.syncCoronaData() / Fetching Data From Johns Hopkins CSSE Repo *****");

		coronaDataService.fetchCoronaDataFromJHCSSE();

		LOGGER.info("***** Inside CoronaDataScheduler.syncCoronaData() / Data synch completed *****");
	}

}
