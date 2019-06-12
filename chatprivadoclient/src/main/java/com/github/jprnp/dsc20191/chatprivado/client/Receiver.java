package com.github.jprnp.dsc20191.chatprivado.client;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Classe responsável pela recepção de mensagens do servidor de chat - Implementa Runnable
 */
public class Receiver implements Runnable {

	/**
	 * Instância Socket de conexão com o servidor de chat
	 */
	private Socket client;
	/**
	 * Objeto para recepção de mensagens do servidor de chat
	 */
	private Scanner entrada;

	/**
	 * Construtor público
	 * @param client Instância Socket de conexão com o servidor de chat
	 * @throws IOException Caso a conexão com servidor esteja quebrada
	 */
	public Receiver(Socket client) throws IOException {
		this.client = client;
		this.entrada = new Scanner(this.client.getInputStream());
	}

	/**
	 * Execução da Thread
	 */
	public void run() {
		//Enquanto houver conexão, imprimir mensagens recebidas na tela
		while(true) {
			try {
				System.out.println(this.entrada.nextLine());
			} catch (NoSuchElementException e) {
				System.out.println("Conexão com o servidor encerrada.");
				break;
			}
		}
	}
}