/**
 * The BigTwoCard class is a subclass of the Card class and is used to model a
 * card used in a Big Two card game.
 * 
 * @author Wu Sen Pan
 */
public class BigTwoCard extends Card {
    /**
     * a constructor for building a card with the specified suit and rank. suit is
     * an integer between 0 and 3, and rank is an integer between 0 and 12.
     * 
     * @param suit an int value between 0 and 3 representing the suit of a card:
     *             <p>
     *             0 = Diamond, 1 = Club, 2 = Heart, 3 = Spade
     * @param rank an int value between 0 and 12 representing the rank of a card:
     *             <p>
     *             0 = 'A', 1 = '2', 2 = '3', ..., 8 = '9', 9 = '0', 10 = 'J', 11 =
     *             'Q', 12 = 'K'
     */
    public BigTwoCard(int suit, int rank) {
        super(suit, rank);
    }

    /**
     * Compares this card with the specified card for order.
     * 
     * @param card the card to be compared
     * @return a negative integer, zero, or a positive integer as this card is less
     *         than, equal to, or greater than the specified card
     */
    public int compareTo(Card card) {
        if(this.getRank() == card.getRank()){
            return this.getSuit() - card.getSuit();
        }
        else{
            int ThisWeight = this.getRank();
            int CardWeight = card.getRank();

            if(this.getRank() == 0)
                ThisWeight = 13;
            if(this.getRank() == 1)
                ThisWeight = 14;
            if(card.getRank() == 0)
                CardWeight = 13;
            if(card.getRank() == 1)
                CardWeight = 14;   
            return ThisWeight - CardWeight;
            

        }
    }
}
