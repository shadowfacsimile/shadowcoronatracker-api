package com.shadow.coronatracker.model;

import java.util.Date;

public class CoronaCaseGrowthCountryStats {

	private Date date;
	private Integer growth;

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

}
