import org.robovm.apple.coregraphics.CGRect
import org.robovm.apple.foundation.NSSet
import org.robovm.apple.uikit._

class MyViewController extends UIViewController {
  var label : UILabel = _ 
  var string = "Hello, RoboScala!"

  override def viewDidLoad() {
    label = new UILabel(new CGRect(20, 104, 280, 44))
    label.setFont(UIFont.getFont("Helvetica", 24))
    label.setTextColor(UIColor.white())
    label.setBaselineAdjustment(UIBaselineAdjustment.AlignCenters)
    label.setTextAlignment(NSTextAlignment.Center)
    label.setText(string)
    getView().addSubview(label)
  }
}
