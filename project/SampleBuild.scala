import sbt.Keys._
import sbt._
import sbtrobovm.RobovmPlugin._

object SampleBuild extends Build {

  private val sharedSettings = Seq(
    scalaVersion := "2.11.7",
    robovmVerbose := true //This will show debug output from RoboVM, enable if you like lot of text or are debugging
  )

  val roboVersion = RoboVMVersion // Taking the RoboVM version directly from the plugin

  /**
   * Example of simple native console application.
   * Can be run using "sbt native/native".
   * (First native is project specifier, second is native launch task in that project)
   *
   * TIP: Unless you are doing something which requires native compilation, you can compile and run the project as normal scala/java with "sbt native/run")
   *
   * NOTE: Example project uses standard input, which is not yet connected by RoboVM, so you cannot interact directly in sbt.
   *       As a workaround, launch compiled executable in Terminal (./native/target/robovm.tmp/NativeRobo) or launch non-native
   */
  lazy val native = NativeProject(id = "native", base = file("native"), settings = sharedSettings ++ Seq(
    connectInput := true, //So command to the program get through sbt (Does not work yet for native)
    robovmConfiguration := Right(
      <config>
        <executableName>NativeRobo</executableName>
        <mainClass>${{app.mainclass}}</mainClass>
      </config>
    )
  ))

  private val iosSettings = sharedSettings ++ Seq(
    //This adds dependency on robovm runtime and cocoatouch, which is iOS API for user interface
    libraryDependencies ++= Seq("robovm-rt", "robovm-cocoatouch").map("org.robovm" % _ % roboVersion)
  )

  /**
   * A minimal iOS project, just solid color.
   * Can be run using "sbt empty/ipadSim" or similar task.
   */
  lazy val empty = iOSProject("empty", file("empty"), settings = iosSettings)

  /**
   * Simple iOS project, showcasing some UI elements and configuration.
   * Can be run using "sbt hello/ipadSim" or similar task.
   */
  lazy val hello = iOSProject("hello", file("hello"), settings = iosSettings ++ Seq(
    robovmConfiguration := Right( //You can put here Left(file("pathTo/robovm.xml")) or Right(robovm.xml content), as shown here
      <config> <!-- See http://docs.robovm.com/configuration.html -->
        <executableName>HelloRobo</executableName>
        <mainClass>${{app.mainclass}}</mainClass> <!-- app.mainclass is defined in robovmProperties by default, but you can use anything you want. Note that you have to use double {{ and }}. -->
        <resources>
          <resource>
            {
            //You can use inline scala here
            val assetDirectory = "src/main/assets"

            <directory>{assetDirectory}</directory>
            /* Inline scala allows for very complex and flexible configuration.
            * Paths are relative to the project base directory. */
            }
          </resource>
        </resources>
        <!--
          You can add more settings
        -->
        <!--
        <iosInfoPList>plists/Info.plist</iosInfoPList>
        <iosResourceRulesPList>plists/ResourceRules.plist</iosResourceRulesPList>
        <iosEntitlementsPList>plists/Entitlements.plist</iosEntitlementsPList>
        -->
      </config>
    )
  ))

}
