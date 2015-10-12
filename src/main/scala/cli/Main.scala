package cli

import pokercalc.core.{HandClassifier, Card}

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Await, Future}

/**
 * Created by tayama on 15/10/11.
 */
object Main {

  implicit val ec = ExecutionContext.global

  def stopwatch[T](f: => T): T = {
    val begin = System.currentTimeMillis()
    val result = f
    val end = System.currentTimeMillis()
    println(s"${end - begin} ms")
    result
  }

  def allHands: Iterator[Seq[Card]] = {
    for {
      a <- Iterator.range(0, Card.deck.size)
      b <- Iterator.range(0, a)
      c <- Iterator.range(0, b)
      d <- Iterator.range(0, c)
      e <- Iterator.range(0, d)
    } yield Seq(a, b, c, d, e).map(Card.deck)
    // Card.deck.combinations(5).take(1000)
  }

  def main(args: Array[String]): Unit = {
    val classifier = new HandClassifier
    val result = stopwatch {
      Await.result(
        Future.sequence(
          allHands
            .grouped(1000)
            .map(hs => Future(hs.map(c => classifier.classify(c).precedence)))
        ),
        Duration.Inf
      ).toSeq.flatten.groupBy(identity).mapValues(_.size)
    }
    println(result)
  }
}
