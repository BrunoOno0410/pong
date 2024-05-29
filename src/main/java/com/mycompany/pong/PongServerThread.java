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

    public PongServerThread(Socket socket, PongServer server, PongServerForm form) {
        this.socket = socket;
        this.form = form;
        this.server = server;
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

                String player = parts[0];
                int code;
                try {
                    code = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("Código inválido na mensagem: " + message);
                    continue;
                }

                switch (player) {
                    case "player1":
                        switch (code) {
                            case KeyEvent.VK_UP:
                                server.player1Y = Math.max(0, server.player1Y - paddleSpeed);
                                break;
                            case KeyEvent.VK_DOWN:
                                server.player1Y = Math.min(350, server.player1Y + paddleSpeed);
                                break;
                        }
                        break;
                    case "player2":
                        switch (code) {
                            case KeyEvent.VK_UP:
                                server.player2Y = Math.max(0, server.player2Y - paddleSpeed);
                                break;
                            case KeyEvent.VK_DOWN:
                                server.player2Y = Math.min(350, server.player2Y + paddleSpeed);
                                break;
                        }
                        break;
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
