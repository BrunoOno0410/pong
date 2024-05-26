package com.mycompany.pong;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PongServer extends Thread {
    private int port;
    private PongServerForm form;
    private ServerSocket serverSocket;
    private List<PongServerThread> clients;

    public PongServer(int port, PongServerForm form) {
        this.port = port;
        this.form = form;
        clients = new ArrayList<>();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            form.updateStatus("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                form.updateStatus("New client connected");
                PongServerThread clientThread = new PongServerThread(clientSocket, this, form);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            form.updateStatus("Server error: " + e.getMessage());
        }
    }

    public void broadcast(String message) {
        for (PongServerThread client : clients) {
            client.sendMessage(message);
        }
    }

    public void updateStatus(String message) {
        form.updateStatus(message);
    }

    public static void main(String[] args) {
        PongServerForm form = new PongServerForm();
        form.setVisible(true);
    }
}
