import java.io.FileOutputStream
import java.util.jar.{JarEntry, JarOutputStream, JarFile}

import com.google.common.io.ByteStreams
import com.google.common.io.Files
import sbt._
import Keys._

import sbtrobovm.RobovmPlugin._

object SampleBuild extends Build {
  lazy val native = makeSample("native", "NativeRobo")
  lazy val hello = makeSample("hello", "HelloRobo")
  lazy val empty = makeSample("empty", "EmptyRobo")
  lazy val problematic = makeSample("problematic", "Problematic")

  val roboVersion = "1.0.0-alpha-04"
  val roboDependencies = Seq("robovm-rt", "robovm-cocoatouch")
    .map("org.robovm" % _ % roboVersion)

  def makeSample(path: String, name: String, settings: Seq[Setting[_]] = Seq.empty): Project = {
    RobovmProject(path, file(path),
      settings = settings ++ Seq(
        scalaVersion := "2.11.2",
        executableName := name,
        robovmDebug := true,
        libraryDependencies ++= roboDependencies,
        unmanagedResources in Compile += sourceDirectory.value / "assets",
        alternativeInputJars := {
          classPathCacheDirectory.mkdirs()
          Some((fullClasspath in Compile).value.map(af => {
            val f = af.data
            if(f.isFile){
              val name = f.getName
              val cachedFile = classPathCacheDirectory / name
              if(classPathCacheDirectory.list().contains(name)){
                cachedFile
              }else{
                if(name.toLowerCase.endsWith(".jar")){
                  val inputFile = new JarFile(f)
                  val entries = inputFile.entries()
                  var containsOffenders = false
                  while(entries.hasMoreElements && !containsOffenders){
                    val element = entries.nextElement()
                    val elementName = element.getName
                    containsOffenders = !robovmOffenders.forall(offender => !elementName.startsWith(offender))
                  }
                  if(containsOffenders){
                    println("File "+name+" contains an offender(s)!")
                    val output = new JarOutputStream(new FileOutputStream(cachedFile))
                    val input = inputFile.entries()
                    while(input.hasMoreElements){
                      val next = input.nextElement()
                      val nextName = next.getName
                      if(robovmOffenders.forall(offender => !nextName.startsWith(offender))){
                        val je = new JarEntry(nextName)
                        output.putNextEntry(je)
                        output.write(ByteStreams.toByteArray(inputFile.getInputStream(next)))//Probably should reuse the array
                        output.closeEntry()
                      }else{
                        println("Offender '"+nextName+"' eliminated.")
                      }
                    }
                    output.flush()
                    output.close()
                    cachedFile
                  }else{
                    Files.copy(f,cachedFile) // So we don't have to check again
                    f
                  }
                }else{
                  //Weird unknown file
                  classPathCacheDirectory / name
                }
              }
            }else{
              //f is probably directory or something we don't do business with
              f
            }
          }))
        }
      )
    )
  }

  //Classpath files for workaround will be temporarily saved here
  lazy val classPathCacheDirectory = file("target") / "RoboVMClassPathCache"
  //Add all prefixes that cause problems to this set
  lazy val robovmOffenders = Set(
    "scala/concurrent/" //Things in this package seem to be broken, remove them all
  )
}
