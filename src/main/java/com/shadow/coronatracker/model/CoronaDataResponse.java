package com.shadow.coronatracker.model;

import java.util.List;

public class CoronaDataResponse {

	private List<CoronaData> coronaStats;
	private CoronaSummaryStats coronaSummaryStats;

	public List<CoronaData> getCoronaStats() {
		return coronaStats;
	}

	public void setCoronaStats(List<CoronaData> coronaStats) {
		this.coronaStats = coronaStats;
	}

	public CoronaSummaryStats getCoronaSummaryStats() {
		return coronaSummaryStats;
	}

	public void setCoronaSummaryStats(CoronaSummaryStats coronaSummaryStats) {
		this.coronaSummaryStats = coronaSummaryStats;
	}

}
