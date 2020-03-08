package com.shadow.coronatracker.model;

import java.util.List;

public class CoronaStatsResponse {

	private List<CoronaStats> coronaStats;
	private CoronaSummaryStats coronaSummaryStats;

	public List<CoronaStats> getCoronaStats() {
		return coronaStats;
	}

	public void setCoronaStats(List<CoronaStats> coronaStats) {
		this.coronaStats = coronaStats;
	}

	public CoronaSummaryStats getCoronaSummaryStats() {
		return coronaSummaryStats;
	}

	public void setCoronaSummaryStats(CoronaSummaryStats coronaSummaryStats) {
		this.coronaSummaryStats = coronaSummaryStats;
	}

}
