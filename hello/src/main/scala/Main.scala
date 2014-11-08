import org.robovm.apple.foundation.NSAutoreleasePool
import org.robovm.apple.uikit._

class HelloWorld extends UIApplicationDelegateAdapter {
  var window: UIWindow = _

  override def didFinishLaunching(applicaiton:UIApplication, launchOptions:UIApplicationLaunchOptions) : Boolean = {
    val myViewController = new MyViewController();

    window = new UIWindow(UIScreen.getMainScreen().getBounds())
    window.setRootViewController(myViewController);
    window.makeKeyAndVisible()
    addStrongRef(window);

    return true;
  }
}

object Main {
  def main(args: Array[String]) {
    val pool = new NSAutoreleasePool()
    UIApplication.main(args, null, classOf[HelloWorld])
    pool.drain()
  }
}