package roboscalasamples.ibhello

import org.robovm.apple.uikit.{UILabel, UIStatusBarStyle, UIViewController}
import org.robovm.objc.annotation.{CustomClass, IBAction, IBOutlet}

@CustomClass("HelloViewController")
class HelloViewController extends UIViewController {

  /*
  NOTE:
  Due to how RoboVM (and Obj-C to some extent) handles initialization of controllers (etc.),
  initialization code in the body/constructor of the class won't get called. So do your initialization elsewhere.
  See: https://github.com/robovm/robovm/issues/894
   */
  private var label: UILabel = null
  private var clickCount: Int = 0

  @IBOutlet
  def setLabel(label: UILabel) {
    this.label = label
    label.setText("Hello from Scala!")
  }

  @IBAction
  def clicked() {
    clickCount += 1
    clickCount match {
      case 1 =>
        label.setText("Clicked once")
      case 42 =>
        label.setText("Clicked arbitrary number of times")
      case other =>
        label.setText(s"Clicked $other times")
    }
  }

  override def getPreferredStatusBarStyle: UIStatusBarStyle = UIStatusBarStyle.LightContent

  override def prefersStatusBarHidden(): Boolean = false
}
