API For Yet Another Novel Coronavirus COVID-19 Pandemic Statistics Tracker

To lauch the application -
mvn clean install liberty:run-server

List of API:
/ Summary stats overall, country level and state level -
  http://localhost:9080/ShadowCoronaTracker/api/stats/summary/
  http://localhost:9080/ShadowCoronaTracker/api/stats/countries/{country}
  http://localhost:9080/ShadowCoronaTracker/api/stats/states/{state}

/ Cases growth stats -
  http://localhost:9080/ShadowCoronaTracker/api/growth/cases/
  http://localhost:9080/ShadowCoronaTracker/api/growth/cases/countries/{country}

/ Deaths growth stats -
  http://localhost:9080/ShadowCoronaTracker/api/growth/deaths/
  http://localhost:9080/ShadowCoronaTracker/api/growth/deaths/countries/{country}

