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
    private int playerY = 150, ballX = 290, ballY = 190;
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
                    if (message.startsWith("P1") || message.startsWith("P2")) {
                        updateGameState(message);
                    } else if (message.equals("START_GAME")) {
                        // Lógica para iniciar o jogo, se necessário
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateGameState(String message) {
        String[] parts = message.split(" ");
        if (parts.length > 3) {
            playerY = Integer.parseInt(parts[0].split(":")[1]);
            ballX = Integer.parseInt(parts[2].split(":")[1].split(",")[0]);
            ballY = Integer.parseInt(parts[2].split(":")[1].split(",")[1]);
            player1Score = Integer.parseInt(parts[3].split(":")[1].split(",")[0]);
            player2Score = Integer.parseInt(parts[3].split(":")[1].split(",")[1]);
            gamePanel.repaint();
        }
    }

    private void updateGamePanel(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(10, playerY, 10, playerHeight);
        g.fillRect(580, playerY, 10, playerHeight);
        g.fillOval(ballX, ballY, 20, 20);
        g.drawString("Player 1: " + player1Score, 10, 10);
        g.drawString("Player 2: " + player2Score, 500, 10);
    }

    public static void main(String[] args) {
        String host = JOptionPane.showInputDialog("Enter server IP:");
        int port = Integer.parseInt(JOptionPane.showInputDialog("Enter server port:"));
        String player = JOptionPane.showInputDialog("Enter player (player1/player2):");

        SwingUtilities.invokeLater(() -> new PongClient(host, port, player).setVisible(true));
    }
}
