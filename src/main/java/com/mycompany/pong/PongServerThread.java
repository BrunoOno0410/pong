package com.mycompany.pong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.KeyEvent;

public class PongServerThread extends Thread {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private PongServer server;
    private PongServerForm form;
    private final int paddleSpeed = 8; // Aumentar a velocidade dos paddles

    private String player; // Identificar qual jogador é

    public PongServerThread(Socket socket, PongServer server, PongServerForm form) {
        this.socket = socket;
        this.form = form;
        this.server = server;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            String message;
            while (true) {
                if (socket.isConnected() && input != null) {
                    message = input.readLine();
                    System.out.println("Thread [" + this.toString() + "] recebeu mensagem: " + message);
                } else {
                    break;
                }
                if (message == null || message.equals("")) {
                    break;
                }

                // Verificação de validade da mensagem
                String[] parts = message.split(":");
                if (parts.length != 2) {
                    System.out.println("Formato de mensagem inválido: " + message);
                    continue;
                }

                int code;
                try {
                    code = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Código inválido na mensagem: " + message);
                    continue;
                }

                // Atualizar posição do jogador baseado na tecla pressionada
                if ("player1".equals(player)) {
                    switch (code) {
                        case KeyEvent.VK_UP:
                            server.player1Y = Math.max(0, server.player1Y - paddleSpeed);
                            break;
                        case KeyEvent.VK_DOWN:
                            server.player1Y = Math.min(350, server.player1Y + paddleSpeed);
                            break;
                    }
                } else if ("player2".equals(player)) {
                    switch (code) {
                        case KeyEvent.VK_UP:
                            server.player2Y = Math.max(0, server.player2Y - paddleSpeed);
                            break;
                        case KeyEvent.VK_DOWN:
                            server.player2Y = Math.min(350, server.player2Y + paddleSpeed);
                            break;
                    }
                }
                form.repaint();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }
}
