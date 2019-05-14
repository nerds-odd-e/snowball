name := """play-main"""
organization := "com.odde.massivemailer"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"