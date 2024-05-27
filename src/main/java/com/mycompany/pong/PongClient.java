package com.mycompany.pong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongClient extends JFrame {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private GamePanel gamePanel;

    public PongClient(String player) {
        try {
            socket = new Socket("localhost", 1234); // Certifique-se de que o endereço e a porta estão corretos
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            setTitle("Pong Game - " + player);
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gamePanel = new GamePanel();
            add(gamePanel);
            setVisible(true);

            // Enviar a identificação do jogador ao servidor
            out.println(player);

            // Thread para receber atualizações do servidor
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        // Processar a mensagem do servidor (ex: atualizar posição do jogador, bola,
                        // etc.)
                        gamePanel.updateGameState(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Adicionar KeyListener para capturar eventos de tecla pressionada
            gamePanel.addKeyListener(new KeyListener() {
                @Override
                public void keyPressed(KeyEvent e) {
                    out.println(player + ":" + e.getKeyCode());
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }

                @Override
                public void keyTyped(KeyEvent e) {
                }
            });
            gamePanel.setFocusable(true);
            gamePanel.requestFocusInWindow();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class GamePanel extends JPanel {
        int player1Y = 150, player2Y = 150, ballX = 290, ballY = 190;

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            g.fillRect(10, player1Y, 10, 50);
            g.fillRect(580, player2Y, 10, 50);
            g.fillOval(ballX, ballY, 20, 20);
        }

        public void updateGameState(String message) {
            // Processar a mensagem e atualizar os estados do jogo
            // Exemplo de mensagem: "P1:150 P2:150 BALL:290,190"
            String[] parts = message.split(" ");
            player1Y = Integer.parseInt(parts[0].split(":")[1]);
            player2Y = Integer.parseInt(parts[1].split(":")[1]);
            String[] ballCoords = parts[2].split(":")[1].split(",");
            ballX = Integer.parseInt(ballCoords[0]);
            ballY = Integer.parseInt(ballCoords[1]);
            repaint();
        }
    }

    public static void main(String[] args) {
        String player = args.length > 0 ? args[0] : "player1";
        new PongClient(player);
    }
}
