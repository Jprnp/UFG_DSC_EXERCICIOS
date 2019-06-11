package com.github.jprnp.dsc20191.chatprivado.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Conexao implements Runnable {
	
	public static final String COLON = ":";

    public static final String COMMA = ",";

    public static final String COMMAND = "command";

    public static final String COMMAND_FRIENDS = "friends";

    public static final String COMMAND_REMOVE = "remove";

    private Socket client;
	
    private Scanner entrada;
	
    private PrintStream saida;
	
    private String nickName;

    public Conexao(Socket client) throws IOException {
        this.client = client;
        this.entrada = new Scanner(this.client.getInputStream());
        this.saida = new PrintStream(this.client.getOutputStream());
        this.nickName = this.entrada.nextLine();
        this.saida.println("Bem vindo ao WhatsUFG");
    }

    public String getNickName() {
        return this.nickName;
    }

    public PrintStream getSaida() {
        return this.saida;
    }

    public void run() {
        String linha;

        while (true) {
            try {
                linha = entrada.nextLine();
                this.parseLine(linha);
            } catch (NoSuchElementException e) {
                break;
            } catch (IOException e) {
                break;
            }
        }
    }

    private void parseLine(String linha) throws IOException {
        String split[];

        if (!linha.contains(Conexao.COLON)) {
            this.sendToAll(linha);
            return;
        }

        split = linha.split(Conexao.COLON);

        if (split[0].equals(Conexao.COMMAND)) {
            this.executeCommand(split[1]);
            return;
        }

        for (String user : split[0].split(Conexao.COMMA)) {
            this.sendPrivate(user, split[1]);
        }
    }

    private void executeCommand(String command) throws IOException {
        switch (command) {
            case Conexao.COMMAND_FRIENDS:
                this.friendList();
                break;
            case Conexao.COMMAND_REMOVE:
                this.client.close();
                break;
        }
    }

    private void friendList() {
        String friends;

        List<String> users = Server.conexoes.stream()
                .map(Conexao::getNickName)
                .filter(nick -> !nick.equals(this.getNickName()))
                .collect(Collectors.toList());

        friends = "users:" + String.join(",", users);
        this.getSaida().println(friends);
    }

    private void sendToAll(String mensagem) {
        for (Conexao c : Server.conexoes) {
            if (!c.getNickName().equals(this.getNickName())) {
                c.getSaida().println(this.getNickName() + ": " + mensagem);
            }
        }
    }

    private void sendPrivate(String nickname, String mensagem) {
        if (mensagem.startsWith(" ")) {
            mensagem = mensagem.substring(1);
        }

        for (Conexao c : Server.conexoes) {
            if (c.getNickName().toLowerCase().equals(nickname.toLowerCase())) {
                c.getSaida().println(this.getNickName() + ": " + mensagem);
                break;
            }
        }
    }
}
