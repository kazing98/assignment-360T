package org.example.firstway;

/**
 * Represents a player in a message-passing system.
 * Each player sends a message to the next player in a chain.
 */
public class Player {
    // Fields
    private Player nextPlayer;
    private String name;
    boolean initiative;
    private int sentMessageCounter = 0;
    private int receivedRepliesCounter = 0;
    private final int MAX_MESSAGES = 10;

    /**
     * Constructs a new Player.
     *
     * @param name       the name of the player.
     * @param initiative true if this player starts the communication(or initiator).
     */
    Player(String name, boolean initiative) {
        this.name = name;
        this.initiative = initiative;
    }

    /**
     * Sets the next player to whom message has to be sent.
     *
     * @param nextPlayer the next player
     */
    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    /**
     * Sends a message to the next player.
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {

        /*
        Using Ternary operator to Assign Player names and prepare the message to be sent.
        This works only for two players. For more than two players, we need to change the logic accordingly.
         */
        String newMessage = (initiative ? "Player1" : "Player2")
                + (initiative ? " sent: " : " received: ")
                + " " + messageCountToStr(sentMessageCounter)
                + " " + message;


        System.out.println(newMessage);
        sentMessageCounter++;
        nextPlayer.receiveMessage(message);
    }

    /**
     * Receives the message and sends to the next player.
     *
     * @param message the message to send
     */
    public void receiveMessage(String message) {

        // Creating reply message
        String reply = message + " | response : " + (sentMessageCounter + 1);

        if (!initiative) {
            // Responder sends reply
            sendMessage(reply);
        } else {
            // Initiator handles reply
            receivedRepliesCounter++;
            if (receivedRepliesCounter < MAX_MESSAGES) {
                sendMessage("Message from " + name);
            } else {
                System.out.println(name + " has sent and received 10 messages. Stopping...");
            }
        }
    }

    /**
     * Converts messageCounter(int) to their English count(String).
     *
     * @param messageCounter count of the message
     */
    public String messageCountToStr(int messageCounter) {
        return switch (messageCounter) {
            case 0 -> "First";
            case 1 -> "Second";
            case 2 -> "Third";
            case 3 -> "Fourth";
            case 4 -> "Fifth";
            case 5 -> "Sixth";
            case 6 -> "Seventh";
            case 7 -> "Eighth";
            case 8 -> "Ninth";
            default -> "Tenth";
        };
    }


    /**
     * Main method of java.firstway package.
     * Communication starts from here.
     */
    public static void main(String[] args) {

        // Player Instances
        Player initiator = new Player("Player1", true);
        Player secondPlayer = new Player("Player2", false);

        // Setting nextPlayer
        initiator.setNextPlayer(secondPlayer);
        secondPlayer.setNextPlayer(initiator);

        // Starting the communication
        initiator.sendMessage("Message from Player1");

    }
}