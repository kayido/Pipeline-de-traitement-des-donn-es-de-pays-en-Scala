package projetEtl

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import scala.io.Source
import scala.util.{Try, Success, Failure}


object DataLoader {
  def loadCountries(filename: String): Either[String, List[Country]] =
    Try {
      val source = Source.fromFile(filename)
      val content = source.mkString
      source.close()
      content
    } match {
      case Success(jsonString) => {
        decode[List[Country]](jsonString).left.map(_.getMessage)
      }
      case scala.util.Failure(_) => Left("Erreur de parsing")
    }


  def loadCountriesDirty(filename: String): Either[String, List[Country]] =
    Try {
      val source = Source.fromFile(filename)
      val content = source.mkString
      source.close()
      content
    } match {
      case Success(jsonString) =>
        val tmp: Either[String, List[Json]] =
          decode[List[Json]](jsonString).left.map(_.getMessage)

        tmp.map { jsonList =>
          jsonList.flatMap { json =>
            json.as[Country].toOption
          }
        }

      case Failure(_) =>
        Left("Erreur de parsing")
    }
}