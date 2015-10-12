package pokercalc.core

/**
 * Created by tayama on 15/10/11.
 */
sealed abstract class HoleCard

object HoleCard {
  case class Fixed(a: Card, b: Card) extends HoleCard
  case class PocketPair(a: Rank) extends HoleCard
  case class OffSuited(a: Rank, b: Rank) extends HoleCard
  case class Suited(a: Rank, b: Rank) extends HoleCard
  case object Any extends HoleCard

  def fixed(a: Card, b: Card) = Fixed(a, b)
  def offSuited(a: Rank, b: Rank) = OffSuited(a, b)
  def suited(a: Rank, b: Rank) = Suited(a, b)

  /**
   * Recognize "AhTd", "KK", "K9o", "J2s", "any", etc.
   * Returns None on parse failure.
   */
  def parse(value: String): Option[HoleCard] = {
    value.split("").toSeq match {
      case _ if value.toLowerCase == "any" => Some(Any)
      case Seq(a, b) if a == b => parseRank(a).map(PocketPair)
      case Seq(a, b, c) if c == "o" =>
        for(s <- parseRank(a); t <- parseRank(b)) yield offSuited(s, t)
      case Seq(a, b, c) if c == "s" =>
        for(s <- parseRank(a); t <- parseRank(b)) yield suited(s, t)
      case Seq(a, b, c, d) =>
        for {
          s <- parseRank(a)
          t <- parseSuit(b)
          u <- parseRank(c)
          v <- parseSuit(d)
        } yield fixed(Card(t, s), Card(v, u))
      case _ => None
    }
  }

  private def parseSuit(value: String): Option[Suit] = Suit.values.find(_.name == value)
  private def parseRank(value: String): Option[Rank] = Rank.values.find(_.name == value)
}
