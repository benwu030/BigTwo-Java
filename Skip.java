/**
 * The Skip class is a subclass of the Hand class and is used to model a hand called Skip.
 * @author Wu Sen Pan
 */
public class Skip extends Hand{
    private String type;
    
    /**
    * a constructor for building a hand of Skip with the specified player and list of cards.
    * @param player The player of this hand
    * @param card The list of cards that the player use
    */
   
   public Skip(CardGamePlayer player, CardList card){
       super(player, card);
       this.type = "Pass";
    
   }
   /**
    * a method for checking if this is a valid Skip.
    */
   public boolean isValid(){
       if(this.getTotalCardsNum() == 0 || this.size()==0){       
           return true;
       }
       return false;
   }
   /**
    * a method for returning a string specifying the type of this Skip
    */
   public String getType(){
       return this.type;
   }

    /**
     * a method for checking if this hand beats a specified hand.
     * @param hand used to compared this hand
     * @return true / false
     */
    public boolean beats(Hand hand){
        return true;
    }
}
