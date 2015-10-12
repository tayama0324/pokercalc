package pokercalc.core

import pokercalc.core.Hand._
import pokercalc.core.Rank._

/**
 * Created by tayama on 15/10/11.
 */
class HandClassifier {

  private def descendingRank(cards: Card*): Seq[Rank] = {
    cards.map(_.rank).sorted(Rank.descendingOrder)
  }

  /**
   * Detects quads, full house, three-of-a-kinds, two pairs, one pair and high card.
   */
  private def checkPairHand(cards: Seq[Card]): Hand = {
    val groups = cards.groupBy(_.rank).toSeq
      .sortBy(_._1)(Rank.descendingOrder)
      .map(_._2)
      .sortBy(_.size)(Ordering.Int.reverse)
    val ranks = groups.flatten.map(_.rank).distinct
    groups.map(_.size) match {
      case Seq(4, 1) => FourOfAKind(ranks)
      case Seq(3, 2) => FullHouse(ranks)
      case Seq(3, 1, 1) => ThreeOfAKind(ranks)
      case Seq(2, 2, 1) => TwoPairs(ranks)
      case Seq(2, 1, 1, 1) => OnePair(ranks)
      case _ => HighCard(ranks)
    }
  }

  private def checkStraight(cards: Seq[Card]): Option[Straight] = {
    val ranks = descendingRank(cards:_*)
    ranks.map(_.precedence) match {
      case Seq(a, b, c, d, e) if a - 1 == b && b - 1 == c && c - 1 == d && d - 1 == e =>
        Some(Straight(ranks.headOption.toSeq))
      case _ => ranks match {
        case Seq(Ace, Five, Four, Three, Deuce) => Some(Straight(Seq(Five)))
        case _ => None
      }
    }
  }

  private def checkFlush(cards: Seq[Card]): Option[Flush] = {
    if (cards.map(_.suit).distinct.size == 1) {
      Some(Flush(descendingRank(cards:_*)))
    } else {
      None
    }
  }

  /**
   * Detects royal flush, straight flush, flush and straight.
   */
  private def checkNonPairHand(cards: Seq[Card]): Option[Hand] = {
    (checkStraight(cards), checkFlush(cards)) match {
      case (Some(Straight(Seq(Ace))), Some(Flush(_))) => Some(RoyalFlush(Seq(Ace)))
      case (Some(Straight(ranks)), Some(Flush(_))) => Some(StraightFlush(ranks))
      case (sOpt, fOpt) => sOpt orElse fOpt
    }
  }

  /**
   * Returns poker hand which given five cards comprises.
   */
  def classify(a: Card, b: Card, c: Card, d: Card, e: Card): Hand = {
    classify(Seq(a, b, c, d, e))
  }

  /**
   * Returns poker hand which given five cards comprises.
   * @param cards List of cards. Must contain exactly five elements.
   * @return Hand
   * @throws IllegalArgumentException if length of cards is not five
   */
  def classify(cards: Seq[Card]): Hand = {
    require(cards.size == 5, "Hand must contain exactly five cards.")
    Seq(checkNonPairHand(cards).toSeq, Seq(checkPairHand(cards))).flatten.max
  }
}
