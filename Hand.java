import java.util.ArrayList;

/**
 * The Hand class is a subclass of the CardList class and is used to model a hand of cards.
 * @author Wu Sen Pan
 */
public abstract class Hand extends CardList{
    private CardGamePlayer player;
    private int TotalCardsNum;
    private ArrayList<String> WinAgainst;
    /**
     * a constructor for building a hand with the specified player and list of cards.
     * @param player The player of this hand
     * @param cards The list of cards that the player use
     */
    public Hand(CardGamePlayer player, CardList cards){
        this.player = player;
        if(cards != null){
            this.TotalCardsNum = cards.size();
            for(int i = 0; i < cards.size(); i++)
                this.addCard(cards.getCard(i));
            this.sort();
        }
       else{
           this.TotalCardsNum = 0;
       }
       this.WinAgainst = new ArrayList<String>();
    }
    /**
     * a method for retrieving the player of this hand.
     * @return the player of this hand
     */
    public CardGamePlayer getPlayer(){
        return this.player;
    }
    /**
     * a method for retrieving the top card of this hand.
     * @return the top card of this hand
     */
    public Card getTopCard(){
        this.sort();
        return this.getCard(0);
    }
    /**
     * a method for retrieving the number of cards of current hand
     * @return number of cards
     */
    public int getTotalCardsNum(){
        return this.TotalCardsNum;
    }
    /**
     * a method for retrieving list of winable combinations of a specified hand
     * @return
     */
    public ArrayList<String> getWinAgainst(){
        return this.WinAgainst;
    }
     /**
     * a method for adding a element to the  list of winable combinations of a specified hand
     * @return
     */
    public void setWinAgainst(String type){
        this.WinAgainst.add(type);
    }
    /**
     * a method for checking if this hand beats a specified hand.
     * @param hand used to compared this hand
     * @return true / false
     */
    public boolean beats(Hand hand){
        if(hand == null)
            return true;
        if(this.getTotalCardsNum() == hand.getTotalCardsNum()){
            if(this.getType() == hand.getType()){
                if(this.getTopCard().compareTo(hand.getTopCard()) > 0){
                    return true;
                }
                else
                    return false;
            }
            else{
                if(this.getWinAgainst().contains(hand.getType()))
                    return true;
            }
        }
        return false;
    }
    /**
     *  a method for checking if this is a valid hand.
     * @return true/false
     */
    public abstract boolean isValid();
    /**
     * a method for returning a string specifying the type of this hand.
     * @return a string representing type of the hand e.g. single, pair,triple.etc
     */
    public abstract String getType();
}
