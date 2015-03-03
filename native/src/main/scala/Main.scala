import scala.io.StdIn

/**
 * Example Scala project implementing simple prefix calculator.
 * 
 * For more info about prefix notation see https://en.wikipedia.org/wiki/Polish_notation
 * 
 * 
 */
object Main {

  /**
   * Main class of Prefix Calculator
   * @param args are ignored
   */
  def main(args: Array[String]) {
    println("Prefix calculator waiting for commands, empty line to exit.")
    println(" (Try something like \"+ 2 * 8 8\", it is same as 2 + 8 * 8)")

    var line = StdIn.readLine()
    while(line != null && line.nonEmpty){
      var tokens = line.split("(\\ )+").toList

      var exceptionPlace = "start"
      def pop():Token = {
        if(tokens == Nil) throw new ParsingException(s"Not enough tokens after $exceptionPlace")
        val head = tokens.head
        exceptionPlace = head
        tokens = tokens.tail
        head match {
          case LiteralMatch(literal) => literal
          case UnaryOperatorMatch(unOperator) => unOperator
          case BinaryOperatorMatch(binOperator) => binOperator
          case unknown => throw new ParsingException(s"Unknown token $unknown")
        }
      }

      /**
       * Take expression and evaluate it, recursively calls itself to resolve children expressions.
       * @return value of expression on stack
       */
      def popResult():Double = {
        pop() match {
          case Literal(value) => value
          case UnaryOperator(operator) => operator(popResult())
          case BinaryOperator(operator) => operator(popResult(),popResult())
        }
      }

      try{
        while(tokens.nonEmpty){
          println("= "+popResult())
        }
      }catch {
        case ParsingException(message) =>
          println(message)
      }
      line = StdIn.readLine()
    }
    println("Bye!")
  }

  /**
   * Exception thrown when something cannot be parsed correctly
   */
  case class ParsingException(message:String) extends Exception

  /**
   * Base token class.
   * For example expression "floor + 1 * 2 2.4" (which can be rewritten as 1 + 2 * 2) contains these tokens:
   * Unary(floor) Binary(+) Literal(1) Binary(*) Literal(2) Literal(2.4)
   *
   * You can imagine tokens as function calls, Literal does not take any parameters, Unary takes one (the next expression)
   * and Binary takes two (next two expressions).
   *
   * If operators were functions, it would be rewritten as:
   * floor( +( 1, *(2,2.4) ) )
   */
  abstract class Token

  /**
   * Token that does not depend on any other tokens, directly contains some number
   */
  final case class Literal(literal:Double) extends Token

  object LiteralMatch {
    def unapply(token:String):Option[Literal] = {
      token match {
        case "pi" =>
          Some(new Literal(Math.PI))
        case "e" =>
          Some(new Literal(Math.E))
        case number =>
          try{
            Some(new Literal(number.toDouble))
          }catch{
            case nfe:NumberFormatException =>
              None
          }
      }
    }
  }

  /**
   * Token that transforms result of next expression
   */
  final case class UnaryOperator(function:Double => Double) extends Token

  object UnaryOperatorMatch {
    def unapply(token:String):Option[UnaryOperator] = {
      token match {
        case "floor" =>
          Some(new UnaryOperator(_.floor))
        case "round" =>
          Some(new UnaryOperator(_.round))
        case "ceil" =>
          Some(new UnaryOperator(_.ceil))
        case "sqrt" =>
          Some(new UnaryOperator(n => Math.sqrt(n)))
        case _ => None
      }
    }
  }

  /**
   * Token that transforms result of next two expressions
   */
  final case class BinaryOperator(function:(Double,Double) => Double) extends Token

  object BinaryOperatorMatch {
    def unapply(token:String):Option[BinaryOperator] = {
      token match {
        case "+" =>
          Some(new BinaryOperator(_ + _))
        case "-" =>
          Some(new BinaryOperator(_ - _))
        case "*" | "x" =>
          Some(new BinaryOperator(_ * _))
        case "/" =>
          Some(new BinaryOperator(_ / _))
        case "%" | "mod" =>
          Some(new BinaryOperator(_ % _))
        case "^" | "pow" =>
          Some(new BinaryOperator(_ + _))
        case _ => None
      }
    }
  }

}
