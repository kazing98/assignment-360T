package org.example.secondway;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * PlayerResponder class for Responder(Player2).
 * This class functions as a server socket, waiting for connections from
 * PlayerInitiator (Player1). Upon receiving a message, it sends
 * back a response that includes a message counter.
 * Ensure this server is running before starting the PlayerInitiator class.
 * Listens on port 5000 for incoming connections. For each received message,
 * it appends a counter and sends a response. The exchange continues until
 * 10 messages have been received.
 */
public class PlayerResponder {

    /**
     * Starts the server to receive the messages from client
     * @throws IOException to handle exceptions
     */
    public void server() throws IOException {
        try (ServerSocket serverSocket = createSocket()) {

            System.out.println("Player2 waiting for messages...");

            // Accept incoming connection
            Socket socket = serverSocket.accept();

            // Set up for communication
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Calling respondToMessage function to read and then write the message
            respondToMessage(reader, writer);
        }
    }

    /**
     * Used to start the server
     * @return ServerSocket connection port
     * @throws IOException to handle exception
     */
    public ServerSocket createSocket() throws IOException {
        return new ServerSocket(5000);
    }


    /**
     * Reads the inputs from client and sends back response
     * @param reader used to read client's(Player1) request
     * @param writer used to send server's(Player2) response
     * @throws IOException to handle exceptions
     */
    public static void respondToMessage(BufferedReader reader, BufferedWriter writer) throws IOException{
        String msg;
        int counter = 1;

        // Process incoming messages and reply with a counter
        while ((msg = reader.readLine()) != null && counter <= 10) {
            System.out.println("Player2 received: " + msg);
            String response = msg + " | response : " + counter++;
            writer.write(response + "\n");
            writer.flush();
        }
    }

    /**
     * Main method for secondway.PlayerResponder class.
     */
    public static void main(String[] args) {

        // Creating PlayerResponder instance
        PlayerResponder playerResponder = new PlayerResponder();
        try {
            // calling the server()
            playerResponder.server();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
