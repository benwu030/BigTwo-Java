import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * The BigTwo class implements the CardGame interface and is used to model a Big
 * Two card game. It has private instance variables for storing the number of
 * players, a deck of cards, a list of players, a list of hands played on the
 * table, an index of the current player, and a user interface.
 * 
 * @author Wu Sen Pan
 */
public class BigTwo implements CardGame {
    private int numOfPlayers = 4;
    private Deck deck;
    private ArrayList<CardGamePlayer> playerList;
    private ArrayList<Hand> handsOnTable;
    private int currentPlayerIdx;
    
    private BigTwoGUI ui;
    private boolean GameEnded;
    private int noOfPass;
    private int round;
    /**
     * a BigTwo Client object to hold client
     */
    public BigTwoClient client;

    /**
     * a constructor for creating a Big Two card game. You should (i) create 4
     * players and add them to the player list; and (ii) create a BigTwoUI object
     * for providing the user interface.
     */
    public BigTwo() {

        this.playerList = new ArrayList<CardGamePlayer>();
        this.handsOnTable = new ArrayList<Hand>();
        for (int i = 0; i < this.numOfPlayers; i++){
            this.playerList.add(new CardGamePlayer());
            this.playerList.get(i).setName("");
        }
            
        this.ui = new BigTwoGUI(this);
        this.client = new BigTwoClient(this,this.ui);

    }

    /**
     * a method for getting the number of players.
     * 
     * @return number of players
     */
    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    /**
     * a method for retrieving the deck of cards being used
     * 
     * @return the deck is using
     */
    public Deck getDeck() {
        return this.deck;
    }

    /**
     * a method for retrieving the list of players.
     * 
     * @return the list of players
     */
    public ArrayList<CardGamePlayer> getPlayerList() {
        return this.playerList;
    }

    /**
     * a method for retrieving the list of hands played on the table
     * 
     * @return the list of hands played on the table
     */
    public ArrayList<Hand> getHandsOnTable() {

        return this.handsOnTable;
    }

    /**
     * a method for retrieving the index of the current player.
     * 
     * @return the index of current player.
     */
    public int getCurrentPlayerIdx() {
        return this.currentPlayerIdx;
    }

    /**
     * a method for starting/restarting the game with a given shuffled deck of
     * cards.
     */
    public void start(Deck deck) {
        this.deck = deck;
        this.GameEnded = false;
        this.noOfPass = 0;
        this.round = 0;
        for (int i = 0; i < this.numOfPlayers; i++)
            this.playerList.get(i).removeAllCards();
        for (int i = 0; i < 52; i++) {
            this.playerList.get(i / 13).addCard(this.deck.getCard(i));
            if (this.deck.getCard(i).getRank() == 2 && this.deck.getCard(i).getSuit() == 0) {
                // find who own diamond 3

                this.currentPlayerIdx = i / 13;
                this.ui.setActivePlayer(this.currentPlayerIdx);

            }
        }
        for (int i = 0; i < this.numOfPlayers; i++)
            this.playerList.get(i).sortCardsInHand();

    }

