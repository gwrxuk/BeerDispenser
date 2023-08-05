ThisBuild / scalaVersion := "2.13.11"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """BeerDispenser""",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "org.flywaydb" %% "flyway-play" % "6.0.0",
      "org.playframework.anorm" %% "anorm" % "2.6.7",
      "org.postgresql" % "postgresql" % "42.5.1"
    )
  )