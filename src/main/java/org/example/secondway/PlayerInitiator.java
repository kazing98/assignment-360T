package org.example.secondway;

import java.io.*;
import java.net.Socket;

/**
 * PlayerInitiator class for Sender(Player1).
 * This class connects to the receiver (Player2), sends a total of 10 messages,
 * and waits for a response to each message. After completing the exchange,
 * the connection is closed.
 * To run this program, ensure that PlayerResponser class (the server)
 * is started and listening on the appropriate port (5000) before starting this client.
 */
public class PlayerInitiator {

    /**
     * Establishes a connection with server before sending messages(requests)
     * @throws IOException to handle exception
     */
    public void connect() throws IOException {
        try (Socket socket = createSocket()) {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sendAndReceiveMessage(writer, reader);
        }
    }

    /**
     * Used to start a connection
     * @return Socket connection at location localhost and port 5000
     * @throws IOException to handle exception
     */
    public Socket createSocket() throws IOException {
        return new Socket("localhost", 5000);
    }

    /**
     * Sends message(request) to server(Player2) and prints its response
     * @param writer used to send client's(Player1) request
     * @param reader used to read server's(Player2) response
     * @throws IOException to handle exception
     */
    public void sendAndReceiveMessage(BufferedWriter writer, BufferedReader reader) throws IOException{

        // Sends and receives 10 messages
        for (int i = 1; i <= 10; i++) {
            String message = "Message No. " + i + " from Player1";
            System.out.println("Player1 sending: " + message);
            writer.write(message + "\n");
            writer.flush();

            String reply = reader.readLine();
            System.out.println("Player1 received: " + reply);
        }
        System.out.println("Player1 has sent and received 10 messages. Stopping...");
    }

    /**
     * Main method for secondway.PlayerInitiator class.
     */
    public static void main(String[] args) {

        // Creating PlayerInitiator instance
        PlayerInitiator playerInitiator = new PlayerInitiator();
        try {
            // calling the connect()
            playerInitiator.connect();
        } catch (IOException e) {
            // Catching IOException
            System.out.println("Please start PlayerResponser class before starting PlayerInitiator!!");
            System.out.println(e.getMessage());
        }
    }
}
