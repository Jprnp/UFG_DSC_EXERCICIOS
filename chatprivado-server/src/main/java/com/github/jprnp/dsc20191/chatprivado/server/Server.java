package com.github.jprnp.dsc20191.chatprivado.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

    /**
     * Porta em que o servidor será executado
     */
    public static final int PORT = 12345;
    /**
     * Objeto ServerSocket para receber conexões
     */
    public static ServerSocket serverSocket;
    /**
     * Vector que armazena as conexões recebidas
     */
    public static Vector<Conexao> conexoes = new Vector<Conexao>();

    public static void main(String[] args) {
        try {
            //Inicia Servidor
            serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor rodando na porta " + PORT);
        } catch (IOException e) {
            System.out.println("Erro ao criar server socker");
        }

        while (true) {
            try {
                //Aguarda por novas conexões
                Socket client = serverSocket.accept();
                System.out.println("Cliente conectado -> host " + client.getInetAddress().getHostName());
                Conexao conexao = new Conexao(client);
                conexoes.add(conexao);
                //Inicia thread responsável pelo recebimento e envio de mensagens de cada cliente
                new Thread(conexao).start();
            } catch (IOException e) {
                break;
            }
        }
    }
}
