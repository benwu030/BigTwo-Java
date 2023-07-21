/**
 * The FullHouse class is a subclass of the Hand class and is used to model a hand called FullHouse.
 * @author Wu Sen Pan
 */
public class FullHouse extends Hand{

    private String type;
    private Card pair_3;
    /**
    * a constructor for building a hand of FullHouse with the specified player and list of cards.
    * @param player The player of this hand
    * @param card The list of cards that the player use
    */
   
   public FullHouse(CardGamePlayer player, CardList card){
       super(player, card);
       this.type = "FullHouse";
       this.setWinAgainst("Straight");
       this.setWinAgainst("Flush");
   }
  /**
    * a method for checking if this is a FullHouse hand.
    */
   public boolean isValid(){
    if(this.getTotalCardsNum() == 5){
        for(int i = 0; i < 5; i++)
        if(this.getCard(i).suit < 0 && this.getCard(i).suit > 3 && this.getCard(i).rank < 0 && this.getCard(i).rank > 12 )
            return false; 
       int count;
       boolean is3pair = false;
       for(int i = 0; i < 5; i++){
            count = 1;
           for(int j = i+1; j < 5; j++){
               if(this.getCard(i).getRank() == this.getCard(j).getRank())
                count++;
           }
           if(count == 3)
            {
                this.pair_3 = this.getCard(i);
                is3pair = true;
                break;
            }

       }
       for(int i = 0; i < 5; i++)
        for(int j = i+1; j < 5; j++)
            if(this.getCard(i).getRank()==this.getCard(j).getRank() && this.getCard(j) !=pair_3 && is3pair)
                return true;
    }
    return false;
   }
   /**
     *  a method for retrieving the top card of the triplet.
     * @return the top card of this FullHouse
     * @Override
     */
    public Card getTopCard(){
        CardList Triplets = new CardList();
        for(int i = 0; i < this.getTotalCardsNum(); i++)
            if(this.getCard(i).getRank() == this.pair_3.getRank())
                Triplets.addCard(this.getCard(i));
        Triplets.sort();
        return Triplets.getCard(0);      
    }
   /**
    * a method for returning a string specifying the type of this FullHouse
    */
   public String getType(){
       return this.type;
   }
}
