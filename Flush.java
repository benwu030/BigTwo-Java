/**
 * The Flush class is a subclass of the Hand class and is used to model a hand called Flush.
 * @author Wu Sen Pan
 */
public class Flush extends Hand{

    private String type;

    /**
    * a constructor for building a hand of Flush with the specified player and list of cards.
    * @param player The player of this hand
    * @param card The list of cards that the player use
    */
   
   public Flush(CardGamePlayer player, CardList card){
       super(player, card);
       this.type = "Flush";
       this.setWinAgainst("Straight");
   }
    /**
    * a method for checking if this is a Flush hand.
    */
   public boolean isValid(){
    if(this.getTotalCardsNum() == 5){
        for(int i = 0; i < 5; i++)
           if(this.getCard(i).suit < 0 && this.getCard(i).suit > 3 && this.getCard(i).rank < 0 && this.getCard(i).rank > 12 )
               return false; 
       int suit = this.getCard(0).suit;
       for(int i = 1; i < 5; i++)
            if(suit != this.getCard(i).getSuit())
                return false;
        return true;
       }
       return false;

   }
    /**
     * a method for checking if this hand beats a specified hand.
     * @param hand used to compared this hand
     * @return true / false
     * @Override 
     */
   public boolean beats(Hand hand){
    if(this.getTotalCardsNum() == hand.getTotalCardsNum()){
        if(this.getType() == hand.getType()){
            if(this.getTopCard().getSuit() > hand.getTopCard().getSuit()){
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
    * a method for returning a string specifying the type of this Flush
    */
   public String getType(){
       return this.type;
   }
}
