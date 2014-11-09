import org.robovm.apple.foundation._
import org.robovm.apple.uikit._

class AppDelegate extends UIApplicationDelegateAdapter {
  lazy val window = new UIWindow(UIScreen.getMainScreen.getBounds)

  override def didFinishLaunching(application: UIApplication) {
    window.setBackgroundColor(UIColor.white())
    window.makeKeyAndVisible()
  }  
}

object Main {
  def main(args: Array[String]) {
      val pool = new NSAutoreleasePool()
      UIApplication.main(args, null, classOf[AppDelegate])
      pool.drain()
  }
}
