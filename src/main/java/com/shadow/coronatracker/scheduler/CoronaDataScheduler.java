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

	@Scheduled(cron = "0 0/15 * * * ?")
	public void syncCoronaData() {
		LOGGER.info(
				"***** Inside CoronaDataScheduler.fetchCoronaData() / Fetching Data From Johns Hopkins CSSE Repo *****");

		coronaDataService.fetchCoronaData();

		LOGGER.info("***** Inside CoronaDataScheduler.fetchCoronaData() / Data synch completed *****");
	}

}
