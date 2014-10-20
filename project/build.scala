import sbt._
import Keys._

import com.typesafe.sbt.SbtProguard._
import ProguardKeys.{ merge, options, proguard }
import ProguardOptions.keepMain

import sbtrobovm.RobovmPlugin._

object SampleBuild extends Build {
  lazy val native = makeSample("native", "NativeRobo")
  lazy val hello = makeSample("hello", "HelloRobo")
  lazy val empty = makeSample("empty", "EmptyRobo")

  val roboVersion = "1.0.0-alpha-04"
  val roboDependencies = Seq("robovm-rt", "robovm-cocoatouch")
    .map("org.robovm" % _ % roboVersion)

    def makeSample(path: String, name: String, settings: Seq[Setting[_]] = Seq.empty): Project = {
      RobovmProject(path, file(path),
        settings = proguardSettings ++ settings ++ Seq(
          scalaVersion := "2.11.2",
          executableName := name,
          robovmDebug := true,
          libraryDependencies ++= roboDependencies,
          unmanagedResources in Compile += sourceDirectory.value / "assets",
          options in Proguard += ProguardConf.robovm,
          options in Proguard += keepMain("Main"),
          merge in Proguard := false
          // *NOTE* Due to a bug in LLVM, samples fail to build for the sim.
          // Uncomment this line if you are running in the simulator.
          // alternativeInputJars := Some((proguard in Proguard).value)
        )
      )
    }
}

object ProguardConf {
  val robovm = """
  # Ignore errors
  -dontskipnonpubliclibraryclassmembers
  -dontnote
  -dontwarn
  -ignorewarnings

  # RoboVM already optimizes
  -dontobfuscate
  -dontoptimize

  # Keep needed code
  -keep class org.robovm.** { *; }
  -keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,EnclosingMethod,*Annotation*
  -keep class java.lang.** { *; }
  -keep class java.util.zip.** { *; }
  -keep class libcore.** { *; }
  -keep class org.apache.harmony.lang.annotation.** { *; }
  -keepclasseswithmembers class * {
    native <methods>;
  }
  """
}
