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
    private PongServerForm form;

    public PongServerThread(Socket socket, PongServer server, PongServerForm form) {
        this.socket = socket;
        this.form = form;
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
                                form.player1Y -= 3;
                                break;
                            case KeyEvent.VK_DOWN:
                                form.player1Y += 3;
                                break;
                        }
                        break;
                    case "player2":
                        switch (code) {
                            case KeyEvent.VK_UP:
                                form.player2Y -= 3;
                                break;
                            case KeyEvent.VK_DOWN:
                                form.player2Y += 3;
                                break;
                        }
                        break;
                    default:
                        System.out.println("Player desconhecido: " + player);
                        continue;
                }
                form.repaint();
                sendMessage(message);
            }
            socket.close();
        } catch (IOException ex) {
            System.out.println("Erro na thread do servidor: " + ex.getMessage());
        }
    }

    synchronized void sendMessage(String message) {
        output.println(message);
    }
}
