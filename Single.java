/**
 * The Single class is a subclass of the Hand class and is used to model a hand called single.
 * @author Wu Sen Pan
 */
public class Single extends Hand{
     /**
     * a constructor for building a hand of single with the specified player and list of cards.
     * @param player The player of this hand
     * @param card The list of cards that the player use
     */
    private String type;
    public Single(CardGamePlayer player, CardList card){
        super(player, card);
        this.type = "Single";
    }
    /**
     * a method for checking if this is a valid Single.
     */
    public boolean isValid(){
        if(this.getTotalCardsNum() == 1){
            if(this.getCard(0).suit >= 0 && this.getCard(0).suit <= 3 && this.getCard(0).rank >= 0 && this.getCard(0).rank <= 12 )
                return true;            
        }
        return false;
    }
    /**
     * a method for returning a string specifying the type of this Single
     */
    public String getType(){
        return this.type;
    }
}
