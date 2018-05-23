import java.io.File

val m = {
  Map("APPL" -> 597, "MSFT" -> 48)
}

val n = {
  m - "APPL" + ("GOOG" -> 521)
}

println(m)

val nums = collection.mutable.Buffer(1)
for (i <- 2 to 10) nums += i
println(nums)

val nums1 = collection.mutable.Buffer[Int]()
for (i <- 1 to 10) nums1 += i
println(nums1)

val l = nums1.toList

val b = m.toBuffer
b trimStart(1)

b += ("GOOG" -> 521)
val a = b.toMap

val c = b.toList
val d = b.toSet

val e = Set.newBuilder[Char]
e += 'h'
e ++= List('e', 'l', 'l', 'o')
val helloSet = e.result

/**
  * Builder vs Buffer
  * Builder - building a mutable collection iteratively in order to convert to an
  * immutable collection.
  * Buffer - need operations while building mutable collections, or don't plan on
  * converting to an immutable collection
  */

// Arrays
val colors = Array("red", "green", "blue")
colors(0) = "purple"
colors
println("very purple: " + colors)

val files = new File(".").listFiles
val scala = files map(_.getName) filter(_ endsWith "scala")

// Seq and Sequences
val inks = Seq('C', 'M', 'Y', 'K')

val hi = "Hello, " ++ "worldly" take 12 replaceAll("w", "W")

// Streams
def inc(i: Int): Stream[Int] = Stream.cons(i, inc(i+1))
val s = inc(1)
val ls = s.take(5).toList
s

def inc2(head: Int): Stream[Int] = head #:: inc(head+1)
inc2(10).take(10).toList

def to(head: Char, end: Char): Stream[Char] = head > end match {
  case true => Stream.empty
  case false => head #:: to((head+1).toChar, end)
}
val hexChars = to('A', 'F').take(20).toList

// Monadic Collections
// Option Collections
var x: String = "Indeed"
var ao = Option(x)
x = null
var bo = Option(x)
println(s"aa is defined? ${ao.isDefined}")
println(s"bo is defined? ${bo.isDefined}")

def divide(amt: Double, divisor: Double): Option[Double] = {
  if (divisor == 0) None
  else Option(amt / divisor)
}
val legit = divide(5, 2)
val illegit = divide(3, 0)

val odds = List(1, 3, 5)
val firstOdd = odds.headOption
val evens = odds filter (_ % 2 == 0)
val firstEven = evens.headOption

val words = List("risible", "scavenger", "gist")
val uppercase = words find (w => w == w.toUpperCase)
val lowercase = words find (w => w == w.toLowerCase)
val filtered = lowercase filter (_ endsWith "ible") map (_.toUpperCase)
val exactSize = filtered filter (_.size > 15) map (_.size)

def nextOption = if (util.Random.nextInt > 0) Some(1) else None
val aaa = nextOption
val bbb = nextOption

// Try Collection
throw new Exception("No DB connection, exiting...")

def loopAndFail(end: Int, failAt: Int): Int = {
  for (i <- 1 to end) {
    println(s"${i}")
    if (i == failAt) throw new Exception("Too many iterations")
  }
  end
}
loopAndFail(10, 3)
val t1 = util.Try( loopAndFail(2, 3) )
val t2 = util.Try( loopAndFail(4, 2) )

def nextError = util.Try{ 1 / util.Random.nextInt(2) }
val xxx = nextError
val yyy = nextError

val input = " 123 "
val result = util.Try(input.toInt) orElse util.Try(input.trim.toInt)
result foreach {
  r => println(s"Parsed '$input' to $r!")
}

val xx = result match {
  case util.Success(x) => Some(x)
  case util.Failure(ex) => {
    println(s"Couldn't parse input '$input")
    None
  }
}

// Future Collection
import concurrent.ExecutionContext.Implicits.global
val f = concurrent.Future { println("hi") }
val ff = concurrent.Future { Thread.sleep(5000); println("hi")}
println("waiting")

import concurrent.Future
def nextFtr(i: Int = 0) = Future {
  def rand(x: Int) = util.Random.nextInt(x)
  Thread.sleep(rand(5000))
  if (rand(3) > 0) (i + 1) else throw new Exception
}

def cityTemp(name: String): Double = {
  val url = "http://api.openweathermap.org/data/2.5/weather"
  val cityUrl = s"$url?q=$name"
  val json = io.Source.fromURL(cityUrl).mkString.trim
  val pattern = """.*"temp":([\d.]+).*""".r
  val pattern(temp) = json
  temp.toDouble
}

val cityTemps = Future sequence Seq(
  Future(cityTemp("Fresno")), Future(cityTemp("Tempe"))
)
