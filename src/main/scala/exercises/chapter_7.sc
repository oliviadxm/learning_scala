import java.io.File

// 7 - 1(a)
def fib(count: Int): List[Int] = {
  val b = List(1, 1).toBuffer
  while (b.size < count) b += b.takeRight(2).sum
  b.toList
}
val fibonacci = fib(12)

// 7 - 1(b)
def fibAdd(l: List[Int], count: Int): List[Int] = {
  val b = l.toBuffer
  for (1 <- 1 to count) b += b.takeRight(2).sum
  b.toList
}
val more = fibAdd(List(1, 1, 2, 3), 10)

// 7 - 1(c)
def fibC(a: Int, b: Int): Stream[Int] = a #:: fibC(b, a + b)
val fibonacciC = fibC(1, 1).take(100).toList

// 7 - 1(d)
def fibD(a: Long, b: Long): Stream[Long] = a #:: fibD(b, a + b)
def nextFib(i: Int): Option[Long] = {
  val start = fibC(1, 1)
  val preceding = start.takeWhile(_ <= i)
  if (preceding.last == i) {
    Some(preceding.takeRight(2).sum)
  }
  else None
}
val x = nextFib(21)
val y = nextFib(22)

// 7 - 2
def listFiles(path: String): List[String] = {
  val files = new File(path).listFiles.toList
  files.map( _.toString.replaceFirst(",/","") )
}
val files = listFiles(".")
val files2 = listFiles(".").filterNot(_ startsWith ".")

// 7 - 3
val fileLookUp = files.groupBy(_.head.toLower).toList.sortBy(_._1)
for { (c, l) <- fileLookUp } {
   println(s"'$c' has ${l.size} files")
}

// 7 - 4
def toDouble(a: String) = util.Try(a.toDouble).toOption
val x1 = toDouble("a")

def product(a: String, b: String): Option[Double] = {
  (toDouble(a), toDouble(b)) match {
    case (Some(a1), Some(b1)) => Some(a1 * b1)
    case _ => None
  }
}

val x4 = product("yes", "20")
val x5 = product("99.3", "7")

// 7 - 5
def getProperty(s: String): Option[String] = {
  util.Try( System.getProperty(s) ) match {
    case util.Success(x) => Option(x)
    case util.Failure(ex) => None
  }
}
getProperty(null)
getProperty("os.arch")
