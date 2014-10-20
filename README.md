Scala RoboVM sample projects
============================

Examples of using Scala to create iOS apps and games with [sbt-robovm](https://github.com/ajhager/sbt-robovm) and [RoboVM](http://www.robovm.org/).

See [sbt-robovm](https://github.com/roboscala/sbt-robovm) for setup instructions.

*NOTE* Due to a bug in LLVM, most samples will fail to build for the simulator unless run through ProGuard first. Uncommenting the noted line in build.scala will fix the issue, but your builds will take _much_ longer. Testing on an actual device is recommended until the bug is fixed.

## Usage

You can run each demo on device or simulator:

```bash
$ sbt demo-name/device
$ sbt demo-name/iphone-sim
$ sbt demo-name/ipad-sim
```

Or for native applications:

```bash
$ sbt demo-name/native
```

## Demos

* empty: provides just an application delegate and a window
* hello: setting up simple view
* native: example of compiling native desktop programs
