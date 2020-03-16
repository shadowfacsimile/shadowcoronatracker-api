package com.shadow.coronatracker.model;

import java.util.Date;

public class CoronaCaseGrowthStats {

	private Date date;
	private double growthFactor;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getGrowthFactor() {
		return growthFactor;
	}

	public void setGrowthFactor(double growthFactor) {
		this.growthFactor = growthFactor;
	}

}
