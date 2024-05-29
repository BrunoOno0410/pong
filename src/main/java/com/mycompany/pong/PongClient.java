package com.mycompany.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PongClient extends JFrame {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private JPanel gamePanel;
    private String player;
    private int player1Y = 150, player2Y = 150, ballX = 290, ballY = 190;
    private int playerHeight = 50;
    private int player1Score = 0, player2Score = 0;

    public PongClient(String host, int port, String player) {
        this.player = player;
        try {
            socket = new Socket(host, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
        startListening();
    }

    private void initComponents() {
        setTitle("Pong Game - " + player);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                updateGamePanel(g);
            }
        };
        gamePanel.setBackground(Color.BLACK);
        add(gamePanel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                output.println(player + ":" + key);
            }
        });
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    private void startListening() {
        new Thread(() -> {
            try {
                String message;
                while ((message = input.readLine()) != null) {
                    if (message.equals("START_GAME")) {
                        // Iniciar o jogo
                        continue;
                    }
                    parseMessage(message);
                    gamePanel.repaint();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void parseMessage(String message) {
        String[] parts = message.split(" ");
        if (parts.length != 4)
            return;

        String[] p1 = parts[0].split(":");
        player1Y = Integer.parseInt(p1[1]);

        String[] p2 = parts[1].split(":");
        player2Y = Integer.parseInt(p2[1]);

        String[] ball = parts[2].split(":");
        String[] ballPos = ball[1].split(",");
        ballX = Integer.parseInt(ballPos[0]);
        ballY = Integer.parseInt(ballPos[1]);

        String[] score = parts[3].split(":");
        String[] scores = score[1].split(",");
        player1Score = Integer.parseInt(scores[0]);
        player2Score = Integer.parseInt(scores[1]);
    }

    private void updateGamePanel(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(10, player1Y, 10, playerHeight); // Player 1
        g.fillRect(580, player2Y, 10, playerHeight); // Player 2
        g.fillOval(ballX, ballY, 20, 20); // Ball
        g.drawString("Player 1: " + player1Score, 50, 10);
        g.drawString("Player 2: " + player2Score, 450, 10);
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;
        String player = JOptionPane.showInputDialog("Enter player (player1/player2):");
        PongClient client = new PongClient(host, port, player);
        client.setVisible(true);
    }
}
