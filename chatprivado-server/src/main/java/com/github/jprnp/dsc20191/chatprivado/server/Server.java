package com.github.jprnp.dsc20191.chatprivado.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

    public static final int PORT = 12345;

    public static ServerSocket serverSocket;

    public static Vector<Conexao> conexoes = new Vector<Conexao>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor rodando na porta " + PORT );
        } catch (IOException e) {
            System.out.println("Erro ao criar server socker");
        }

        while(true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Cliente conectado -> host " + client.getInetAddress().getHostName());
                Conexao conexao = new Conexao(client);
                conexoes.add(conexao);
                new Thread(conexao).start();
            } catch (IOException e) {
                break;
            }
        }
    }
}
