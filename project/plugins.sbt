resolvers += Resolver.url("scalasbt snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.hagerbot" % "sbt-robovm" % "1.0.0-alpha-04-SNAPSHOT")

addSbtPlugin("com.typesafe.sbt" % "sbt-proguard" % "0.2.2")
