import org.robovm.apple.coregraphics._
import org.robovm.apple.foundation._
import org.robovm.apple.uikit.UIControl.OnTouchUpInsideListener
import org.robovm.apple.uikit._

class AppDelegate extends UIApplicationDelegateAdapter {
  var window: UIWindow = _

  override def didFinishLaunching(application: UIApplication) {
    val title = new UILabel(new CGRect(0, 0, 200, 100))
    var titleText = "Hello, Robo!"
    title.setText(titleText)

    window = new UIWindow(UIScreen.getMainScreen.getBounds)
    window.setBackgroundColor(UIColor.lightGray())

    window.addSubview(title)
    val bounds = title.getSuperview.getBounds
    title.setCenter(new CGPoint(bounds.origin.x + bounds.size.width / 2, bounds.origin.y + bounds.size.height / 2))

    val button = UIButton.create(UIButtonType.RoundedRect)
    button.setFrame(new CGRect(0, 0, 200, 100))
    button.setTitle("Click me!",UIControlState.Normal)
    button.addOnTouchUpInsideListener(new OnTouchUpInsideListener {
      override def onTouchUpInside(control: UIControl, event: UIEvent): Unit = {
        //This will not compile without proguard
        titleText = titleText.tail+titleText.head
        title.setText(titleText)
      }
    })
    window.addSubview(button)

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
