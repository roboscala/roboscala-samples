import org.robovm.cocoatouch.coregraphics._
import org.robovm.cocoatouch.foundation._
import org.robovm.cocoatouch.uikit._

class Main extends UIApplicationDelegate.Adapter {
  var window: UIWindow = _
  var clicks = 0

  override def didFinishLaunching(application: UIApplication, launchOptions: NSDictionary[_ <: NSObject, _ <: NSObject]) = {
    val button = UIButton.fromType(UIButtonType.RoundedRect)
    button.setFrame(new CGRect(115.0f, 121.0f, 91.0f, 37.0f))
    button.setTitle("Click me!", UIControlState.Normal)

    button.addOnTouchUpInsideListener(new UIControl.OnTouchUpInsideListener {
      override def onTouchUpInside(c: UIControl, e: UIEvent) {
        clicks += 1
        button.setTitle(s"Click #$clicks", UIControlState.Normal)
      }
    })

    window = new UIWindow(UIScreen.getMainScreen().getBounds())
    window.setBackgroundColor(UIColor.lightGrayColor())
    window.addSubview(button)
    window.makeKeyAndVisible()

    true
  }  
}

object Main {
  def main(args: Array[String]) {
      val pool = new NSAutoreleasePool()
      UIApplication.main(args, null, classOf[Main])
      pool.drain()
  }
}

