/**
  * Chapter 6 exercise
  */

// 6 - 1
// Create a list of the first 20 odd Long numbers. Can you create this with a for-loop,
// with the filter operation, and with the map operation? What's the most efficient
// and expressive way to write this?
for (i <- 0L to 9L; j = i * 2 + 1) yield j
0L to 20L filter (_ % 2 == 1)
0L to 9L map (_ * 2 + 1)

// 6 - 2
// Write a function titled "factors" that takes a number and returns a list of its factors,
// other than 1 and the number itself. For example, factors(15) should return List(3, 5)
//
// Then write a new function that applies "factors" to a list of numbers. Try using the
// list of long numbers you generated in exercise 1. For example, executing this function
// with List(9, 11, 13, 15) should return List(3, 3, 5), because the factor of 9 is 3 while
// the factors of 15 are 3 again and 5. Is this a good place to use map and flatten? Or
// would a for-loop be a better fit?
def factors(x: Int) = { 2 until x filter (x % _ == 0) }
factors(15)

def uniqueFactors(l: Seq[Int]) = l flatMap factors
uniqueFactors(List(9, 11, 13, 15))

// 6 - 3
// Write a function, first[A](items: List[A], count: Int): List[A], that returns the first
// x number of items in a given list. For example, first(List('a', 't', 'o'), 2) should
// return List('a', 't'). You could make this a one-liner by invoking one of the built-in
// list operations that already performs this task, or (preferably) implement your own
// solution. Can you do so with a for-loop? With foldLeft? With a recursive function that
// only accesses head and tail?
val chars = ('a' to 'f').toList
def first[A](items: List[A], count: Int): List[A] = items take count
first(chars, 3)

def first2[A](items: List[A], count: Int): List[A] = {
  items.foldLeft[List[A]](Nil) {
    (a: List[A], i: A) =>
      if (a.size >= count) a else i :: a
  }.reverse
}
first2(chars, 3)

def first3[A](items: List[A], count: Int): List[A] = {
  if (count > 0 && items.tail != Nil)
    items.head :: first(items.tail, count - 1)
  else
    Nil
}
first3(chars, 3)

// 6 - 4
// Write a function that takes a list of strings and returns the longest string in the list.
// Can you avoid using mutable variables here? This is an excellent candidate for the list
// folding operations we studied. Can you implement this with both fold and reduce? Would your
// function be more useful if it took a function parameter that compared two strings and
// returned the preferred one? How about if this function was applicable to generic lists,
// i.e., lists of any type?
def longest(l: List[String]): String = l.minBy(0 - _.size)
val fruits = List("Apple", "Orange", "Banana",  "Watermelon")
longest(fruits)

def longest1(l: List[String]): String = {
  l.fold("")((a, b) => if (a.length < b.length) b else a)
}
longest1(fruits)

def longest2(l: List[String]): String = {
  l.reduce((a, b) => if (a.length < b.length) b else a)
}

def largest[A](l: List[A], max: (A, A) => A): A = {
  l reduce((a, b) => max(a, b))
}
largest[String](fruits, (x, y) => if (x.length > y.length) x else y)

// 6 - 5
// Write a function that reverse a list. Can you write this as a recursive function? This may
// be a good place for a match expression.
def reverse[A](src: List[A], dest: List[A] = Nil): List[A] = {
  if (src == Nil)
    dest
  else
    reverse(src.tail, src.head :: dest)
}
val names = List("Terry", "Che", "Olivia", "Ty")
reverse(names)

// 6 - 6
// Write a function that takes a List[String] and returns a (List[String], List[String]),
// a tuple of string lists. The first list should be items in the original list that are
// palindromes (written the same forward and backward, like "racecar"). The second list in
// the tuple should be all of the remaining items from the original list. You can implement
// this easily with partition, but are there other operations you could use instead?
def splitList(l: List[String]) = l partition(s => s == s.reverse)
val palin = List("theaeht", "today", "yesterday", "racecar")
splitList(palin)

// 6 - 7
// The last exercise in this chapter is a multipart problem. We'll be reading and processing
// a forecast from the excellent and free OpenWeatherMap API.
//
// To read content from the URL, we will use the Scala library operation
// io.Source.+fromURL(url:String), which returns an +io.Source instance. Then we'll reduce
// the source to a collection of individual lines using the getLines.toList operation. Here
// is an example of using io.Source to read content from a URL, separate it into lines, and
// return the result as a list of strings:
val url = "http://samples.openweathermap.org/data/2.5/forecast?q=London,us&" +
  "mode=xml&appid=b6907d289e10d714a6e88b30761fae22"
val xmlList: List[String] = io.Source.fromURL(url).getLines.toList
println(xmlList.head)

// a. To make doubly sure we have the right content, print out the top 10 lines of the file.
// This should be a one-liner.
xmlList take 10

// b. The forecast's city's name is there in the first 10 lines. Grab it from the correct
// line and print out its XML element. Then extract the city name and country code from the
// XML elements and print them out together. This is a good place to use regular expressions
// to extract the text from XML tags.
//
// If you don't want to use regular expression capturing groups, you could instead use the
// replaceAll() operation on strings to remove the text surrounding the city name and the
// country name.
val k = xmlList map (_.trim)
def getValue(tag: String) = k filter (_ contains s"<$tag>") mkString "" replaceAll(".*>(\\w+)</.*","$1")
val city = getValue("name")
val country = getValue("country")

// c. How many forecast segments are there? What is the shortest expression you can write
// to count the segments?
val numOfSegments = xmlList.count(_ contains "</time>")

// d. The "symbol" XML element in each forecast segment includes a description of the weather
// forecast. Extract this element in the same way you extracted the city name and country
// code. Try iterating through the forecasts. printing out the description.
//
// Then create an informal weather report by printing out the weather descriptions over the
// next 12 hours.
def attributes(tag: String, attr: String) = {
  k.filter(_ contains s"<$tag")
    .filter(_ contains s"$attr=")
    .map{ s => s.replaceAll(s""".*$attr="([^"]+)".*""", "$1") }
}
val namesD = attributes("symbol", "name")

// e. Let's find out what description are used in this forecast. Print a sorted listing of
// all of these descriptions in the forecast, with duplicate entries removed.
val terms = attributes("symbol", "name").distinct.sorted

// f. These descriptions may be useful later. Included in the "symbol" XML element is an
// attribute containing the symbol number. Create a map from the symbol number to the
// description. Verify this is accurate by manually accessing symbol values from the forecast
// and checking that the description matches the XML document.
val symbolsToDescriptions = attributes("symbol", "number") zip attributes("symbol", "name")
val symMap = symbolsToDescriptions.distinct.map(t => t._1.toInt -> t._2).toMap
println("Light rain? Yup, " + symMap(804))

// g. What are the high and low temperatures over the next 24 hours?
val maxC = attributes("temperature", "max").map(_.toDouble).max
val minC = attributes("temperature", "min").map(_.toDouble).min

// h. What is the average temperature in this weather forecast? You can use the "value" attribute
// in the temperature element to calculate this value.
val temps = attributes("temperature", "value").map(_.toDouble)
val avgC = temps.sum / temps.size
