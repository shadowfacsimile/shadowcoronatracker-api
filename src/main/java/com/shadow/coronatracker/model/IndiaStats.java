package com.shadow.coronatracker.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class IndiaStats {

	@SerializedName(value = "states_daily")
	List<IndiaStatesDaily> indiaStatesDailyList = new ArrayList<>();

	public List<IndiaStatesDaily> getIndiaStatesDailyList() {
		return indiaStatesDailyList;
	}

	public void setIndiaStatesDailyList(List<IndiaStatesDaily> indiaStatesDailyList) {
		this.indiaStatesDailyList = indiaStatesDailyList;
	}

}