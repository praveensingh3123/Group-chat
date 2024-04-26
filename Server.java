package group.chatting.application;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable {
    
    Socket socket;

    static Map<Socket, String> socketToUsername = new HashMap<>();
    public static Vector client = new Vector();
    
    public Server (Socket socket) {
        try {
            this.socket = socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Read the username from the client
            String username = reader.readLine().trim();
            socketToUsername.put(socket, username);


            client.add(writer);
            
            while(true) {
                String data = reader.readLine().trim();
                System.out.println("Received " + data);

                if (data.equals("/signout")) {
                    handleSignOut(writer, socket);
                    break;
                }

                for (int i = 0; i < client.size(); i++) {
                    try {
                        BufferedWriter bw = (BufferedWriter) client.get(i);
                        bw.write(data);
                        bw.write("\r\n");
                        bw.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSignOut(BufferedWriter writer, Socket socket) {
        try {
            client.remove(writer);
            String username = socketToUsername.remove(socket);
            System.out.println(username + " has signed out.");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket s = new ServerSocket(2003);
        while(true) {
            Socket socket = s.accept();
            Server server = new Server(socket);
            Thread thread = new Thread(server);
            thread.start();
        }
    }
}

