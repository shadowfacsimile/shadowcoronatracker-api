package com.shadow.coronatracker.model;

public class CoronaCasesStats {

	private Location location;
	private int cases;
	private int casesSinceYesterday;
	private boolean firstCaseReported;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getCases() {
		return cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}

	public int getCasesSinceYesterday() {
		return casesSinceYesterday;
	}

	public void setCasesSinceYesterday(int casesSinceYesterday) {
		this.casesSinceYesterday = casesSinceYesterday;
	}

	public boolean isFirstCaseReported() {
		return firstCaseReported;
	}

	public void setFirstCaseReported(boolean firstCaseReported) {
		this.firstCaseReported = firstCaseReported;
	}

}
