package com.mycompany.pong;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PongServerForm extends javax.swing.JFrame {

        private int player1Y = 150, player2Y = 150, ballX = 290, ballY = 190;
        private int playerHeight = 50;
        private int player1Score = 0, player2Score = 0;

        public PongServerForm() {
                initComponents();
        }

        public void atualizaPainel(Graphics g) {
                g.setColor(Color.green);
                g.fillRect(10, player1Y, 10, playerHeight);
                g.fillRect(580, player2Y, 10, playerHeight);
                g.fillOval(ballX, ballY, 20, 20);
                g.drawString("Player 1: " + player1Score, 10, 10);
                g.drawString("Player 2: " + player2Score, 500, 10);
        }

        public void updateGameState(String message) {
                String[] parts = message.split(" ");
                if (parts.length > 3) {
                        player1Y = Integer.parseInt(parts[0].split(":")[1]);
                        player2Y = Integer.parseInt(parts[1].split(":")[1]);
                        String[] ballCoords = parts[2].split(":")[1].split(",");
                        if (ballCoords.length > 1) {
                                ballX = Integer.parseInt(ballCoords[0]);
                                ballY = Integer.parseInt(ballCoords[1]);
                        }
                        String[] scores = parts[3].split(":")[1].split(",");
                        if (scores.length > 1) {
                                player1Score = Integer.parseInt(scores[0]);
                                player2Score = Integer.parseInt(scores[1]);
                        }
                }
                jPanel1.repaint();
        }

        public void updateStatus(String message) {
                SwingUtilities.invokeLater(() -> {
                        statusLabel.setText(message);
                });
        }

        private void initComponents() {
                jLabel1 = new javax.swing.JLabel();
                jTextField1 = new javax.swing.JTextField();
                jButton1 = new javax.swing.JButton();
                jButton2 = new javax.swing.JButton();
                jPanel1 = new JPanel() {
                        public void paintComponent(Graphics g) {
                                super.paintComponent(g);
                                atualizaPainel(g);
                        }
                };
                statusLabel = new javax.swing.JLabel();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                jLabel1.setText("Porta do servidor TCP:");
                jTextField1.setText("1234");
                jButton1.setText("Iniciar");
                jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
                jButton2.setText("Sair");
                jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));
                jPanel1.setBackground(new java.awt.Color(0, 0, 0));
                jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 601, Short.MAX_VALUE));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 401, Short.MAX_VALUE));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                601,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addComponent(jLabel1)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jTextField1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                76,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jButton1)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jButton2))
                                                                                .addComponent(statusLabel))
                                                                .addContainerGap(18, Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(5, 5, 5)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel1)
                                                                                .addComponent(jTextField1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jButton1)
                                                                                .addComponent(jButton2))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                401,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(statusLabel)
                                                                .addContainerGap(23, Short.MAX_VALUE)));
                pack();
        }

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
                String porta = jTextField1.getText();
                int port = Integer.parseInt(porta);
                (new PongServer(port, this)).start();
        }

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
        }

        public static void main(String[] args) {
                java.awt.EventQueue.invokeLater(() -> new PongServerForm().setVisible(true));
        }

        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JLabel statusLabel;
}
