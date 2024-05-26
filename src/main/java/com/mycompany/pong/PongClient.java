package com.mycompany.pong;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.KeyEvent;

public class PongClient extends javax.swing.JFrame {

    private TCPClientUtil socket;
    private String player;

    public PongClient(String player) {
        initComponents();
        this.player = player;
    }

    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel1.setText("Servidor IP:");

        jLabel2.setText("Porta do servidor TCP:");

        jTextField2.setText("1234");

        jButton1.setText("Conectar");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        jButton2.setText("Sair");
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 600, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 400, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup().addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 189,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup().addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 77,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)
                                .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String serverIP = jTextField1.getText();
        String port = jTextField2.getText();
        int serverPort = Integer.parseInt(port);
        socket = new TCPClientUtil(serverIP, serverPort);
        if (socket.connectSocket()) {
            JOptionPane.showMessageDialog(null, "Conectado ao servidor!");
            (new Thread(() -> {
                String message;
                while ((message = socket.receiveMessage()) != null) {
                    String[] parts = message.split(":");
                    String player = parts[0];
                    int code = Integer.parseInt(parts[1]);
                    switch (player) {
                        case "player1":
                            switch (code) {
                                case KeyEvent.VK_UP:
                                    jPanel1.getGraphics().clearRect(10, 0, 10, 300);
                                    jPanel1.getGraphics().setColor(Color.green);
                                    jPanel1.getGraphics().fillRect(10, code, 10, 50);
                                    break;
                                case KeyEvent.VK_DOWN:
                                    jPanel1.getGraphics().clearRect(10, 0, 10, 300);
                                    jPanel1.getGraphics().setColor(Color.green);
                                    jPanel1.getGraphics().fillRect(10, code, 10, 50);
                                    break;
                            }
                            break;
                        case "player2":
                            switch (code) {
                                case KeyEvent.VK_UP:
                                    jPanel1.getGraphics().clearRect(580, 0, 10, 300);
                                    jPanel1.getGraphics().setColor(Color.green);
                                    jPanel1.getGraphics().fillRect(580, code, 10, 50);
                                    break;
                                case KeyEvent.VK_DOWN:
                                    jPanel1.getGraphics().clearRect(580, 0, 10, 300);
                                    jPanel1.getGraphics().setColor(Color.green);
                                    jPanel1.getGraphics().fillRect(580, code, 10, 50);
                                    break;
                            }
                            break;
                    }
                }
            })).start();
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao servidor.");
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void formKeyPressed(java.awt.event.KeyEvent evt) {
        int code = evt.getKeyCode();
        socket.sendMessage(player + ":" + code);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new PongClient("player1").setVisible(true));
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
}
