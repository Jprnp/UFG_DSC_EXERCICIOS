package com.github.jprnp.dsc20191.chatprivado.client;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Receiver implements Runnable {
	
	private Socket client;
	
	private Scanner entrada;
	
	public Receiver(Socket client) throws IOException {
		this.client = client;
		this.entrada = new Scanner(this.client.getInputStream());
	}

	public void run() {
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