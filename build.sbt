import Dependencies.scalaTest
import org.typelevel.scalacoptions.ScalacOptions
import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion := "2.13.14"
ThisBuild / organization := "nl.codecraftr"

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / javacOptions ++= Seq("-source", "21", "-target", "21")
tpolecatScalacOptions += ScalacOptions.release("21")
tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement

lazy val commonSettings =
  libraryDependencies += scalaTest

lazy val root = project
  .enablePlugins(ScalafmtPlugin)
  .in(file("."))
  .settings(
    name := "graph-sandbox-scala",
    version := "0.1.0-SNAPSHOT",
    commonSettings,
    libraryDependencies += "org.scala-graph" %% "graph-core" % "2.0.1",
    libraryDependencies += "org.scala-graph" %% "graph-dot" % "2.0.0"
  )
