import sbt._
import Keys._

import sbtrobovm.RobovmPlugin._

object ScaliOSBuild extends Build {
  lazy val native = makeDemo("native", "Native Robo")
  lazy val hello = makeDemo("hello", "Hello Robo")
  lazy val empty = makeDemo("empty", "Empty Robo")

  val roboVersion = "1.0.0-alpha-04"
  val roboDependencies = Seq("org.robovm" % "robovm-compiler" % roboVersion
                          ,"org.robovm" % "robovm-rt" % roboVersion
                          ,"org.robovm" % "robovm-objc" % roboVersion
                          , "org.robovm" % "robovm-cocoatouch" % roboVersion
                          , "org.robovm" % "robovm-cacerts-full" % roboVersion
)
  def makeDemo(path: String, name: String, settings: Seq[Setting[_]] = Seq.empty): Project = {
    RobovmProject(path, file(path),
      settings = Defaults.defaultSettings ++ settings ++ Seq(
        scalaVersion := "2.11.2",
        libraryDependencies ++= roboDependencies,
        executableName := name
      )
    )
  }
}
