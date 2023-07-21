/**
 * The BigTwoDeck class is a subclass of the Deck class and is used to model a
 * deck of cards used in a Big Two card game.
 * 
 * @author Wu Sen Pan
 */
public class BigTwoDeck extends Deck {
    /**
     * a method for initializing a deck of Big Two cards. It should remove all cards
     * from the deck, create 52 Big Two cards and add them to the deck.
     */
    public void initialize() {

        this.removeAllCards();
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 0; rank < 13; rank++) {
                BigTwoCard card = new BigTwoCard(suit, rank);
                this.addCard(card);
            }
        }
        this.shuffle();
    }
}
