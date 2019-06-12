package com.github.jprnp.dsc20191.chatprivado.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
		//Receber informações de conexão ao servidor
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informar IP do servidor:");
        String ip = scanner.nextLine();
        System.out.println("Informar a porta do servidor:");
        int porta = Integer.parseInt(scanner.nextLine());
        System.out.println("Informar nickname");
        String nick = scanner.nextLine();

        try {
			//Realiza conexão ao servidor
            Socket client = new Socket(ip, porta);
			//Captura instância p/ envio de mensagens ao servidor
            PrintStream stream = new PrintStream(client.getOutputStream());
			//Enviar nickname ao servidor
            stream.println(nick);	

			//Thread p/ recebimento de mensagens do servidor
			new Thread(new Receiver(client)).start();

			//Envia mensagens ao servidor até que a conexão seja interrompida
            while(true) {
                stream.println(scanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Conexão com o servidor não alcançada.");
        }
    }
}
