import org.robovm.cocoatouch.coregraphics._
import org.robovm.cocoatouch.foundation._
import org.robovm.cocoatouch.uikit._

class Main extends UIApplicationDelegate.Adapter {
  var window: UIWindow = _
  var alert: UIAlertView = _

  override def didFinishLaunching(application: UIApplication) {
    alert = new UIAlertView()
    alert.setTitle("Hello, Human!")
    alert.setMessage("Would you like to play a game?")
    alert.addButton("Yes")

    val button = UIButton.fromType(UIButtonType.RoundedRect)
    button.setTitle("Hello, Robo!", UIControlState.Normal)
    button.setFrame(new CGRect(0, 0, 200, 100))

    button.addOnTouchUpInsideListener(new UIControl.OnTouchUpInsideListener {
      override def onTouchUpInside(c: UIControl, e: UIEvent) {
        alert.show
      }
    })

    window = new UIWindow(UIScreen.getMainScreen().getBounds())
    window.setBackgroundColor(UIColor.lightGrayColor())

    window.addSubview(button)
    val bounds = button.getSuperview().getBounds()
    button.setCenter(new CGPoint(bounds.origin.x + bounds.size.width / 2, bounds.origin.y + bounds.size.height / 2))

    window.makeKeyAndVisible()
  }  
}

object Main {
  def main(args: Array[String]) {
      val pool = new NSAutoreleasePool()
      UIApplication.main(args, null, classOf[Main])
      pool.drain()
  }
}

