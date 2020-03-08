package com.shadow.coronatracker.model;

public class CoronaCasesStats {

	private String state;
	private String country;
	private float latitude;
	private float longitude;
	private int cases;
	private int casesSinceYesterday;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
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

	@Override
	public String toString() {
		return "CoronaCasesStats [state=" + state + ", country=" + country + ", latitude=" + latitude + ", longitude="
				+ longitude + ", cases=" + cases + ", casesSinceYesterday=" + casesSinceYesterday + "]";
	}

}
