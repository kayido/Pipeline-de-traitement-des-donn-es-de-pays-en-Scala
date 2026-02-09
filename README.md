# ✅ mon-projet-etl

**Projet ETL (Scala)** pour parser, valider et analyser des jeux de données sur les pays du monde.

---

## 🚀 Présentation

Ce projet exécute un pipeline ETL simple : il charge des fichiers JSON de pays, filtre les données invalides, supprime les doublons, calcule des statistiques (top 10 par population, superficie, répartition par continent, langues les plus courantes, etc.) et produit un rapport JSON.


## 🔧 Fonctionnalités principales

- Chargement des datasets : `countries/data_clean.json`, `countries/data_dirty.json`, `countries/data_large.json`
- Validation des enregistrements (population > 0, champs non vides, etc.)
- Suppression des doublons (par `code`)
- Calculs statistiques : top 10 population / area / gdp, counts et moyennes par continent, langues les plus communes
- Écriture d'un fichier de sortie JSON : `results_<dataset>.json`


## 📁 Structure du projet

- `countries/` : jeux de données source
- `src/main/scala/projetEtl/` : code source
  - `Main.scala` : orchestration du pipeline (exécute les 3 datasets)
  - `DataLoader.scala` : lecture/parsing JSON
  - `DataValidator.scala` : règles de validation et suppression de doublons
  - `StatsCalculator.scala` : calculs et agrégations
  - `ReportGenerator.scala` : génération et écriture du rapport JSON
  - `Countries.scala` : modèles de données (case classes)
- `build.sbt` : configuration SBT (Scala 3.3.7, dépendances Circe)




## Installation & exécution

1. Ouvrir un terminal à la racine du projet :

```bash
cd mon-projet-etl
```

2. Compiler :

```bash
sbt compile
```

3. Lancer le pipeline (exécute les 3 datasets listés dans `Main`):

```bash
sbt run
```

Fichiers de sortie générés : `results_data_clean.json`, `results_data_dirty.json`, `results_data_large.json` (créés à la racine du projet).


## Tests

Le projet utilise `munit` pour les tests (dépendance déclarée). Exécutez :

```bash
sbt test
```
