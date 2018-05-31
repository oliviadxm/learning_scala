class User(val name: String) {
  def greet: String = s"Hello from $name"
  override def toString = s"User($name)"
}
val u = new User("Zeniba")
println( u.greet )
val isAnyRef = u.isInstanceOf[AnyRef]

val users = List(new User("Shoto"),
  new User("Art3mis"),
  new User("Aesch"))

val sizes = users map (_.name.length)
val sorted = users sortBy( _.name )
val third = users find (_.name contains "3")
val greet = third map (_.greet) getOrElse "hi"

class A {
  def hi = "Hello from A"
  override def toString = getClass.getName
}

class B extends A
class C extends B {
  override def hi = "hi C -> " + super.hi
}
val hiA = new A().hi
val hiB = new B().hi
val hiC = new C().hi

val misc = List(new C, new A, new B)
val messages = misc.map(_.hi).distinct.sorted

// Defining classes
class Car(val make: String, var reserved: Boolean = true, val year: Int = 2015) {
  def reserve(r: Boolean): Unit = { reserved = r }
}
val t = new Car("Toyota", false)
t.reserve(true)
println(s"My ${t.make} is not reserved? ${t.reserved}")
class Lotus(val color: String, reserved: Boolean) extends Car("Lotus", reserved)
val l = new Lotus("Silver", false)
println(s"Requested a ${l.color} ${l.make}")
val a = new Car("Acura")
val ll = new Car("Lexus", year = 2010)
val p = new Car(reserved = false, make = "Porsche")

class Singular[A](element: A) extends Traversable[A] {
  def foreach[B](f: A => B) = f(element)
}
val pa = new Singular("Planes")
pa foreach println
val name: String = pa.head

// Abstract Classes
abstract class Car1{
  val year: Int
  val automatic: Boolean = true
  def color : String
}
class RedMini(val year: Int) extends Car1 {
  def color = "Red"
}
val mini: Car1 = new RedMini(2005)

class Mini(val year: Int, val color: String) extends Car1 {}
val redMini: Car1 = new Mini(2005, "red")
println(s"Got a ${redMini.color} Mini")

// Anonymous Classes
abstract class Listener {
  def trigger
}
val myListener = new Listener {
  def trigger: Unit = {
    println(s"Trigger at ${new java.util.Date}")
  }
}
myListener.trigger
class Listening {
  var listener: Listener = null
  def register(l: Listener): Unit = {
    listener = l
  }
  def sendNotification() { listener.trigger }
}
val notification = new Listening()
notification.register(new Listener {
  def trigger { println(s"Trigger at ${new java.util.Date()}")}
})
notification.sendNotification()

// More Field and Method Types
class Printer(msg: String) {
  def print(s: String): Unit = println(s"$msg: $s")
  def print(l: Seq[String]): Unit = print(l.mkString(", "))
}

new Printer("Today's Report").print("Foggy" :: "Rainy" :: "Hot" :: Nil)

class Multiplier(factor: Int) {
  def apply(input: Int) = input * factor
}
val tripleMe = new Multiplier(3)
val tripled = tripleMe.apply(10)
val tripled2 = tripleMe(10)

val list = List('a', 'b', 'c')
val character = list(1)

class RandomPoint {
  val x = { println("Creating x"); util.Random.nextInt}
  lazy val y = { println("now y"); util.Random.nextInt}
}
val p1 = new RandomPoint
println(s"Location is ${p1.x}, ${p1.y}")
