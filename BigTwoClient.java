import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/*
 * The BigTwoClient class implements the NetworkGame interface. It is used to model a Big
 * Two game client that is responsible for establishing a connection and communicating with
 * the Big Two game server.
 * @author Wu Sen Pan
 */
public class BigTwoClient implements NetworkGame {

    private BigTwo game;
    private BigTwoGUI gui;
    private Socket sock;
    private ObjectOutputStream oos;
    private int playerID;
    private String playerName;
    private String serverIP;
    private int serverPort;


    /**
     * a constructor for creating a Big Two client.
     * 
     * @param game a reference to a BigTwo object associated with this client
     * @param gui  a reference to a BigTwoGUI object associated the BigTwo object
     */
    public BigTwoClient(BigTwo game, BigTwoGUI gui) {
        this.game = game;
        this.gui = gui;
       

        String PlayerName = JOptionPane.showInputDialog("Your name? ");
        if (PlayerName != "" || PlayerName != null) {
            setPlayerName(PlayerName);

        } else {
            setPlayerName("Anonymous Player");
        }
        setServerIP("127.0.0.1");
        setServerPort(2396);
        connect();
        //this.gui.disable();
        this.gui.repaint();
        

    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void connect() {
   
            try {
                sock = new Socket(this.getServerIP(), this.getServerPort());
                oos = new ObjectOutputStream(sock.getOutputStream());
                Runnable receiver = new ServerHandler(sock);
                Thread thread = new Thread(receiver);
                thread.start();
                sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, playerName));
                //thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        
    }

    public void parseMessage(GameMessage message) {
        int MessageId;
        String MessageName;
        //this.gui.printMsg(String.format("Message received from server! %d",message.getType()));
        switch (message.getType()) {
            case CardGameMessage.PLAYER_LIST:
              this.setPlayerID(message.getPlayerID()); 
              this.gui.setLocalPlayer(this.getPlayerID());
              //this.gui.printMsg(String.format("playerId%d", playerID));
              //this.gui.printMsg(String.format("playerName%s", playerName));
              String[] Playernames = (String[]) message.getData();
              //set name of others after receving msg from server
              for(int i = 0; i < 4; i++){
                
                  if(playerID != i){
                      if(Playernames[i]!=null)
                      this.game.getPlayerList().get(i).setName(Playernames[i]);
                      else
                      this.game.getPlayerList().get(i).setName("");
                  }
                  else
                    this.game.getPlayerList().get(i).setName(playerName);

                    //gui.printMsg(Playernames[i]);
              }
              this.gui.repaint();
              break;
            case CardGameMessage.FULL:
              JOptionPane.showConfirmDialog(null, "Server is full!","Server",JOptionPane.DEFAULT_OPTION);
              break;
            case CardGameMessage.QUIT:
                MessageId = message.getPlayerID();
                String port = (String) message.getData();
                MessageName = this.game.getPlayerList().get(MessageId).getName();
                this.gui.printMsg(String.format("%s has lost connection",port));
                this.gui.printMsg(String.format("%s quited the game.",MessageName));
                if(!this.game.endOfGame()){
                    this.game.getPlayerList().get(MessageId).setName("");
                    sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
                }
                this.gui.repaint();
              break;
            case CardGameMessage.JOIN:
                MessageId= message.getPlayerID();
                MessageName = (String) message.getData();
                if(MessageId!=playerID){
                    this.gui.printMsg(String.format("%s joined the game.",MessageName));
                    this.game.getPlayerList().get(MessageId).setName(MessageName);
                    this.gui.repaint();
                }
                else
                sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
                
                
              break;
            case CardGameMessage.READY:
                MessageId = message.getPlayerID();
                
                this.gui.printMsg(String.format("%s is ready!", this.game.getPlayerList().get(MessageId).getName()));
              break;
            case CardGameMessage.START:
                this.gui.printMsg("Everyone is ready, Let's start the game!");
                BigTwoDeck deck = (BigTwoDeck) message.getData();
                this.game.start(deck);
                
                this.gui.repaint();
              break;
            case CardGameMessage.MSG:
                MessageId = message.getPlayerID();
                MessageName = (String) message.getData();
                if(MessageId!=playerID)
                    this.gui.printChat(MessageName);
              break;
            case CardGameMessage.MOVE:
                MessageId = message.getPlayerID();
                int[] cardIdx = (int[])message.getData();
                this.game.checkMove(MessageId, cardIdx);
              break;
            default:
              this.gui.printMsg("Wrong message type: " + message.getType());
              // invalid message
            break;
            }
    }

    public void sendMessage(GameMessage message) {
            try{

                //(String.format("Message sent from %s type = %d", playerName,message.getType()));
                oos.writeObject(message);
                oos.flush();

            }catch(Exception ex){
                ex.printStackTrace();
            }
    }

    
    private class ServerHandler implements Runnable {
        private Socket serverSocket;
        private ObjectInputStream oistream;


        /**
		 * Creates and returns an instance of the ServerHandler class.
		 * 
		 * @param serverSocket
		 *            the socket connection to the server
		 */
		public ServerHandler(Socket serverSocket) {
			this.serverSocket = serverSocket;
			try {
				// creates an ObjectInputStream and chains it to the InputStream
				// of the server socket
				oistream = new ObjectInputStream(serverSocket.getInputStream());
			} catch (Exception ex) {
                ex.printStackTrace();	
            		}
		} // constructor

        // implementation of method from the Runnable interface
        @Override
        public void run() {
            CardGameMessage message;
            try{
                while ((message = (CardGameMessage) oistream.readObject()) != null) {
					//gui.printMsg("Message received from "+ serverSocket.getRemoteSocketAddress());
					parseMessage(message);
				} // close while
            }catch (Exception ex){
                ex.printStackTrace();
            }
            
        }

    }
       
}
