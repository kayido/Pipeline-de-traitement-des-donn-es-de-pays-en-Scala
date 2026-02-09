package projetEtl

object StatsCalculator {
  def calculateStats(countries: List[Country]): CountriesStats = {

    val validCountries = DataValidator.filterValid(countries)
    new CountriesStats(
      countries.size,
      validCountries.size,
      countries.size - validCountries.size,
      validCountries.size - DataValidator.duplicadedRemove(countries).size
    )
  }

  def top_10_by_population(countries: List[Country], n: Int = 10): List[TopCountriesPopulation] = {
    countries.sortBy(_.population)(Ordering.Long.reverse).take(n).map(country => new TopCountriesPopulation(country.name, country.population, country.continent))
  }

  def top_10_by_area(countries: List[Country], n: Int = 10): List[Country] = {
    countries.sortBy(_.area)(Ordering.Long.reverse).take(n)
  }
  
  def top_10_by_gdp(countries: List[Country], n: Int = 10): List[Country] = {
    countries.sortBy(_.gdp)(Ordering.Int.reverse).take(n)
  }
  
  def countryByContinent(countries: List[Country]): Map[String, Int] = {
    countries.groupBy(_.continent).map { case (continent, list) => continent -> list.size }
  }

  def AveragePopulationByContinent(countries: List[Country]): Map[String, Double] = {
    countries.groupBy(_.continent).map {
      case (continent, list) => continent -> list.foldLeft(0.0) { (acc, n) => acc + n.population } / list.size
    }
  }
  def most_common_languages(countries: List[Country]): Map[String, Int] = {
    countries
      .flatMap(_.languages)
      .groupBy(identity)
      .map { case (lang, langs) => lang -> langs.size }
      .toList
      .sortBy { case (_, count) => -count }
      .take(10)
      .toMap
  }

}