    /**
     * a method for making a move by a player with the specified index using the
     * cards specified by the list of indices
     */
    public void makeMove(int playerIdx, int[] cardIdx) {
        this.client.sendMessage(new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx));
        //checkMove(playerIdx, cardIdx);
    }

    /**
     * a method for checking a move made by a player.
     */
    public void checkMove(int playerIdx, int[] cardIdx) {
        CardGamePlayer currentGamePlayer = this.playerList.get(playerIdx);
        CardList cards = currentGamePlayer.play(cardIdx);
        Hand CurrentHand = BigTwo.composeHand(currentGamePlayer, cards);
        Hand LastHandOnTable;

        if (this.handsOnTable.isEmpty())
            LastHandOnTable = null;
        else
            LastHandOnTable = this.handsOnTable.get(this.handsOnTable.size() - 1);
        // check if it is invalid to pass or check if lasthanontable win currenthead

        if ((CurrentHand == null) || (LastHandOnTable != null && !CurrentHand.beats(LastHandOnTable))
                || (LastHandOnTable == null && CurrentHand.getType() == "Pass")
                || (this.round == 0 && !CurrentHand.contains(new Card(0, 2)))) {
            this.ui.printMsg("Not a legal move!!!");
            this.ui.resetSelected();
            this.ui.promptActivePlayer();
            return;
        }
        if (CurrentHand.getType() != "Pass") {
            String msg = "{" + CurrentHand.getType() + "}" + " " + CurrentHand.toString();
            this.ui.printMsg(msg);
            currentGamePlayer.removeCards(CurrentHand);
            this.handsOnTable.add(CurrentHand);

            this.currentPlayerIdx = (this.currentPlayerIdx + 1) % 4;
            this.ui.setActivePlayer(currentPlayerIdx);
            this.noOfPass = 0;
        }
        // pass
        else {
            String msg = "{Pass}" + " ";
            this.ui.printMsg(msg);
            this.noOfPass++;
            this.currentPlayerIdx = (this.currentPlayerIdx + 1) % 4;
            this.ui.setActivePlayer(currentPlayerIdx);
        }
        // check if game end
        if (currentGamePlayer.getNumOfCards() == 0) {
            String msg = "Games End\n";
            this.ui.printMsg(msg);
            this.GameEnded = true;
            this.handsOnTable.clear();
            for (int i = 0; i < this.numOfPlayers; i++) {
                CardGamePlayer gamer = this.playerList.get(i);
                if (gamer.getNumOfCards() > 0) {
                    msg += gamer.getName() + " has " + gamer.getNumOfCards() + " cards in hand.\n";
                    //this.ui.printMsg(msg);
                } else {
                    msg += gamer.getName() + " wins the game.\n";
                    //this.ui.printMsg(msg);
                }
               
            }
            JOptionPane.showConfirmDialog(null, msg);
            this.ui.repaint();
            this.ui.disable();
            return;
        }
        this.round++;

        // everyone passed
        if (noOfPass == 3) {
            this.handsOnTable.clear();
            this.noOfPass = 0;
        }
        this.ui.resetSelected();
        this.ui.repaint();
    }

    /**
     * @return true if the game ends; false otherwise
     */
    public boolean endOfGame() {
        return GameEnded;
    }

    /**
     * a method for starting a Big Two card game.
     */
    public static void main(String[] args) {
        BigTwo nCardGame = new BigTwo();
        //BigTwoDeck nDeck = new BigTwoDeck();
      
        //nCardGame.start(nDeck);
    }

    /**
     * a method for returning a valid hand from the specified list of cards of the
     * player.
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards) {
        if (cards == null) {
            Skip skip = new Skip(player, cards);
            if (skip.isValid())
                return skip;
        } else {
            if (cards.size() == 1) {
                Single single = new Single(player, cards);
                if (single.isValid())
                    return single;
            }
            if (cards.size() == 2) {
                Pair Pair = new Pair(player, cards);
                if (Pair.isValid())
                    return Pair;
            }
            if (cards.size() == 3) {
                Triple Tri = new Triple(player, cards);
                if (Tri.isValid())
                    return Tri;
            }
            if (cards.size() == 5) {
                Quad quad = new Quad(player, cards);
                Straight straight = new Straight(player, cards);
                FullHouse fullHouse = new FullHouse(player, cards);
                Flush flush = new Flush(player, cards);
                StraightFlush straightFlush = new StraightFlush(player, cards);
                if (straightFlush.isValid())
                    return straightFlush;
                else if (fullHouse.isValid())
                    return fullHouse;
                else if (quad.isValid())
                    return quad;
                else if (flush.isValid())
                    return flush;
                else if (straight.isValid())
                    return straight;
            }
        }
        return null;
    }
}
