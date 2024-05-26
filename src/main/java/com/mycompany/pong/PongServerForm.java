package com.mycompany.pong;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PongServerForm extends javax.swing.JFrame {

        int player1Y = 150, player2Y = 150, ballX = 290, ballY = 190, ballDirX = 3, ballDirY = 3;
        int playerHeight = 50, playerWidth = 10, ballSize = 20;

        public PongServerForm() {
                initComponents();
        }

        public void atualizaPainel(Graphics g) {
                g.setColor(Color.green);
                g.fillRect(10, player1Y, playerWidth, playerHeight);
                g.fillRect(580, player2Y, playerWidth, playerHeight);
                g.fillOval(ballX, ballY, ballSize, ballSize);
        }

        // New method to update status
        public void updateStatus(String message) {
                SwingUtilities.invokeLater(() -> {
                        // Assuming there's a JLabel named statusLabel to display the status
                        statusLabel.setText(message);
                });
        }

        // Existing code
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
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
                statusLabel = new javax.swing.JLabel(); // Add a JLabel for status

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

                // Set up the layout including the new statusLabel
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
                                                                                .addComponent(statusLabel)) // Add
                                                                                                            // statusLabel
                                                                                                            // to the
                                                                                                            // layout
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
                                                                .addComponent(statusLabel) // Add statusLabel to the
                                                                                           // layout
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

        public static void main(String args[]) {
                java.awt.EventQueue.invokeLater(() -> new PongServerForm().setVisible(true));
        }

        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JLabel statusLabel; // Declare statusLabel
}
