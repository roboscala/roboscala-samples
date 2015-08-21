Scala RoboVM sample projects
============================

Examples of using Scala to create iOS apps and games with [RoboVM](http://www.robovm.org/) using [sbt-robovm](https://github.com/roboscala/sbt-robovm).

See [sbt-robovm](https://github.com/roboscala/sbt-robovm) for plugin manual and environment setup instructions.

## Quick Start

1. Make sure that your environment is [ready](https://github.com/roboscala/sbt-robovm) and you are familiar with [sbt](http://www.scala-sbt.org/0.13/tutorial/index.html)
1. Download or clone this repository
1. This repository is a sbt plugin, so you can start running the tasks.

There are tree projects set up:

* empty: iOS project that provides just an application delegate and a window
* hello: sets up simple iOS UI and shows how to use assets
* native: example of how to compile Scala into a native console application for OSX

sbt-robovm defines a few tasks, here are the important ones:

* `iphoneSim` run the project as iOS application on iPhone simulator
* `ipadSim` same, but on iPad simulator
* `device` compile, upload and run the app on the connected device
* `native` compile and run the app as a console application - only available in console projects (such as `native`)

For example:
```bash
$ sbt hello/device
$ sbt empty/iphoneSim
$ sbt hello/ipadSim
$ sbt native/native
```

_**Note:** `native` is an example of interactive console app and thus requires user input. However, connecting the sbt
console input to the app launched using the `native` task is not yet implemented, so the app will quit immediately after
starting. To workaround this, launch the compiled app in Terminal manually: `$ ./native/target/robovm/NativeRobo`_