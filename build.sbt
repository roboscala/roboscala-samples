
/**
 * Example of simple native console application.
 *
 * The settings are in `native/build.sbt`, to show that defining a project is not even necessary.
 *
 * Can be run using "sbt native/native".
 * (First native is project specifier, second is native launch task in that project)
 *
 * TIP: Unless you are doing something which requires native compilation, you can compile and run the project as normal scala/java with "sbt native/run")
 *
 * NOTE: Example project uses standard input, which is not yet connected by RoboVM, so you cannot interact directly in sbt.
 *       As a workaround, launch compiled executable in Terminal (./native/target/robovm.tmp/NativeRobo) or launch non-native
 */
lazy val native = Project(id = "native", base = file("native"))

/**
 * Base settings that is added to both iOS project examples.
 *
 * Note the inclusion of `iOSRoboVMSettings`, which adds all RoboVM related tasks and settings for iOS projects
 */
lazy val iosSettings = iOSRoboVMSettings ++ Seq(
  scalaVersion := "2.11.7",
  robovmVerbose := true, //This will show debug output from RoboVM, enable if you like lot of text or are debugging
  //This adds dependency on robovm runtime and cocoatouch, which is iOS API for user interface
  libraryDependencies ++= Seq("robovm-rt", "robovm-cocoatouch").map("org.robovm" % _ % RoboVMVersion)
)

/**
 * A minimal iOS project, just solid color.
 * Can be run using "sbt empty/ipadSim" or similar task.
 */
lazy val empty = Project("empty", file("empty"), settings = iosSettings)

/**
 * Simple iOS project, showcasing some UI elements and configuration.
 * Can be run using "sbt hello/ipadSim" or similar task.
 */
lazy val hello = Project("hello", file("hello"), settings = iosSettings ++ Seq(
  robovmConfiguration := Right( //You can put here Left(file("pathTo/robovm.xml")) or Right(robovm.xml content) (as shown below)
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

/**
 * A project much like the `hello` project, but this time using Interface Builder integration. (Licensed only)
 *
 * The setup is mostly the same, there are some notable exceptions:
 * Info.plist is specified explicitly so:
    <key>UIMainStoryboardFile</key>
    <string>HelloStoryboard</string>
 * can be added.
 * It could be of course done inline, like robovm.xml, but since it is rather big, it is left separate for clarity.
 * Keeping it separate also allows it to be modified from Xcode.
 */
lazy val ibHello = project in file("ibHello") settings iosSettings settings(
  robovmConfiguration := Right(
    <config>
      <executableName>HelloRoboIB</executableName>
      <mainClass>${{app.mainclass}}</mainClass>
      <resources>
        <resource>
          <directory>src/main/resources</directory>
        </resource>
      </resources>
      <iosInfoPList>Info.plist.xml</iosInfoPList><!-- .xml is only for the IDE to recognize it as xml -->
    </config>
  ))