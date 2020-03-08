package com.shadow.coronatracker.model;

public class CoronaRecoveriesStats {

	private String state;
	private String country;
	private float latitude;
	private float longitude;
	private int recoveries;

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

	public int getRecoveries() {
		return recoveries;
	}

	public void setRecoveries(int recoveries) {
		this.recoveries = recoveries;
	}

	@Override
	public String toString() {
		return "CoronaRecoveriesStats [state=" + state + ", country=" + country + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", recoveries=" + recoveries + "]";
	}

}
