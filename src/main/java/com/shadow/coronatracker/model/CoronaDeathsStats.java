package com.shadow.coronatracker.model;

public class CoronaDeathsStats {

	private String state;
	private String country;
	private float latitude;
	private float longitude;
	private int deaths;
	private int deathsSinceYesterday;

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

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getDeathsSinceYesterday() {
		return deathsSinceYesterday;
	}

	public void setDeathsSinceYesterday(int deathsSinceYesterday) {
		this.deathsSinceYesterday = deathsSinceYesterday;
	}

	@Override
	public String toString() {
		return "CoronaDeathsStats [state=" + state + ", country=" + country + ", latitude=" + latitude + ", longitude="
				+ longitude + ", deaths=" + deaths + ", deathsSinceYesterday=" + deathsSinceYesterday + "]";
	}

}