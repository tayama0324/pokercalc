package pokercalc.core

/**
 * Cards, suits and ranks.
 */
case class Card(suit: Suit, rank: Rank) {
  override def toString(): String = {
    rank.name + suit.name
  }
}

object Card {
  val deck = {
    for (suit <- Suit.values; rank <- Rank.values) yield Card(suit, rank)
  }
}

sealed abstract class Suit(val name: String)

object Suit {
  case object Spade extends Suit("s")
  case object Diamond extends Suit("d")
  case object Heart extends Suit("h")
  case object Club extends Suit("c")

  val values = Seq(Spade, Diamond, Heart, Club)
}

sealed abstract class Rank(
  val precedence: Int,
  val name: String
)

object Rank {
  case object Ace extends Rank(14, "A")
  case object Deuce extends Rank(2, "2")
  case object Three extends Rank(3, "3")
  case object Four extends Rank(4, "4")
  case object Five extends Rank(5, "5")
  case object Six extends Rank(6, "6")
  case object Seven extends Rank(7, "7")
  case object Eight extends Rank(8, "8")
  case object Nine extends Rank(9, "9")
  case object Ten extends Rank(10, "T")
  case object Jack extends Rank(11, "J")
  case object Queen extends Rank(12, "Q")
  case object King extends Rank(13, "K")

  val values = Seq(Ace, Deuce, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King)

  implicit val ascendingOrder: Ordering[Rank] = Ordering.by[Rank, Int](_.precedence)
  val descendingOrder = ascendingOrder.reverse
}
