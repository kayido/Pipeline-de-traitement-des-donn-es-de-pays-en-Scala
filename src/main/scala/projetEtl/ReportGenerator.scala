package projetEtl

import io.circe.*
import io.circe.generic.auto.*
import io.circe.syntax.*

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

object ReportGenerator {
  def generateReport(countries : List[Country]): AnalysisReport = {
    // TODO: Créer le rapport avec StatsCalculator
    new AnalysisReport(
      StatsCalculator.calculateStats(countries),
      StatsCalculator.top_10_by_population(countries),
      StatsCalculator.top_10_by_area(countries),
      StatsCalculator.top_10_by_gdp(countries),
      StatsCalculator.AveragePopulationByContinent(countries),
      StatsCalculator.countryByContinent(countries),
      StatsCalculator.most_common_languages(countries)
    )
  }

  def writeReport(report: AnalysisReport, filename: String): Either[String, Unit] = {
    // TODO: Convertir en JSON et écrire dans le fichier
    Try { val reportJson = report.asJson.spaces2 
      Files.write( Paths.get(filename), reportJson.getBytes(StandardCharsets.UTF_8) ) } 
    match { 
      case Success(_) => Right(()) 
      case Failure(ex) => Left(s"Erreur lors de l'écriture du fichier : ${ex.getMessage}") 
    }
    
  }
}