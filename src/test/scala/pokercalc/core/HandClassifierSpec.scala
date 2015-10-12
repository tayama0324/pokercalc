package pokercalc.core

import org.scalatest.WordSpec

/**
 * Created by tayama on 15/10/11.
 */
class HandClassifierSpec extends WordSpec {

  "HandClassifier" should {
    val classifier = new HandClassifier

    def stopwatch[T](f: => T): T = {
      val begin = System.currentTimeMillis()
      val result = f
      val end = System.currentTimeMillis()
      println(s"${end - begin} ms")
      result
    }

    "work" in {
      // We know the number of combination that makes each hand (see wikipedia).
      // Verify all combinations of five cards.
      // This test is extremely slow, so you want to run it only when
      // you refactored HandClassifier.

      val result = stopwatch {
        Card.deck.combinations(5)
          .map(c => classifier.classify(c).precedence)
          .toSeq
          .groupBy(identity)
          .mapValues(_.size)
      }
      val expected = Map(
        0 -> 1302540,
        1 -> 1098240,
        2 -> 123552,
        3 -> 54912,
        4 -> 10200,
        5 -> 5108,
        6 -> 3744,
        7 -> 624,
        8 -> 36,
        9 -> 4
      )
      // assert(result === expected)
    }
  }
}
