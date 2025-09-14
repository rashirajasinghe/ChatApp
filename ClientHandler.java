import java.io.*;
import java.net.*;

/**
 * ClientHandler - Manages individual client connections and message handling
 */
public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String clientName;
    
    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error creating client handler: " + e.getMessage());
        }
    }
    
    @Override
    public void run() {
        try {
            // Get client name
            clientName = reader.readLine();
            System.out.println("Client " + clientName + " joined the chat");
            
            // Notify other clients about new user
            ChatServer.broadcastMessage(clientName + " joined the chat!", this);
            
            String message;
            while ((message = reader.readLine()) != null) {
                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }
                
                // Format message with sender name and timestamp
                String formattedMessage = "[" + getCurrentTime() + "] " + clientName + ": " + message;
                System.out.println(formattedMessage);
                
                // Broadcast message to all other clients
                ChatServer.broadcastMessage(formattedMessage, this);
            }
        } catch (IOException e) {
            System.err.println("Error handling client " + clientName + ": " + e.getMessage());
        } finally {
            try {
                // Notify other clients about user leaving
                if (clientName != null) {
                    ChatServer.broadcastMessage(clientName + " left the chat.", this);
                }
                
                // Clean up resources
                ChatServer.removeClient(this);
                socket.close();
                reader.close();
                writer.close();
            } catch (IOException e) {
                System.err.println("Error closing client connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Sends a message to this specific client
     */
    public void sendMessage(String message) {
        if (writer != null) {
            writer.println(message);
        }
    }
    
    /**
     * Gets current timestamp
     */
    private String getCurrentTime() {
        return java.time.LocalTime.now().toString().substring(0, 8);
    }
    
    /**
     * Gets the client name
     */
    public String getClientName() {
        return clientName;
    }
}
