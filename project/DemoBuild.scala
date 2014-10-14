import com.typesafe.sbt.SbtProguard
import com.typesafe.sbt.SbtProguard._
import sbt.Keys._
import sbt._
import sbtrobovm.RobovmPlugin._

object DemoBuild extends Build {
  lazy val native = makeDemo("native", "Native Robo")
  lazy val hello = makeDemo("hello", "Hello Robo")
  lazy val empty = makeDemo("empty", "Empty Robo")

  val roboVersion = "0.0.14"
  val roboDependencies = Seq("org.robovm" % "robovm-compiler" % roboVersion
                          ,"org.robovm" % "robovm-rt" % roboVersion
                          ,"org.robovm" % "robovm-objc" % roboVersion
                          , "org.robovm" % "robovm-cocoatouch" % roboVersion
                          , "org.robovm" % "robovm-cacerts-full" % roboVersion
)
  def makeDemo(path: String, name: String, settings: Seq[Setting[_]] = Seq.empty): Project = {
    RobovmProject(path, file(path),
      settings = Defaults.defaultSettings ++ settings ++ Seq(
        robovmDebug := true,
        scalaVersion := "2.10.3",
        libraryDependencies ++= roboDependencies,
        executableName := name
      )
    )
  }

  val proguardedRoboVersion = "1.0.0-alpha-04"
  lazy val proguarded:Project = RobovmProject("proguarded",file("proguarded"),
    settings = proguardSettings ++ Seq(//NOTE: Notice `proguardSettings` added here
      scalaVersion := "2.11.2",
      executableName := "Proguarded Robo",
      robovmDebug := true, //Use this to make sure that RoboVM's debug info is visible when compiling. Not necessary, but useful.
      libraryDependencies ++= Seq(
        "org.robovm" % "robovm-rt" % proguardedRoboVersion,
        "org.robovm" % "robovm-objc" % proguardedRoboVersion,
        "org.robovm" % "robovm-cocoatouch" % proguardedRoboVersion
      ),
      //Proguard settings
      //Proguard will generate a lot of errors. Don't worry about them.
      ProguardKeys.options in Proguard ++= Seq("-dontskipnonpubliclibraryclassmembers","-dontnote", "-dontwarn", "-ignorewarnings"),
      //Obfuscating is not needed and robovm will do its own optimization, so this is useless
      ProguardKeys.options in Proguard ++= Seq("-dontobfuscate", "-dontoptimize"),
      //Need to keep some essential classes
      ProguardKeys.options in Proguard ++= Seq(
        ProguardOptions.keepMain("Main"),
        "-keep class com.badlogic.gdx.scenes.scene2d.ui.** { *; }",//If you are not using libGDX you can omit this line
        "-keep class org.robovm.** { *; }",
        "-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,EnclosingMethod,*Annotation*",
        "-keep class java.lang.** { *; }",
        "-keep class java.util.zip.** { *; }",
        "-keep class libcore.** { *; }",
        "-keep class org.apache.harmony.lang.annotation.** { *; }",
        "-keepclasseswithmembers class * {\n    native <methods>;\n}",
        "-verbose" //It is nice to see some more info
      ),
      //Merging jars is actually contraproductive here
      ProguardKeys.merge in Proguard := false,
      //This line will tell sbt-robovm to use proguarded jars instead of normally compiled ones
      alternativeInputJars := Some((SbtProguard.ProguardKeys.proguard in (DemoBuild.proguarded,Proguard)).value)
    )
  )
}
