Scala RoboVM sample projects
============================

Examples of using Scala to create iOS apps and games with [sbt-robovm](https://github.com/ajhager/sbt-robovm) and [RoboVM](http://www.robovm.org/).

See [sbt-robovm](https://github.com/roboscala/sbt-robovm) for setup instructions.

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
