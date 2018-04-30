/**
  * Chapter 6 tutorials
  */

// Lists, Sets, and Maps
val numbers = List(32, 95, 24, 21, 17)
val colors = List("red", "green", "blue")
println(s"I have ${colors.size} colors: $colors")

colors.head
colors.tail
colors(1)
colors(2)

var total = 0
for (n <- numbers) { total += n; println(total) }

for(c <- colors) { println(c) }
// foreach() takes a function (a procedure, to be accurate) and invokes it with every item in the list.
colors.foreach( (c: String) => println(c) )
// map() takes a function that converts a single list element to another value and/or type.
val sizes = colors.map( (c: String) => c.size)
// reduce() takes a function that combines two list elements into a single elements.
total = numbers.reduce( (a: Int, b: Int) => a + b )

val unique = Set(10, 20, 30, 20, 20, 10)
val sum = unique.reduce( (a: Int, b: Int) => a + b)

val colorMap = Map("red" -> 0xFF0000, "green" -> 0xFF00, "blue" -> 0xFF)
val redRGB = colorMap("red")
val cyanRGB = colorMap("green") | colorMap("blue")
val hasWhite = colorMap.contains("white")
for (pairs <- colorMap) { println(pairs) }

val oddsAndEvens = List(List(1, 3, 5), List(2, 4, 6))
val keyValues = List(('A', 65), ('B', 66), ('C', 67))
val primes = List(2, 3, 5, 7, 11, 13)
val first = primes(0)
val forth = primes(3)
val remaining = primes.tail
var i = primes
while(! i.isEmpty) { print(i.head + ", "); i = i.tail}

def visit(i: List[Int]) {
  if (i.nonEmpty) {
    print(i.head + ", ")
    visit(i.tail)
  }
}

visit(primes)
i = primes
while(i != Nil) {
  print(i.head + ", ")
  i = i.tail
}

// The Cons Operator
val numbers2 = 1 :: 2 :: 3 :: Nil
val first2 = Nil.::(1)

val f = List(23, 8, 14, 21) filter (_ > 18)
val p = List(1, 2, 3, 4, 5) partition ( _ < 3)
val s = List("apple", "to") sortBy (_.length)
val appended = List(1, 2, 3, 4) :+ 5
val suffix = appended takeRight 3
val middle = suffix dropRight 2

// Mapping Lists
List(0, 1, 0) collect {case 1 => "ok"}
List("milk, tea") flatMap (_.split(','))
List("milk", "tea") map (_.toUpperCase)

// Reducing Lists
val validations = List(true, true, false, true, true, true)
val valid1 = !(validations contains false)
val valid2 = validations forall (_ == true)
val valid3 = !validations.contains(false)

def contains(x: Int, l: List[Int]): Boolean = {
  var a: Boolean = false
  for (i <- l) {
    if (!a) {
      a = i == x
    }
  }
  a
}
val included = contains(19, List(46, 19, 92))

def boolReduce(l: List[Int], start: Boolean)(f: (Boolean, Int) => Boolean): Boolean = {
  var a = start
  for (i <- l) {
    a = f(a, i)
  }
  a
}
val included2 = boolReduce(List(46, 29, 93), false) {
  (a, i) =>
    if (a) a else i == 19
}

def reduceOp[A, B](l: List[A], start: B)(f: (B, A) => B): B = {
  var a = start
  for (i <- l) a = f(a, i)
  a
}
val included3 = reduceOp(List(46, 19, 92), false) {
  (a, i) =>
    if (a) a else i == 19
}

val answer = reduceOp(List(11.3, 23.5, 7.2), 0.0)(_ + _)

// Pattern Matching with Collections
val statuses = List(500, 404)
val msg = statuses.head match {
  case x if x < 500 => "okay"
  case _ => "whoah, an error"
}