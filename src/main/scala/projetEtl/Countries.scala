package projetEtl



case class Country(
                      name: String,
                      code : String,
                      capital: String,
                      continent: String,
                      population: Long,
                      area : Long,
                      gdp : Int,
                      languages : List[String],
                      currency : String
                    )

case class CountriesStats(
                     total_countries_parsed : Int,
                     total_countries_valid : Int,
                     parsing_errors : Int,
                     duplicates_removed : Int
                     )


case class TopCountriesPopulation (
                                  name : String,
                                  population : Long,
                                  continent : String
                                       )
case class CountryLanguage(
  name : String,
  languages : List[String]
)


case class AnalysisReport(
                           statistics :  CountriesStats,
                           TopCountriesPopulation : List[TopCountriesPopulation],
                           TopCountriesArea : List[Country],
                           TopCountriesGdp : List[Country],
                           AvgPopulationByContient : Map[String, Double],
                           CountryByContinent : Map[String, Int],
                           most_common_languages : Map[String, Int]
                         )