import org.robovm.cocoatouch.coregraphics._
import org.robovm.cocoatouch.foundation._
import org.robovm.cocoatouch.uikit._

class AppDelegate extends UIApplicationDelegate.Adapter {
  var window: UIWindow = _

  override def didFinishLaunching(application: UIApplication) {
    val title = new UILabel(new CGRect(0, 0, 200, 100))
    title.setText("Hello, Robo!")

    window = new UIWindow(UIScreen.getMainScreen().getBounds())
    window.setBackgroundColor(UIColor.lightGrayColor())

    window.addSubview(title)
    val bounds = title.getSuperview().getBounds()
    title.setCenter(new CGPoint(bounds.origin.x + bounds.size.width / 2, bounds.origin.y + bounds.size.height / 2))

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
