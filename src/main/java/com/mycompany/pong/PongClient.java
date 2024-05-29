package com.mycompany.pong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class PongClient extends JFrame {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private GamePanel gamePanel;
    private JTextField ipField, portField;
    private String player;

    public PongClient(String player) {
        this.player = player; // Atribui o valor do jogador ao campo player
        setTitle("Pong Client");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel ipLabel = new JLabel("IP:");
        ipLabel.setBounds(10, 10, 80, 25);
        add(ipLabel);

        ipField = new JTextField("localhost");
        ipField.setBounds(100, 10, 160, 25);
        add(ipField);

        JLabel portLabel = new JLabel("Port:");
        portLabel.setBounds(10, 40, 80, 25);
        add(portLabel);

        portField = new JTextField("1234");
        portField.setBounds(100, 40, 160, 25);
        add(portField);

        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(10, 80, 250, 25);
        connectButton.addActionListener(e -> connectToServer());
        add(connectButton);

        gamePanel = new GamePanel();
        gamePanel.setBounds(10, 110, 260, 50);
        add(gamePanel);

        setVisible(true);
    }

    private void connectToServer() {
        String ip = ipField.getText();
        int port = Integer.parseInt(portField.getText());

        try {
            socket = new Socket(ip, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

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
                    out.println("player:" + e.getKeyCode());
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
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }

        public void updateGameState(String message) {
            // Atualizar a interface do cliente conforme necessário
            repaint();
        }
    }

    public static void main(String[] args) {
        String player = args.length > 0 ? args[0] : "player1";
        new PongClient(player);
    }
}
