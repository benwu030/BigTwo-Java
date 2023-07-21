/**
 * The Triple class is a subclass of the Hand class and is used to model a hand called Triple.
 * @author Wu Sen Pan
 */
public class Triple extends Hand{
    /**
    * a constructor for building a hand of Triple with the specified player and list of cards.
    * @param player The player of this hand
    * @param card The list of cards that the player use
    */
   private String type;
   public Triple(CardGamePlayer player, CardList card){
       super(player, card);
       this.type = "Triple";
   }
   /**
    * a method for checking if this is a Triple hand.
    */
   public boolean isValid(){
    if(this.getTotalCardsNum() == 3){
        for(int i = 0; i < 3; i++)
           if(this.getCard(i).suit < 0 && this.getCard(i).suit > 3 && this.getCard(i).rank < 0 && this.getCard(i).rank > 12 )
               return false;  
        if(this.getCard(0).rank == this.getCard(1).rank && this.getCard(1).rank == this.getCard(2).rank)
            return true;   
       
       }
       return false;
   }
   /**
    * a method for returning a string specifying the type of this Triple
    */
   public String getType(){
       return this.type;
   }
}
