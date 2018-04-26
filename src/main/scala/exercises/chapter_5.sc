/**
  * Chapter 5 exercise
  */

// 5 - 1
// Write a function literal that takes two integers and returns the higher number.
// Then write a higher-order function that takes a 3-sized tuple of integers plus
// this function literal, and uses it to return the maximum value in the tuple
val max = (x: Int, y: Int) => if (x > y) x else y
val higher = max(13, 15)

def max2(t: (Int, Int, Int), cmp: (Int, Int) => Int): Int = {
  cmp(t._1, cmp(t._2, t._3))
}
max2( (13, 15, 17), max )

// 5 - 2
// The library function util.Random.nextInt returns a random integer. Use it to invoke
// the "max" function with two random integers plus a function that returns the larger
// of two given integers. Do the same with a function that returns the smaller of two
// given integers, and then a function that returns the second integer every time.
def random =  util.Random.nextInt
max(random, random)

val t = (random, random, random)
max2(t, (x, y) => if (x < y) x else y)
max2(t, (x, y) => x)

// 5 - 3
// Write a higher-order function that takes an integer and returns a function. The
// returned function should take a single integer argument (say, "x") and return the
// product of x and the integer passed to the higher-order function.
def multiply(x: Int) = (y: Int) => x * y
val tripler = multiply(3)
tripler(10)

// 5 - 4
// Let's say that you happened to run across this function while reviewing another
// developer's code: def fzero[A](x: A)(f: A => Unit): A = { f(x); x }
// What does this function accomplish? Can you give an example of how you might invoke it?
def fzero[A](x: A)(f: A => Unit): A = { f(x); x }
def fzero(x: String, f: String => Unit): String = { f(x); x }
fzero("Hello", s => println(s.reverse))
fzero[Boolean](false) { b => println(s"b was $b") }

// 5 - 5
// There's a function named "square" that you would like to store in a function value.
// Is this the right way to do it? How else can you store a function in a value?
// def square(m: Double) = m * m
// val sq = square
def square(m: Double) = m * m
val sq1 = square _
sq1(4.0)
val sq: Double => Double = square
sq(5)

// 5 - 6
// Write a function called "conditional" that takes a value x and two functions, p and f,
// and returns a value of the same type as x. The p function is a predicate, taking the
// value x and returning a Boolean b. The f function also takes the value x and returns
// a new value of the same type. Your "conditional" function should only invoke the function
// f(x) if p(x) is true, and otherwise return x. How many type parameters will the "conditional"
// function require?
def conditional[A](x: A, p: A => Boolean, f: A => A): A = {
  if (p(x)) f(x) else x
}
val x = conditional[String]("Abcded", _.size > 4, _.reverse)

// 5 - 7
// Do you recall the "typesafe" challenge from the exercises in Chapter 3? There is a
// popular coding interview question I'll call "typesafe", in which the numbers 1-100
// must be printed one per line. The catch is that multiples of 3 must replace the number
// with the word "type", while multiples of 5 must replace the number with the word "safe"
// Of course, multiples of 15 must print "typesafe"
// Use the "conditional" function from exercise 6 to implement this challenge.
// Would your solution be shorter if the return type of the "conditional" did not match
// the type of the parameter x? Experiment with an altered version of the "conditional"
// function that works better with this challenge.
def conditional2[A](x: A, p: A => Boolean, f: A => String): String = {
  if (p(x)) f(x) else ""
}
def typesafe(i: Int): String = {
  val a1 = conditional2[Int](i, _ % 3 == 0, _ => "type")
  val a2 = conditional2[Int](i, _ % 5 == 0, _ => "safe")
  val a3 = conditional2[Int](i, _ % 3 > 0 && i % 5 > 0, x => s"$x")
  a1 + a2 + a3
}
val sequence = 1 to 100 map typesafe
println(sequence.mkString("\n"))