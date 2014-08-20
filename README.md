Scala iOS Demos
===============

Examples of using Scala to create iOS apps and games with [sbt-robovm](https://github.com/ajhager/sbt-robovm) and [RoboVM](http://www.robovm.org/).

## Prerequisities

 * [RoboVM 0.0.14]()
 * Current snapshot of [sbt-robovm plugin](https://github.com/roboscala/sbt-robovm) should be installed in your local ivy repository:
```
$ git clone git://github.com/ajhager/sbt-robovm.git
$ cd sbt-robovm
$ sbt publish-local
```

## Run

You can run each demo on device or simulator:

    $ sbt demo-name/device
    $ sbt demo-name/iphone-sim
    $ sbt demo-name/ipad-sim

Or for native applications:
    
    $ sbt demo-name/native

## Demos

 * empty: provides just an application delegate and a window
 * hello: setting up simple view
 * native: example of compiling native desktop programs
