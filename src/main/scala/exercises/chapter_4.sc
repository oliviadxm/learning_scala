/**
  * Chapter 4 exercise
  */

// 4 - 1
// Write a function that computes the area of a circle given its radius.
def area(i: Int): Double = { i * 2 * Math.PI }
area(3)

// 4 - 2
// Provide an alternate form of the function in exercise 1 that takes the
// radius as a String. What happens if your function is invoked with an
// empty String?
def area(s: String): Double = { s.toInt * 2 * Math.PI}
area("3")
//area("") // NumberFormatException

// 4 - 3
// Write a recursive function that prints the values from 5 to 50 by five,
// without using for or while loops. Can you make it tail-recursive?
@annotation.tailrec
def printFive(i: Int): Unit = {
  if (i == 50) {
    println(i)
  } else {
    println(i)
    printFive(i + 5)
  }
}
printFive(5)

// 4 - 4
// Write a function that takes a milliseconds value and returns a string
// describing the value in days, hours, minutes, and seconds. What's the
// optimal type for the input value?
def convert(s: String): Unit = {
  val l = s.toLong
  val seconds = (l / 1000).asInstanceOf[Int] % 60
  val minutes = ((l / (1000 * 60)) % 60).asInstanceOf[Int]
  val hours = ((l / (1000 * 60 * 60)) % 24).asInstanceOf[Int]
  val days = ((l / (1000 * 60 * 60 * 24)) % 365).asInstanceOf[Int]
  println("Days: " + days + " Hours: " + hours + " \nMinutes: " + minutes + " Second: " + seconds)
}
convert("1523508895622")

// 4 - 5
// Write a function that calculates the first value raised to the exponent
// of the second value. Try writing this first using math.pow, then with your
// own calculation. Did you implement it with variables? Is there a solution
// available that only uses immutable data? Did you choose a numeric type
// that is large enough for your uses?
def exponent(i: Double, j: Double): Double = {
  math.pow(i, j)
}
exponent(2, 3)

def exponent2(i: Double, j: Double): Double = {
  var res = i
  for (a <- 2 to j.toInt){
    res = i * res
  }
  res
}
exponent2(2, 3)

// 4 - 6
// Write a function that calculates the difference between a pair of 2D points
// (x and y) and returns the result as a point.
def difference(x: (Double, Double), y: (Double, Double)): (Double, Double) = {
  (x._1 - y._1, x._2 - y._2)
}
difference((5, 5), (0, 0))

// 4 - 7
// Write a function that takes a 2-sized tuple and returns it with the Int
// value (if included) in the first position.
def twoTuple(x: (Any, Any)): Any = {
  if (x._1.isInstanceOf[Int]) {
    (x._1, x._2)
  } else if (x._2.isInstanceOf[Int]) {
    (x._2, x._1)
  }
}
twoTuple("a", "b")
twoTuple(123, "b")
twoTuple("a", 123)

// 4 - 8
// Write a function that takes a 3-sized tuple and returns a 60sized tuple,
// with each original parameter followed by its String representation.
def threeTuple(x: (Any, Any, Any)): (Any, String, Any, String, Any, String) = {
  (x._1, x._1.toString, x._2, x._2.toString, x._3, x._3.toString)
}
threeTuple(true, 22.25, "yes")

