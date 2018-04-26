/**
  * Chapter 2 exercise
  */

// 2 - 1
// Write a new Centigrade-to-Fahrenheit conversion (using the formula
// (x * 9/5) + 32), saving each step of the conversion into separate values.
// What do you expect the type of each value will be?
val x = 22
val y = x * 9
val t = y / 5.0
val z = t + 32

// 2 - 2
// Modify the Centigrade-to-Fahrenheit formula to return an integer instead
// of a floating-point number.
val t1 = y / 5
val z1 = t1 + 32

// 2 - 3
// Using the input value 2.7255, generate the string "You own $2.73." Is
// this doable with string interpolation?
val own = 2.7255
println(f"You own $$$own%.2f dollars.")

// 2 - 4
// Is there a simpler way to write the following?
// val flag: Boolean = false
// val result: Boolean = (flag == flag)
val flag = false
val result = !flag

// 2 - 5
// Convert the number 128 to a Char, a String, a Double, and then back to
// an Int. Do you expect the original amount to be retained? Do you need
// any special conversion functions for this?
var num = 128
var char = num.toChar
var string = char.toString
var double = string.toDouble
var ori = double.toInt

// 2 - 6
// Using the input string "Frank, 123 Main, 925-555-1943, 95122" and a regular
// expression matching, retrieve the telephone number. Can you convert each part
// of the telephone number to its own integer value? How would you store this
// in a tuple?
var input = "Frank,123 Main,925-555-1943,95122"
val pattern = """.*,(\d{3})-(\d{3})-(\d{4}),.*""".r
val pattern(p1, p2, p3) = input
(p1.toInt, p2.toInt, p3.toInt)
