package projetEtl


object Main extends App {

    println("----------------------------------")

    val datasets = List(
      "data_clean.json",
      "data_dirty.json",
      "data_large.json"
    )

    val results = datasets.map { dataset =>

      val startTime = System.nanoTime()

      println(s"\n=== Traitement du dataset : $dataset ===")

      val result = for {
        countries <- DataLoader.loadCountriesDirty(s"countries/$dataset")
        _ = println(s"Countries chargés : ${countries.length}")

        validCountries = DataValidator.filterValid(countries)
        _ = println(s"Countries valides : ${validCountries.length}")

        parsingErrors = countries.size - validCountries.size
        _ = println(s"Parsing Errors : $parsingErrors")

        distinctCountries = DataValidator.duplicadedRemove(validCountries)
        _ = println(s"Countries distincts : ${distinctCountries.length}")

        report = ReportGenerator.generateReport(distinctCountries)
        _ = println("Rapport généré")

        outputName = s"results_${dataset.replace(".json", "")}.json"
        _ <- ReportGenerator.writeReport(report, outputName)
        _ = println(s"Rapport écrit dans $outputName")

      } yield report

      val endTime = System.nanoTime()
      val durationMs = (endTime - startTime) / 1000000
      println(s"Temps de traitement du dataset $dataset : $durationMs ms")


      dataset -> result
    }


  results.foreach {
    case (dataset, Right(report)) =>

      println(s"\n STATISTIQUES : ${dataset}")
      println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
      //println(s"Nombre de country : ${report.statistics.total_countries_parsed}")
      //println(s"Country Valid : ${report.statistics.total_countries_valid}")
      //println(s"Parsing errors : ${report.statistics.parsing_errors}")
      //println(s"Duplicate Remove : ${report.statistics.duplicates_removed}")

      println("\n Nombre de pays par continents")
      report.CountryByContinent.foreach { country => println(s"${country._1} : ${country._2} countries") }
      println("\n TOP 10 Country Population")
      println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
      report.TopCountriesPopulation.foreach { country => println(s"${country.name} : ${country.population} hab") }
      println("\n Top 10 Country gpd")
      println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
      report.TopCountriesGdp.foreach { country => println(s"${country.name} : ${country.gdp}") }
      println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
      println("\n Top 10 Country By Area")
      report.TopCountriesGdp.foreach { country => println(s"${country.name} : ${country.area} Km2 ") }
      println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
      println("\n Average Population By Continent")
      report.AvgPopulationByContient.foreach { country => println(s"${country._1} : ${country._2} ") }

      println("\n Most common languages")
      report.most_common_languages.foreach { country => println(s"${country._1} : ${country._2}") }

      println("\n Pipeline ETL terminé avec succès !")

    case (dataset, Left(error)) => println(s"\n❌ Erreur lors du traitement de $dataset : $error")
  }
}