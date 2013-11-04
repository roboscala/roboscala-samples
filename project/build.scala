import sbt._
import Keys._

import sbtrobovm.RobovmPlugin._

object ScaliOSBuild extends Build {
  lazy val hello = makeDemo("hello", "Hello Robo")
  lazy val empty = makeDemo("empty", "Empty Robo")

  def makeDemo(path: String, name: String, settings: Seq[Setting[_]] = Seq.empty): Project = {
    RobovmProject(path, file(path),
      settings = Defaults.defaultSettings ++ settings ++ Seq(
        scalaVersion := "2.10.3",
        executableName := name
      )
    )
  }
}
