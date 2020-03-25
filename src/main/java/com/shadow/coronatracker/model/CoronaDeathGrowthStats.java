package com.shadow.coronatracker.model;

import java.util.Date;

public class CoronaDeathGrowthStats {

	private Date date;
	private Integer growth;
	private Integer delta;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
