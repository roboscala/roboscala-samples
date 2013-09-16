import sbt._
import Keys._

import sbtrobovm.RobovmPlugin._

object ScaliOSBuild extends Build {
  lazy val root = RobovmProject(
    "ios",
    file("."),
    settings = Defaults.defaultSettings ++ Seq(
      version := "0.1",
      scalaVersion := "2.10.2",
      executableName := "ScaliOS"
    )
  )
}
