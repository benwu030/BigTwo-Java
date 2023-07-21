/**
 * The Quad class is a subclass of the Hand class and is used to model a hand called Quad.
 * @author Wu Sen Pan
 */
public class Quad extends Hand{

    private String type;
    private Card pair_4;
    /**
    * a constructor for building a hand of Quad with the specified player and list of cards.
    * @param player The player of this hand
    * @param card The list of cards that the player use
    */
   
   public Quad(CardGamePlayer player, CardList card){
       super(player, card);
       this.type = "Quad";
       this.setWinAgainst("Straight");
       this.setWinAgainst("Flush");
       this.setWinAgainst("FullHouse");
   }
   public boolean isValid(){
    if(this.getTotalCardsNum() == 5){
        for(int i = 0; i < 5; i++)
            if(this.getCard(i).suit < 0 && this.getCard(i).suit > 3 && this.getCard(i).rank < 0 && this.getCard(i).rank > 12 )
                return false; 
       int count;
       for(int i = 0; i < 5; i++){
            count = 1;
           for(int j = i+1; j < 5; j++){
               if(this.getCard(i).getRank() == this.getCard(j).getRank())
                count++;
           }
           if(count == 4)
            {
                this.pair_4 = this.getCard(i);
                return true;
            }

       }
    }
    return false;
   }
   /**
     *  a method for retrieving the top card of the triplet.
     * @return the top card of this Quad
     * @Override
     */
    public Card getTopCard(){
        CardList Quads = new CardList();
        for(int i = 0; i < this.getTotalCardsNum(); i++)
            if(this.getCard(i).getRank() == this.pair_4.getRank())
                Quads.addCard(this.getCard(i));
        Quads.sort();
        return Quads.getCard(0);      
    }
   /**
    * a method for returning a string specifying the type of this Quad
    */
   public String getType(){
       return this.type;
   }
}
