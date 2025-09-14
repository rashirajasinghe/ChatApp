import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Chat Client - Provides user interface for connecting to chat server
 */
public class ChatClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;
    
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner scanner;
    private String clientName;
    
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
    
    public void start() {
        scanner = new Scanner(System.in);
        
        try {
            // Connect to server
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            
            // Get client name
            System.out.print("Enter your name: ");
            clientName = scanner.nextLine();
            writer.println(clientName);
            
            // Start message reading thread
            Thread messageReader = new Thread(this::readMessages);
            messageReader.start();
            
            // Start message writing
            writeMessages();
            
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }
    
    /**
     * Reads messages from server in a separate thread
     */
    private void readMessages() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.err.println("Error reading messages: " + e.getMessage());
        }
    }
    
    /**
     * Handles user input and sends messages to server
     */
    private void writeMessages() {
        System.out.println("\n=== Welcome to Chat Application ===");
        System.out.println("Type your messages and press Enter to send.");
        System.out.println("Type '/quit' to exit the chat.");
        System.out.println("=====================================\n");
        
        try {
            String message;
            while (true) {
                System.out.print("You: ");
                message = scanner.nextLine();
                
                if (message.equalsIgnoreCase("/quit")) {
                    writer.println("/quit");
                    break;
                }
                
                if (!message.trim().isEmpty()) {
                    writer.println(message);
                }
            }
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
    
    /**
     * Closes all connections and resources
     */
    private void closeConnection() {
        try {
            if (scanner != null) scanner.close();
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
