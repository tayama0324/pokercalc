package pokercalc.core

import org.scalatest.WordSpec

/**
 * Created by tayama on 15/10/11.
 */
class CardSpec extends WordSpec {

  "Deck" should {
    "have 52 cards" in {
      assert(Card.deck.size === 52)
    }

    "be identical" in {
      assert(Card.deck === Card.deck.distinct)
    }
  }
}
