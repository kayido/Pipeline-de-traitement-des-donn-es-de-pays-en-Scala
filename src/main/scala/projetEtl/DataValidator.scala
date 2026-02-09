package projetEtl



import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import io.circe.syntax.*

import scala.util.{Failure, Success, Try}
import scala.io.Source
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets
import scala.language.postfixOps
object DataValidator {

  /**
   * Valide un restaurant selon les règles métier
   */
  def isValid(country: Country): Boolean = {
    if (country.capital.nonEmpty && 
      country.gdp.toString.nonEmpty && 
      country.name.nonEmpty &&
      country.continent.nonEmpty &&
      country.currency.nonEmpty &&
      country.population.toString.nonEmpty &&
      country.code.nonEmpty &&
      country.languages.nonEmpty &&
      country.area.toString.nonEmpty &&
      country.population > 0
    )
      true
    else false  
  }
  /**
   * Filtre les restaurants valides
   */
  def filterValid(countries: List[Country]): List[Country] = {
    countries.filter(isValid)
  }
  
  def duplicadedRemove(countries: List[Country]): List[Country] = {
    countries.distinctBy(_.code)
  }

}
