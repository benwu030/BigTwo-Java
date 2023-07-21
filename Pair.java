/**
 * The Single class is a subclass of the Hand class and is used to model a hand called pair.
 * @author Wu Sen Pan
 */
public class Pair extends Hand{
    /**
    * a constructor for building a hand of Pair with the specified player and list of cards.
    * @param player The player of this hand
    * @param card The list of cards that the player use
    */
   private String type;
   public Pair(CardGamePlayer player, CardList card){
       super(player, card);
       this.type = "Pair";
   }
   /**
    * a method for checking if this is a valid Pair.
    */
   public boolean isValid(){
       
       if(this.getTotalCardsNum() == 2){
        for(int i = 0; i < 2; i++)
           if(this.getCard(i).suit < 0 && this.getCard(i).suit > 3 && this.getCard(i).rank < 0 && this.getCard(i).rank > 12 )
               return false;   
        if(this.getCard(0).rank == this.getCard(1).rank)
            return true;         
       }
       return false;
   }
   /**
    * a method for returning a string specifying the type of this Pair
    */
   public String getType(){
       return this.type;
   }
}
