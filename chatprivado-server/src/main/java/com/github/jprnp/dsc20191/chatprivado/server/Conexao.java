package com.github.jprnp.dsc20191.chatprivado.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe responsável pelo recebimento e envio de mensagens com os clientes - Implementa Runnable
 */
public class Conexao implements Runnable {

    /**
     * Dois pontos
     */
	public static final String COLON = ":";
    /**
     * Vírgula
     */
    public static final String COMMA = ",";
    /**
     * Comando: Enviar para todos (*)
     */
	public static final String SEND_TO_ALL = "*";
    /**
     * Prefixo para comandos
     */
    public static final String COMMAND = "command";
    /**
     * Comando: Exibir usuários conectados
     */
    public static final String COMMAND_FRIENDS = "friends";
    /**
     * Comando: Encerrar conexão
     */
    public static final String COMMAND_REMOVE = "remove";

    /**
     * Instância Socket de conexão com um cliente
     */
    private Socket client;
    /**
     * Objeto p/ recebimento de mensagens dos clientes
     */
    private Scanner entrada;
    /**
     * Objeto p/ envio de mensagens aos clientes
     */
    private PrintStream saida;
    /**
     * Apelido do usuário conectado
     */
    private String nickName;

    /**
     * Construtor público
     * @param client Instância Socket de conexão com um cliente
     * @throws IOException Caso a conexão seja quebrada
     */
    public Conexao(Socket client) throws IOException {
        this.client = client;
        this.entrada = new Scanner(this.client.getInputStream());
        this.saida = new PrintStream(this.client.getOutputStream());
        this.nickName = this.entrada.nextLine();
        this.saida.println("Bem vindo ao WhatsUFG");
    }

    /**
     * GET - nickName
     * @return Apelido do usuário
     */
    public String getNickName() {
        return this.nickName;
    }

    /**
     * GET - saida
     * @return Objeto p/ envio de mensagens ao cliente conectado
     */
    public PrintStream getSaida() {
        return this.saida;
    }

    /**
     * Execução da Thread
     */
    public void run() {
        String linha;

        // Enquanto houver conexão, receber e interpretar mensagens
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

    /**
     * Interpretar mensagens/comandos vindos dos clientes
     * @param linha Mensagem recebida do cliente
     * @throws IOException Caso a conexão com o cliente seja quebrada
     */
    private void parseLine(String linha) throws IOException {
        String split[];
		
		split = linha.split(Conexao.COLON);

		//Caso não haja ":", executar broadcast
        if (!linha.contains(Conexao.COLON)) {
            this.sendToAll(linha);
            return;
        }        

        //Caso exista o comando broadcast, executá-lo (*:mensagem)
		if (split[0].equals(Conexao.SEND_TO_ALL) && split.length > 1) {
			this.sendToAll(split[1]);
			return;
		}

		//Caso exista o prefixo "command" para comandos, executar o comando após ":"
        if (split[0].equals(Conexao.COMMAND)) {
            this.executeCommand(split[1]);
            return;
        }

        //Caso exista ":" e não seja um comando, interpretar como uma lista de usuários p/ envio privado
        for (String user : split[0].split(Conexao.COMMA)) {
            this.sendPrivate(user, split[1]);
        }
    }

    /**
     * Executar comando recebido após "command:"
     * @param command Comando a ser executado
     * @throws IOException Caso a conexão com o cliente seja quebrada
     */
    private void executeCommand(String command) throws IOException {
        switch (command) {
            case Conexao.COMMAND_FRIENDS:
                this.friendList();
                break;
            case Conexao.COMMAND_REMOVE:
                Server.conexoes.remove(this);
                this.client.close();
                break;
        }
    }

    /**
     * Comando: Listar usuários conectados
     */
    private void friendList() {
        String friends;

        List<String> users = Server.conexoes.stream()
                .map(Conexao::getNickName)
                .filter(nick -> !nick.equals(this.getNickName()))
                .collect(Collectors.toList());

        friends = "users:" + String.join(",", users);
        this.getSaida().println(friends);
    }

    /**
     * Enviar mensagem em modo broadcast
     * @param mensagem Mensagem a ser enviada
     */
    private void sendToAll(String mensagem) {
        for (Conexao c : Server.conexoes) {
            if (!c.getNickName().equals(this.getNickName())) {
                c.getSaida().println(this.getNickName() + ": " + mensagem);
            }
        }
    }

    /**
     * Enviar mensagem em modo privado
     * @param nickname Nickname do destinatário
     * @param mensagem Mensagem a ser enviada
     */
    private void sendPrivate(String nickname, String mensagem) {
        //Retirar espaço em branco do início da mensagem
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
