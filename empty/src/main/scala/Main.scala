import org.robovm.cocoatouch.foundation._
import org.robovm.cocoatouch.uikit._

class AppDelegate extends UIApplicationDelegate.Adapter {
  lazy val window = new UIWindow(UIScreen.getMainScreen.getBounds)

  override def didFinishLaunching(application: UIApplication) {
    window.setBackgroundColor(UIColor.whiteColor)
    window.makeKeyAndVisible
  }  
}

object Main {
  def main(args: Array[String]) {
      val pool = new NSAutoreleasePool()
      UIApplication.main(args, null, classOf[AppDelegate])
      pool.drain()
  }
}
