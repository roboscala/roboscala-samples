import org.robovm.apple.coregraphics.CGRect
import org.robovm.apple.foundation.NSSet
import org.robovm.apple.uikit._

class MyViewController extends UIViewController {
  lazy val label = new UILabel(new CGRect(20, 104, 280, 44))
  lazy val textField = new UITextField(new CGRect(44, 32, 232, 31))
  var string = "World"

  override def viewDidLoad() {
    val background = new UIImageView(UIImage.create("assets/Background.png"))
    background.setFrame(new CGRect(0, 0, 320, 480))
    background.setContentMode(UIViewContentMode.Center)
    background.setUserInteractionEnabled(false)
    getView.addSubview(background)

    textField.setContentVerticalAlignment(UIControlContentVerticalAlignment.Center)
    textField.setBorderStyle(UITextBorderStyle.RoundedRect)
    textField.setFont(UIFont.getFont("Helvetica", 17))
    textField.setClearsOnBeginEditing(true)
    textField.setAdjustsFontSizeToFitWidth(true)
    textField.setMinimumFontSize(17)
    textField.setAutocapitalizationType(UITextAutocapitalizationType.Words)
    textField.setKeyboardType(UIKeyboardType.ASCIICapable)
    textField.setReturnKeyType(UIReturnKeyType.Done)
    textField.setDelegate(new UITextFieldDelegateAdapter() {
      override def shouldReturn (theTextField:UITextField) : Boolean = {
        if (theTextField == textField) {
          textField.resignFirstResponder()
          string = textField.getText
          label.setText(getText)
        }
        true
      }
    })
    getView.addSubview(textField)

    label.setFont(UIFont.getFont("Helvetica", 24))
    label.setTextColor(UIColor.white())
    label.setBaselineAdjustment(UIBaselineAdjustment.AlignCenters)
    label.setTextAlignment(NSTextAlignment.Center)
    label.setText(getText)
    getView.addSubview(label)
  }

  override def touchesBegan (touches:NSSet[UITouch], event:UIEvent) {
    textField.resignFirstResponder()
    textField.setText(string)
    super.touchesBegan(touches, event)
  }

  def getText: String = {
    "Hello, " + string + "!"
  }
}
