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
    private boolean gameStarted = false;
    public int player1Score = 0; // Torne os campos públicos
    public int player2Score = 0; // Torne os campos públicos
    private int ballX = 290, ballY = 190, ballDirX = 3, ballDirY = 3;
    private int player1Y = 150, player2Y = 150;
    private final int playerHeight = 50;

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

                if (clients.size() == 2 && !gameStarted) {
                    startGame();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            form.updateStatus("Server error: " + e.getMessage());
        }
    }

    private void startGame() {
        gameStarted = true;
        broadcast("START_GAME");
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {
        while (true) {
            ballX += ballDirX;
            ballY += ballDirY;

            // Colisão com as bordas superiores e inferiores
            if (ballY <= 0 || ballY >= 380) {
                ballDirY = -ballDirY;
            }

            // Colisão com as barras dos jogadores
            if (ballX <= 20 && ballY >= player1Y && ballY <= player1Y + playerHeight) {
                ballDirX = -ballDirX;
            }
            if (ballX >= 560 && ballY >= player2Y && ballY <= player2Y + playerHeight) {
                ballDirX = -ballDirX;
            }

            // Pontuação
            if (ballX <= 0) {
                player2Score++;
                resetBall();
            }
            if (ballX >= 580) {
                player1Score++;
                resetBall();
            }

            broadcast("P1:" + player1Y + " P2:" + player2Y + " BALL:" + ballX + "," + ballY + " SCORE:" + player1Score
                    + "," + player2Score);

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetBall() {
        ballX = 290;
        ballY = 190;
        ballDirX = -ballDirX;
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
