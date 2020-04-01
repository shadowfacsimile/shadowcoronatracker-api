package com.shadow.coronatracker.model.casegrowth;

public class CasesGrowth {

	private String date;
	private Integer growth;
	private Integer delta;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getGrowth() {
		return growth;
	}

	public void setGrowth(Integer growth) {
		this.growth = growth;
	}

	public Integer getDelta() {
		return delta;
	}

	public void setDelta(Integer delta) {
		this.delta = delta;
	}

}
