import java.util.*;

/**
 * The Straight class is a subclass of the Hand class and is used to model a hand called Straight.
 * @author Wu Sen Pan
 */
public class Straight extends Hand{

    private String type;

    /**
    * a constructor for building a hand of Straight with the specified player and list of cards.
    * @param player The player of this hand
    * @param card The list of cards that the player use
    */
   
   public Straight(CardGamePlayer player, CardList card){
       super(player, card);
       this.type = "Straight";
   }
    /**
    * a method for checking if this is a Straight hand.
    */
   public boolean isValid(){
    if(this.getTotalCardsNum() == 5){
     
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

      if(Rank.size() < 5)
        return false;

       else if (Rank.size() == 5){
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
    * a method for returning a string specifying the type of this Straight
    */
   public String getType(){
       return this.type;
   }
}
