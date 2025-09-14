import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Chat Server - Handles multiple client connections and message broadcasting
 */
public class ChatServer {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clients = new HashSet<>();
    
    public static void main(String[] args) {
        System.out.println("Starting Chat Server on port " + PORT + "...");
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for clients...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    
    /**
     * Broadcasts a message to all connected clients except the sender
     */
    public static void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }
    
    /**
     * Removes a client from the active clients list
     */
    public static void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
            System.out.println("Client disconnected. Active clients: " + clients.size());
        }
    }
    
    /**
     * Gets the number of active clients
     */
    public static int getClientCount() {
        return clients.size();
    }
}
