package pokercalc.core

/**
 * Created by tayama on 15/10/11.
 */
sealed abstract class Hand(
  val precedence: Int,
  val tieBreakers: Seq[Rank]
)

object Hand {
  case class HighCard(override val tieBreakers: Seq[Rank]) extends Hand(0, tieBreakers)
  case class OnePair(override val tieBreakers: Seq[Rank]) extends Hand(1, tieBreakers)
  case class TwoPairs(override val tieBreakers: Seq[Rank]) extends Hand(2, tieBreakers)
  case class ThreeOfAKind(override val tieBreakers: Seq[Rank]) extends Hand(3, tieBreakers)
  case class Straight(override val tieBreakers: Seq[Rank]) extends Hand(4, tieBreakers)
  case class Flush(override val tieBreakers: Seq[Rank]) extends Hand(5, tieBreakers)
  case class FullHouse(override val tieBreakers: Seq[Rank]) extends Hand(6, tieBreakers)
  case class FourOfAKind(override val tieBreakers: Seq[Rank]) extends Hand(7, tieBreakers)
  case class StraightFlush(override val tieBreakers: Seq[Rank]) extends Hand(8, tieBreakers)
  case class RoyalFlush(override val tieBreakers: Seq[Rank]) extends Hand(9, tieBreakers)

  implicit val ascendingOrder: Ordering[Hand] =
    Ordering.by[Hand, (Int, Seq[Rank])](
      h => (h.precedence, h.tieBreakers)
    )(Ordering.Tuple2(Ordering.Int, Ordering.Implicits.seqDerivedOrdering(Rank.ascendingOrder)))
}


