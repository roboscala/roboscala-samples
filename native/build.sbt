//Needed to denote that this is a native project
//Adds configuration (settings+tasks) which this project needs
nativeRoboVMSettings

name := "NativeRobo"

scalaVersion := "2.11.7"

connectInput := true //So command to the program get through sbt (Doesn't work properly yet)

robovmConfiguration := Right(
  <config>
    <executableName>NativeRobo</executableName>
    <mainClass>${{app.mainclass}}</mainClass>
    <treeShaker>aggressive</treeShaker>
  </config>
)