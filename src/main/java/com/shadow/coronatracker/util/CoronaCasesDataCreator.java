package com.shadow.coronatracker.util;

import java.util.Comparator;
import java.util.stream.Collectors;

import com.shadow.coronatracker.model.CoronaData;
import com.shadow.coronatracker.model.CoronaDataResponse;
import com.shadow.coronatracker.model.CoronaStatsCollection;

public class CoronaCasesDataCreator implements CoronaDataCreator {

	@Override
	public void create(CoronaStatsCollection coronaStatsCollection, CoronaDataResponse coronaDataResponse) {

		coronaDataResponse.setCoronaStats(coronaStatsCollection.getCoronaDataList().stream()
				.sorted(Comparator.comparingInt(CoronaData::getCases).reversed()).collect(Collectors.toList()));

	}

}
