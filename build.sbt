import sbt.Keys._
import sbt._
import xerial.sbt.pack.PackPlugin

val organizationName = "com.github.slowaner.scala"
val rootProjectName = "utils"

// Versions
// Testing
val scalatestVersion = "3.0.3"
val junitVersion = "4.12"

// JNA
val jnaVersion = "4.5.0"

lazy val commonSettings = Defaults.defaultConfigs ++ Seq(
  organization := organizationName,
  scalaVersion := "2.12.3",
  crossPaths := false,
  libraryDependencies ++= Seq(
    // Testing
    "org.scalatest" %% "scalatest" % scalatestVersion % Test,
    "junit" % "junit" % junitVersion % Test,
    // JNA
    // https://mvnrepository.com/artifact/net.java.dev.jna/jna
    "net.java.dev.jna" % "jna" % jnaVersion,
    // https://mvnrepository.com/artifact/net.java.dev.jna/jna-platform
    "net.java.dev.jna" % "jna-platform" % jnaVersion
  ),
  excludeFilter := new SimpleFileFilter(f => f.getName match {
    case ".gitignore" | ".gitkeep" => true
    case _ => false
  })
)

lazy val rootSettings = PackPlugin.packSettings ++ Seq(
  name := rootProjectName
)

lazy val root = Project(rootProjectName, file("."))
  .settings(commonSettings ++ rootSettings)
  .enablePlugins(PackPlugin)
