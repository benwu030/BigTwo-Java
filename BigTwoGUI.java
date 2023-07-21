import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * The BigTwoGUI class implements the CardGameUI interface. It is used to build
 * a GUI for the Big Two card game and handle all user actions.
 * 
 * @author Wu Sen Pan
 */
public class BigTwoGUI implements CardGameUI {
    private BigTwo game;
    /**
     * a variable to store index of cards being selected
     */
    public boolean[] selected;
    private int activePlayer;
    private int localPlayer;
    private JFrame frame;
    private JPanel bigTwoPanel;
    private JButton playButton;
    private JButton passButton;
    private JTextArea msgArea;
    private JTextArea chatArea;
    private JTextField chatInput;
    private Image[] avatars;
    private Image cardsCover;
    private Image[][] cardsJPG;
    private ArrayList<CardGamePlayer> playerList; // the list of players
    private ArrayList<Hand> handsOnTable; // the list of hands played on the
    private String path;
    private String[] RANKS = { "ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king" };
    private String[] SUITS = { "diamonds", "clubs", "hearts", "spades" };
    private Color[] Players_Color;

    /**
     * a constructor for creating a BigTwoGUI.
     * 
     * @param game The parameter game is a reference to a Big Two card game
     *             associates with this GUI.
     */
    public BigTwoGUI(BigTwo game) {
        this.game = game;
        selected = new boolean[13];
        playerList = game.getPlayerList();
        handsOnTable = game.getHandsOnTable();

        Players_Color = new Color[4];
        Players_Color[0] = new Color(66, 104, 124);
        Players_Color[1] = new Color(132, 165, 184);
        Players_Color[2] = new Color(179, 218, 241);
        Players_Color[3] = new Color(203, 203, 203);

        avatars = new Image[4];
        cardsJPG = new Image[4][13];

        // get images of avatars
        for (Integer i = 1; i <= 4; i++) {
            path = "Avatars/" + "Player" + i.toString() + ".png";
            avatars[i - 1] = new ImageIcon(path).getImage();
        }

        // get images of cards
        for (Integer i = 0; i < 4; i++) {
            for (Integer j = 0; j < 13; j++) {
                if (j >= 10)
                    path = "Cards/" + RANKS[j] + "_of_" + SUITS[i] + "2.png";
                else
                    path = "Cards/" + RANKS[j] + "_of_" + SUITS[i] + ".png";
                cardsJPG[i][j] = new ImageIcon(path).getImage().getScaledInstance(80, 125, Image.SCALE_SMOOTH);
            }
        }

        // get images of card cover
        path = "Cards/Cover.png";
        cardsCover = new ImageIcon(path).getImage().getScaledInstance(80, 125, Image.SCALE_SMOOTH);
        // create frame
        // create panel to hold the playground
        bigTwoPanel = new BigTwoPanel();

        // msg and chat panel
        JPanel MsgAndChatPanel = new JPanel();
        MsgAndChatPanel.setPreferredSize(new Dimension(500, 850));
        MsgAndChatPanel.setLayout(new BorderLayout(5, 5));

        // msg panel
        JPanel Msgpanel = new JPanel();
        Msgpanel.setLayout(new BorderLayout());
        // msg message Area
        JLabel MsgHeading = new JLabel("Game Message:");

        msgArea = new JTextArea();
        msgArea.setBackground(Color.white);
        msgArea.setEditable(false);
        //msgArea.setCaretPosition(msgArea.getDocument().getLength());
        JScrollPane msgScroller = new JScrollPane(msgArea);
        msgArea.setLineWrap(true);
        msgScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        msgScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        msgScroller.setPreferredSize(new Dimension(500, 300));
        Msgpanel.add(MsgHeading, BorderLayout.NORTH);
        Msgpanel.add(msgScroller, BorderLayout.CENTER);
        MsgAndChatPanel.add(Msgpanel, BorderLayout.NORTH);

        // chat panel
        JPanel Chatpanel = new JPanel();
        JLabel ChatHeading = new JLabel("Chat:");
        Chatpanel.setLayout(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setBackground(Color.white);
        chatArea.setEditable(false);
        JScrollPane chatScroller = new JScrollPane(chatArea);
        chatScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Chatpanel.add(ChatHeading, BorderLayout.NORTH);
        Chatpanel.add(chatScroller, BorderLayout.CENTER);

        // chat input panel
        JPanel ChatInputpanel = new JPanel();
        ChatInputpanel.setLayout(new BorderLayout());
        JLabel ChatInputHeading = new JLabel("Input a message:");
        chatInput = new JTextField();
        chatInput.setPreferredSize(new Dimension(500, 30));
        chatInput.addKeyListener(new SendMessageListener());
        ChatInputpanel.add(ChatInputHeading, BorderLayout.NORTH);
        ChatInputpanel.add(chatInput, BorderLayout.CENTER);
        Chatpanel.add(ChatInputpanel, BorderLayout.SOUTH);
        MsgAndChatPanel.add(Chatpanel, BorderLayout.CENTER);

        // menu
        JMenuBar menubar = new JMenuBar();
        JMenuItem Connect = new JMenuItem("Conncet");
        JMenuItem quit = new JMenuItem("Quit");
        JMenuItem ClearChat = new JMenuItem("Clear Chat");
        JMenuItem ClearServerChat = new JMenuItem("Clear Server Message");
        Connect.addActionListener(new ConnectMenuItemListener());
        quit.addActionListener(new QuitMenuItemListener());
        ClearChat.addActionListener(new ClearChatMenu());
        ClearServerChat.addActionListener(new ClearServerMenu());
        menubar.add(Connect);
        menubar.add(quit);
        menubar.add(ClearChat);
        menubar.add(ClearServerChat);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Big Two");

        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setJMenuBar(menubar);
        
        

        frame.add(bigTwoPanel, BorderLayout.CENTER);
        frame.add(MsgAndChatPanel, BorderLayout.EAST);
      
        frame.setSize(new Dimension(1000, 850));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * a method for setting the index of the active player (i.e., the player having
     * control of the GUI).
     * 
     * @param activePlayer an int value representing the index of the active player
     */
    public void setActivePlayer(int activePlayer) {
        if (activePlayer < 0 || activePlayer >= playerList.size()) {
            this.activePlayer = -1;
        } else {
            this.activePlayer = activePlayer;
        }
    }
    /**
     * a method for setting the index of the local player (i.e., the player having
     * control of the GUI).
     * 
     * @param localPlayer an int value representing the index of the local player
     */
    public void setLocalPlayer(int localPlayer){
        if (localPlayer < 0 || localPlayer >= playerList.size()) {
            this.localPlayer = -1;
        } else {
            this.localPlayer = localPlayer;
        }
    }

    /**
     * a method for repainting the GUI
     */
    public void repaint() {
        this.resetSelected();
		frame.repaint();
    }

    /**
     * a method for printing the specified string to the message area of the GUI. .
     * 
     * @param msg the string to be printed to the message area of the card game user
     *            interface
     */
    public void printMsg(String msg) {
        this.msgArea.append(msg + "\n");
    }

    /**
     * a method for clearing the message area of the GUI. .
     */
    public void clearMsgArea() {
        this.msgArea.setText("");
    }
    /**
     * a method for printing the specified string to the chat area of the GUI. .
     * 
     * @param msg the string to be printed to the chat area of the card game user
     *            interface
     */
    public void printChat(String msg) {
        this.chatArea.append(msg + "\n");
    }

    /**
     * a method for clearing the chat area of the GUI. .
     */
    public void clearChatArea() {
        this.chatArea.setText("");
    }
    /**
     * a method for resetting the GUI. You should (i) reset the list of selected
     * cards; (ii) clear the message area; and (iii) enable user interactions..
     */
    public void reset() {
        this.resetSelected();
        this.clearMsgArea();
        this.enable();
        this.repaint();

    }

    /**
     * a method for enabling user interactions with the GUI. You should (i) enable
     * the Play button and Pass button (i.e., making them clickable); (ii) enable
     * the chat input; and (iii) enable the BigTwoPanel for selection of cards
     * through mouse clicks.
     */
    public void enable() {
        playButton.setEnabled(true);
        passButton.setEnabled(true);
        chatInput.setEnabled(true);
        bigTwoPanel.setEnabled(true);
    }

    /**
     * a method for disabling user interactions with the GUI. You should (i) disable
     * the Play button and Pass button (i.e., making them not clickable); (ii)
     * disable the chat input; and (ii) disable the BigTwoPanel for selection of
     * cards through mouse clicks.
     */
    public void disable() {
        playButton.setEnabled(false);
        passButton.setEnabled(false);
        chatInput.setEnabled(false);
        bigTwoPanel.setEnabled(false);
       
    }

    /**
     * a method for prompting the active player to select cards and make his/her
     * move. A message should be displayed in the message area showing it is the
     * active player's turn.
     */
    public void promptActivePlayer() {
        printMsg(playerList.get(activePlayer).getName() + "'s turn: ");

    }

    private class BigTwoPanel extends JPanel implements MouseListener {

        public BigTwoPanel() {
            this.setLayout(new BorderLayout());
            this.setBackground(Color.GREEN);
            this.setPreferredSize(new Dimension(500, 850));
            // panel to hold buttons
            JPanel buttoPanel = new JPanel();
            buttoPanel.setPreferredSize(new Dimension(500, 50));
            playButton = new JButton("Play");// create play button
            playButton.addActionListener(new PlayButtonListener());

            passButton = new JButton("Pass");// create pass button
            passButton.addActionListener(new PassButtonListener());

            buttoPanel.add(playButton);
            buttoPanel.add(passButton);

            this.add(buttoPanel, BorderLayout.SOUTH);
            this.addMouseListener(this);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
           
            
            
            if(localPlayer!=activePlayer){
                
                playButton.setEnabled(false);
                passButton.setEnabled(false);
                
            }
            else{
                playButton.setEnabled(true);
                passButton.setEnabled(true);
            }
            // print the planes for each player
            for (int i = 0; i < 4; i++) {
                CardGamePlayer Player = playerList.get(i);
                CardList cards = Player.getCardsInHand();
                String PlayerName = Player.getName();
                g.setColor(Players_Color[i]);
                g.fillRect(0, i * 160, 500, 160);
                
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                if(i==activePlayer)
                    g.setColor(Color.red);
                else{
                    g.setColor(Color.white);
                }
                if(i == localPlayer)
                    g.drawString(PlayerName+" (You)", 5, i * 160 + 20);
                else 
                    g.drawString(PlayerName, 5, i * 160 + 20);
                
               
                    
                Image avatar = avatars[i];
                if(Player.getName()!=""){
                g.drawImage(avatar, 5, i * 160 + 40, 120, 120, this);
                
                // draw the cards for each player
                for(int j = 0; j < cards.size(); j++){
                    //cards.print();
                    if(i == localPlayer || game.endOfGame()){
                    if(selected[j]){//draw selected cards
                        g.drawImage(cardsJPG[cards.getCard(j).getSuit()][cards.getCard(j).getRank()], 135+j*15, i*160+30-10, this);
                    }
                    else{
                        g.drawImage(cardsJPG[cards.getCard(j).getSuit()][cards.getCard(j).getRank()], 135+j*15, i*160+30,  this);
                    }
                }
                else{//draw cards for non local player
                    g.drawImage(cardsCover, 135+j*15, i*160+30, this);
                }
            }

             
                }
                else{
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                     g.drawString("Waiting for Player", 0, i * 160 + 80);
                }

                if(game.endOfGame() && i == ((localPlayer + 4 -1) % 4)){
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                     g.drawString("Win", 200, i * 160 + 80);
                }
            }
            //draw table

            g.setColor(new Color(112,117,113));
            g.fillRect(0, 4 * 160, 500, 160);
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.BOLD, 20));
            

            //draw table cards
            if(handsOnTable.isEmpty()){
                g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                g.drawString("Empty", 5, 4 * 160 + 80);
            }
            else{
                Hand lastHandOnTable = (handsOnTable.isEmpty()) ? null : handsOnTable.get(handsOnTable.size() - 1);
                if (lastHandOnTable != null) {
                    for(int i = 0; i < lastHandOnTable.size(); i++){
                        g.drawImage(cardsJPG[lastHandOnTable.getCard(i).getSuit()][lastHandOnTable.getCard(i).getRank()], 10+i*15, 4*160+30,  this);
                    }
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                    g.drawString("Played by: "+lastHandOnTable.getPlayer().getName()+"    "+lastHandOnTable.getType(), 5, 4 * 160 + 20);

                } else {
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
                    g.drawString("Empty", 5, 4 * 160 + 80);
                }
            }
           
                
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            CardList Currentcards = playerList.get(localPlayer).getCardsInHand();

            if (Currentcards.size() > 0 && !game.endOfGame()) {
                int x = e.getX();
                int y = e.getY();
                // System.out.format("x:%d y:%d", x, y);
                for (int i = 0; i < Currentcards.size() - 1; i++) {

                    if (FindCardChoose(i, x, y) && !FindCardChoose(i + 1, x, y)) {
                        if (selected[i])
                            selected[i] = false;
                        else
                            selected[i] = true;

                    }

                }

                if (FindCardChoose(Currentcards.size() - 1, x, y)) {
                    if (selected[Currentcards.size() - 1])
                        selected[Currentcards.size() - 1] = false;
                    else
                        selected[Currentcards.size() - 1] = true;
                }

            }

            this.repaint();
        }

        private boolean FindCardChoose(int n, int x, int y) {
            int LeftMargin = 135+n*15;
            int RightMargin = LeftMargin + 15;
            int UpMargin = localPlayer*160+30  + (selected[n] ? -10 : 0);
            int BottomMargin = UpMargin + 125;
            // System.out.format("Locations of %d%n%d %d%n%d %d%n%n", n, LeftMargin, RightMargin, UpMargin, BottomMargin);
            if(n == playerList.get(localPlayer).getCardsInHand().size()-1){
                RightMargin = LeftMargin + 80;
            }
            if (x >= LeftMargin && x <= RightMargin && y >= UpMargin && y <= BottomMargin) {

                return true;
            }
            return false;

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

    }

    private class PlayButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (getSelected() != null) {
                game.makeMove(localPlayer, getSelected());
            } else {
                printMsg(String.format("Please select cards to play.%nFor {Pass}, please press <Pass> Button."));
            }

        }
    }

    private class PassButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            game.makeMove(localPlayer, null);
        }
    }

    private class ConnectMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(null, String.format("Are ready to connect to the game?\n"),
                    "Restart", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                game.client.connect();
            }
        }
    }

    private class QuitMenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(null, String.format("Are you sure to leave the game?\n"), "Exit",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }

        }

    }

    private class ClearServerMenu implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            clearMsgArea();
        }
    }
    private class ClearChatMenu implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            clearChatArea();
        }
    }
    private class SendMessageListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                chatArea.append(playerList.get(localPlayer).getName() + ": " + chatInput.getText() + "\n");
                game.client.sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, chatInput.getText() + "\n"));
                chatInput.setText("");
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }

    }

    /**
     * a method to get selected card but player
     * 
     * @return cardlist
     */
    public int[] getSelected() {

        /*System.out.println("outside");
        for (int i = 0; i < 13; i++)
            System.out.println(selected[i]);*/
        int[] cardIdx = null;
        int count = 0;

        // calculate number of cards being selected
        for (int j = 0; j < this.selected.length; j++) {
            if (this.selected[j]) {
                count++;
            }
        }

        if (count != 0) {
            cardIdx = new int[count];
            count = 0;
            for (int j = 0; j < this.selected.length; j++) {
                if (this.selected[j]) {
                    cardIdx[count] = j;
                    count++;
                }
            }
        }
        return cardIdx;
    }

    /**
     * this method is to reset selected cards
     */
    public void resetSelected() {
        selected = new boolean[13];
        bigTwoPanel.repaint();
    }

}