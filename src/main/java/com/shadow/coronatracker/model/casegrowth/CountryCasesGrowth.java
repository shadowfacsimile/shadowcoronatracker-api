package com.shadow.coronatracker.model.casegrowth;

import java.util.List;

public class CountryCasesGrowth {

	private String country;
	private List<CasesGrowth> casesGrowths;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<CasesGrowth> getCasesGrowths() {
		return casesGrowths;
	}

	public void setCasesGrowths(List<CasesGrowth> casesGrowths) {
		this.casesGrowths = casesGrowths;
	}

}
