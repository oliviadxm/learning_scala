/**
  * Chapter 5 tutorial
  */

// Function Types and Values
def double(x: Int): Int = x * 2
double(5)

val myDouble: (Int) => Int = double
myDouble(5)

val myDoubleCopy = myDouble
myDoubleCopy(5)

val myDouble2 = double _
val amount = myDouble2(20)

def max(a: Int, b: Int) = if (a > b) a else b
val maximize: (Int, Int) => Int = max // assigned to a function value
maximize(50, 30)

def logStart() = "=" * 50 + "\nStarting NOW\n" + "=" * 50
val start: () => String = logStart

println( start() )

// Higher-Order Functions
def safeStringOp(s: String, f: String => String) = {
  if (s != null) f(s) else s
}
def reverser(s: String) = s.reverse

safeStringOp(null, reverser)
safeStringOp("Ready", reverser)

// Function Literals
val doubler = (x: Int) => x * 2
val doubled = doubler(22)

val greeter = (name: String) => s"Hello, $name"
val hi = greeter("World")

val maximize2 = (a: Int, b: Int) => if (a > b) a else b //redefined with a functional literal

val start2 = () => "=" * 50 + "\nStarting NOW\n" + "=" * 50
println( start2() )

safeStringOp(null, (s: String) => s.reverse)
safeStringOp("Ready", (s: String) => s.reverse)

safeStringOp(null, s => s.reverse)
safeStringOp("Ready", s => s.reverse)

// Placeholder Syntax
// Can be used when
// (a) the explicit type of the function is specified outside the literal
// (b) the parameters are used no more than once
val doubler2: Int => Int = _ * 2
safeStringOp(null, _.reverse)
safeStringOp("Ready", _.reverse)

def combination(x: Int, y: Int, f: (Int, Int) => Int) = f(x, y)
combination(23, 12, _ * _)

def tripleOp(a: Int, b: Int, c: Int, f: (Int, Int, Int) => Int) = f(a, b, c)
tripleOp(23, 92, 14, _ * _ * _)

def tripleOp2[A, B](a: A, b: A, c: A, f: (A, A, A) => B) = f(a, b, c)
tripleOp2[Int, Int](23, 92, 14, _ * _ * _)
tripleOp2[Int, Double](23, 92, 14, 1.0 * _ / _ / _)
tripleOp2[Int, Boolean](93, 92, 14, _ > _ + _)

// Partially Applied Functions and Currying
def factorOf(x: Int, y: Int) = y % x == 0
val f = factorOf _
val x = f(7, 20)

val multipleOf3 = factorOf(3, _:Int)
val y = multipleOf3(78)

def factorOf2(x: Int)(y: Int) = y % x == 0
val isEven = factorOf2(2) _
val z = isEven(32)

// By-Name Parameters
def doubles(x: => Int) = {
  println("Now doubling " + x)
  x * 2
}
doubles(5)

def f(i: Int) = { println(s"Hello from f($i）"); i }
doubles( f(8) )

// Partial Functions
val statusHandler: Int => String = {
  case 200 => "Okay"
  case 400 => "Your Error"
  case 500 => "Our error"
}

statusHandler(200)
statusHandler(400)
statusHandler(401)

val uuid = java.util.UUID.randomUUID().toString
val timedUUID = safeStringOp(uuid, { s =>
  val now = System.currentTimeMillis
  val timed = s.take(24) + now
  timed.toUpperCase
})

def timer[A](f: => A): A = {
  def now = System.currentTimeMillis
  val start = now; val a = f; val end = now
  println(s"Executed in ${end - start} ms")
  a
}

val veryRandomAmount = timer {
  util.Random.setSeed(System.currentTimeMillis)
  for (i <- 1 to 100000) util.Random.nextDouble
  util.Random.nextDouble
}


