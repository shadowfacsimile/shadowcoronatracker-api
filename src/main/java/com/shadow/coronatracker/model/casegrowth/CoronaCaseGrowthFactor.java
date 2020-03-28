package com.shadow.coronatracker.model.casegrowth;

import java.util.Date;

public class CoronaCaseGrowthFactor {
	
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
