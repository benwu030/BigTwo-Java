import java.util.*;
/**
 * The StraightFlush class is a subclass of the Hand class and is used to model a hand called StraightFlush.
 * @author Wu Sen Pan
 */
public class StraightFlush extends Hand{

    private String type;
    /**
    * a constructor for building a hand of StraightFlush with the specified player and list of cards.
    * @param player The player of this hand
    * @param card The list of cards that the player use
    */
   
   public StraightFlush(CardGamePlayer player, CardList card){
       super(player, card);
       this.type = "StraightFlush";
       this.setWinAgainst("Straight");
       this.setWinAgainst("Flush");
       this.setWinAgainst("FullHouse");
       this.setWinAgainst("Quad");
   }

    /**
    * a method for checking if this is a StraightFlush hand.
    */

   public boolean isValid(){
    if(this.getTotalCardsNum() == 5){
        
        
        for(int i = 0; i < 5; i++)
           if(this.getCard(i).suit < 0 && this.getCard(i).suit > 3 && this.getCard(i).rank < 0 && this.getCard(i).rank > 12 )
               return false; 
        //check if it is a flush
       int suit = this.getCard(0).suit;
       for(int i = 1; i < 5; i++)
            if(suit != this.getCard(i).getSuit())
                return false;
        //check if it is a straight
        ArrayList<Integer> Rank = new ArrayList<Integer>();
       for(int i = 0; i < 5; i++){
            if(!Rank.contains(this.getCard(i).rank)){
                if(this.getCard(i).rank == 0 ||this.getCard(i).rank == 1 ){
                    Rank.add(this.getCard(i).rank+13);
                }
                else
                    Rank.add(this.getCard(i).rank);
            }
                
       }
        if (Rank.size() == 5){
            
            System.out.println(Rank);
            int difference = Rank.get(4) - Rank.get(0);
            return (difference == 4);

       }
       else
        return false;

    }
    return false;
   }

   /**
    * a method for returning a string specifying the type of this StraightFlush
    */
   public String getType(){
       return this.type;
   }
}
