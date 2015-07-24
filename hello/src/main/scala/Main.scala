import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit._

class HelloWorld extends UIApplicationDelegateAdapter {
  lazy val window = new UIWindow(UIScreen.getMainScreen.getBounds)

  override def didFinishLaunching(applicaiton: UIApplication, launchOptions: UIApplicationLaunchOptions): Boolean = {
    window.setRootViewController(new MyViewController())
    window.makeKeyAndVisible()
    addStrongRef(window)

    true
  }
}

object Main extends App {
  val pool = new NSAutoreleasePool()
  UIApplication.main(args, null, classOf[HelloWorld])
  pool.drain()
}