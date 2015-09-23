package roboscalasamples.ibhello

import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit.{UIApplication, UIApplicationDelegateAdapter, UIApplicationLaunchOptions}

object Main extends App {
  //Nothing unusual here
  val pool = new NSAutoreleasePool()
  UIApplication.main(args, null, classOf[Main])
  pool.drain()
}

class Main extends UIApplicationDelegateAdapter {
  override def didFinishLaunching(application: UIApplication, launchOptions: UIApplicationLaunchOptions): Boolean = {
    //We don't have to setup anything here, UI is initialized automatically from iOS

    //You can still do your own initialization here

    /*
    There are a few tips for when creating a new app with storyboard from scratch:
    1. Don't forget to register it as a main storyboard file in Info.plist (if it is main, it probably is)
    2. Don't forget to mark the first View Controller as entry point
      (Xcode/Interface Builder -> Attributes Inspector -> Is Initial View Controller)
     */

    true
  }
}

