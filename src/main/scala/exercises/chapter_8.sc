// 8 - 1
import java.util.Date

abstract class MediaFormat
class DvdMediaFormat extends MediaFormat
class BluRayMediaFormat extends MediaFormat
class USBMediaFormat extends MediaFormat
class CartridgeMediaFormat extends MediaFormat

abstract class VideoResolution(pixels: Int)
class HD extends VideoResolution(1280 * 720)
class FHD extends VideoResolution(1920 * 1080)
class QHD extends VideoResolution(2560 * 1440)
class UHD4K extends VideoResolution(3840 * 2160)

class Console(val make: String, val model: String, val debutDate: Date,
              val wifiType: Option[String], val physicalFormat: List[MediaFormat],
              val maxRes: VideoResolution) {
  override def toString = s"GameConsole($make, $model), max video resolution = " +
    s"${maxRes.getClass.getName}"
}

class ConsoleLibrary {
  def strToDate(s: String): Date = java.text.DateFormat.getInstance().parse(s)

  val chanduOne = new Console("Chandu", "One", strToDate("2/12/2007 0:00 AM"), Some("a/b"),
    List(new CartridgeMediaFormat), new HD)

  val aquaTopia = new Console("Aqua", "Topia", strToDate("5/2/2012 0:00 AM"), Some("a/b/g"),
    List(new DvdMediaFormat), new HD)

  val oonaSeven = new Console("Oona", "Seven", strToDate("3/3/2014 0:00 AM"), Some("b/g/n"),
    List(new BluRayMediaFormat, new DvdMediaFormat), new FHD)

  val leoChallenge = new Console("Leonardo", "Challenge", strToDate("12/12/2014 0:00 AM"), Some("g/n"),
    List(new USBMediaFormat), new UHD4K)
}

class Game(val name: String, val maker: String, val consoles: List[Console]) {
  def isSupported(console: Console) = consoles contains console
  override def toString = s"Game($name, by $maker)"
}

class GameShop {

  val consoles = new ConsoleLibrary()

  val games = List(
    new Game("Elevator Action", "Taito", List(consoles.chanduOne)),
    new Game("Mappy", "Namco", List(consoles.chanduOne, consoles.aquaTopia)),
    new Game("StreetFigher", "Capcom", List(consoles.oonaSeven, consoles.leoChallenge))
  )

  val consoleToGames: Map[Console, List[Game]] = {
    val consoleToGames1: List[(Console, Game)] = games flatMap (g => g.consoles.map(c => c -> g))
    val consoleToGames2: Map[Console, List[(Console, Game)]] = consoleToGames1 groupBy (_._1)
    val consoleToGames3: Map[Console, List[Game]] = consoleToGames2 mapValues(_ map (_._2))
    consoleToGames3
  }

  def reportGames(): Unit = {
    games sortBy (g => s"${g.maker}_${g.name}") foreach { game =>
      val consoleInfo = game.consoles.map(c => s"${c.make} ${c.model}").mkString(", ")
      println(s"${game.name} by ${game.maker} for $consoleInfo")
    }
  }
}

// 8 - 2
import scala.annotation.tailrec


/**
  * ListyHelper can create a linked list of items that have the parameterized type A
  */
class ListyHelper {
  def create[A](items: A*) = {
    var result: Listy[A] = new EmptyList[A]
    for (item <- items.reverse) {
      result = new NonEmptyList[A](item, result)
    }
    result
  }
}

abstract class Listy[A] {
  def foreach(f: A => Unit): Unit
  def apply(index: Int): Option[A]

  def headOption: Option[A] = apply(0)

  lazy val head: A = headOption.get

  def tail: Listy[A]

  def ::(a: A): Listy[A] = new NonEmptyList[A](a, this)

  def filter(f: A => Boolean): Listy[A] = {

    @tailrec
    def filterLists(src: Listy[A], dest: Listy[A], f: A => Boolean): Listy[A] = {
      src.headOption match {
        case Some(i) if f(i) => filterLists(src.tail, i :: dest, f)
        case Some(i) => filterLists(src.tail, dest, f)
        case None => dest
      }
    }

    val result: Listy[A] = filterLists(this, new EmptyList[A], f)
    result.reverse
  }


  lazy val size: Int = {

    @tailrec
    def count(src: Listy[A], total: Int): Int = {
      src.headOption match {
        case Some(i) => count(src.tail, total + 1)
        case None => total
      }
    }

    count(this, 0)
  }

  def map[B](f: A => B): Listy[B] = {

    @tailrec
    def mapLists[B](src: Listy[A], dest: Listy[B], f: A => B): Listy[B] = {
      src.headOption match {
        case Some(i) => mapLists(src.tail, f(i) :: dest, f)
        case None => dest
      }
    }

    val result: Listy[B] = mapLists(this, new EmptyList[B], f)
    result.reverse
  }

  lazy val reverse: Listy[A] = {

    @tailrec
    def reverseLists(src: Listy[A], dest: Listy[A]): Listy[A] = {
      src.headOption match {
        case Some(i) => reverseLists(src.tail, i :: dest)
        case None => dest
      }
    }

    reverseLists(this, new EmptyList[A])
  }
}

class NonEmptyList[A](val item: A, val tail: Listy[A]) extends Listy[A] {

  override def foreach(f: A => Unit): Unit = {
    f(item)
    tail.foreach(f)
  }

  override def apply(index: Int): Option[A] = {
    if (index < 1) Some(item) else tail.apply(index - 1)
  }
}

class EmptyList[A] extends Listy[A] {
  override def foreach(f: A => Unit): Unit = {}
  override def apply(index: Int): Option[A] = None
  override def tail: Listy[A] = null
}

// 8 - 3
import java.io.{FilenameFilter, File}


class DirLister(path: String, f: String => Boolean) {

  lazy val file: File = new File(path)

  lazy val filter = new FilenameFilter {
    override def accept(dir: File, name: String): Boolean = f(name)
  }

  def list: List[String] = file.list(filter).toList

}

// 8 - 4
import javax.sound.midi.MidiChannel

private[calliope] class Calliope(volume: Int) {

  private val duration = 250L
  private lazy val synth = javax.sound.midi.MidiSystem.getSynthesizer

  /**
    * Plays a series of MIDI notes at the specified volume for this calliope
    *
    * @param notes a sequence of MIDI notes as integers
    */
  def play(notes: Seq[Int]): Unit = {
    playChannel { channel =>
      for (note <- notes) {
        channel.noteOn(note, volume)
        Thread.sleep(duration)
        channel.noteOn(note, 0)
      }
    }
  }

  /**
    * Provides a mechanism to play music in a channel without
    * worrying about opening and closing synths.
    */
  private def playChannel(f: MidiChannel => Unit): Unit = {
    synth.open()
    val channel: MidiChannel = synth.getChannels.head
    f(channel)
    synth.close()
  }
}


class CalliopePlaying {

  val calliope = new Calliope(95)

  def playScale(): Unit = {
    calliope.play(Seq(60, 62, 64, 65, 67, 69, 71, 72))
  }

  def playMary(): Unit = {
    val (c, d, e) = (60, 62, 64)
    val mary = Seq(0, e, d, c, d, e, e, e, 0, d, d, d, 0, e, e, e, 0, e, d, c, d, e, e, e, e, d, d, e, d, c, 0)
    calliope.play(mary)
  }
}