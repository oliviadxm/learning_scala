val t1: (Int, Char) = (1, 'a')
val t2: (Int, Char) = Tuple2[Int, Char](1, 'a')
val f1: Int=>Int = _ + 2
val f2: Int=>Int = new Function1[Int, Int] { def apply(x: Int) = x * 2 }

object ImplicitClasses {
  implicit class Hello(s: String) {
     def hello = s"Hello, $s"
  }
  def test = {
    println("World".hello)
  }
}

object ImplicitParams {
  def greet(name: String)(implicit greeting: String) = s"$greeting, $name"
  implicit val hi = "Hello"
  def test = {
    println( greet("Developers") )
  }
}

ImplicitParams.test

class Base { var i = 10 };
class Sub extends Base
def increment[B <: Base](b: Base) = { b.i += 1; b }

val l: List[Base] = List[Sub]()

val x: (Int, Int) = Tuple2(10, 20)
println("Does the arity = 2? " + (x.productArity == 2))

val hello1 = (n: String) => s"Hello, $n"
val h1 = hello1("Function Literals")
val hello2 = new ((String) => String) {
  def apply(n : String) = s"Hello, $n"
}
val h2 = hello2("Functional Instances")
println(s"hello1 = $hello1, hello2 = $hello2")

object Doubly {
  def print(num: Double)(implicit fmt: String) = {
    println(fmt format num)
  }
}
//Doubly.print(3.724)
Doubly.print(3.724)("%.1f")

case class USD(amount: Double) {
  implicit val printFmt = "%.2f"
  def print = Doubly.print(amount)
}
new USD(81.924).print

object TypeFun {
  type Whole = Int
  val x: Whole = 5

  type UserInfo = (Int, String)
  val u: UserInfo = new UserInfo(123, "George")

  type T3[A, B, C] = (A, B, C)
  val things = new T3(1, 'a', true)
}

val typeX = TypeFun.x
val typeU = TypeFun.u
val things = TypeFun.things

class BaseUser(val name: String)
class Admin(name: String, val level: String) extends BaseUser(name)
class Customer(name: String) extends BaseUser(name)
class PreferredCustomer(name: String) extends Customer(name)


abstract class Card {
  type UserType <: BaseUser
  def verify(u: UserType): Boolean
}

class SecurityCard extends Card {
  type UserType = Admin
  def verify(u: Admin) = true
}

val v1 = new SecurityCard().verify(new Admin("George", "high"))

class GiftCard extends Card {
  type UserType = Customer
  def verify(u: Customer) = true
}
val v2 = new GiftCard().verify(new Customer("Fred"))