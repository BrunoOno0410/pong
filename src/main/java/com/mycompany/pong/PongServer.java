package com.mycompany.pong;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PongServer extends Thread {

    private int port;
    private PongServerForm form;

    public PongServer(int port, PongServerForm form) {
        this.port = port;
        this.form = form;
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println(this.getClass().getSimpleName() + " rodando na porta: " + server.getLocalPort());
            while (true) {
                Socket socket = server.accept();
                new PongServerThread(socket, form).start();
            }
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o servidor: " + ex.getMessage());
        }
    }
}
