/**
  * Chapter 3 exercise
  */

// 3 - 1
// Given a string name, write a match expression that will return the same string
// if nonempty, or else the string "n/a" if it is empty.
val r: String =  "String"
r.isEmpty match {
  case true => "n/a"
  case false => r
}

// 3 - 2
// Given a double amount, write an expression to return "greater" if it is more
// zero, "same" if it equals zero, and "less" if it is less than zero. Can you write
// this with if..else block? How about with match expressions?
var x: Double = 1.23
if (x > 0)
  "greater"
else if (x < 0)
  "less"
else
  "same"

x match {
  case x if x > 0 => "greater"
  case x if x < 0 => "less"
  case x => "same"
}

// 3 - 3
// Write an expression to convert one of the input values cyan, magenta, yellow to
// their six-char hexadecimal equivalents in string form. What can you do to handle
// error conditions?
val color = "yellow"
color match {
  case "yellow" => "#FFFF00"
  case "cyan" => "#00FFFF"
  case "magenta" => "#FF00FF"
  case x => println(s"Didn't expect $x!")
}

// 3 - 4
// Print the numbers 1 to 100, with each line containing a group of five numbers.
// For example:
// 1, 2, 3, 4, 5,
// 6, 7, 8, 9, 10,
// ....
for (x <- 1 to 100) {
  print(x + ", ")
  if (x % 5 ==0) {
    println()
  }
}

// 3 - 5
// Write an expression to print the numbers from 1 to 100, except that for multiples
// of 3, print "type". and for multiples of 5, print "safe". For multiples of both
// 3 and 5, print "typesafe".
for (i <- 1 to 100) {
  i match {
      case x if x % 15 == 0 => println("typesafe")
      case x if x % 5 == 0 => println("safe")
      case x if x % 3 == 0 => println("type")
      case x => println(x)
  }
}

// 3 - 6
// Can you rewrite the answer to exercise 5 to fit on one line? It probably won't
// be easier to read, but reducing code to its shortest form is an art, and a good
// exercise to learn the language.
for (i <- 1 to 100) { var s = ""; if (i % 3 == 0) s = "type"; if (i % 5 == 0) s += "safe"; if(s.isEmpty) s += i; println(s) }