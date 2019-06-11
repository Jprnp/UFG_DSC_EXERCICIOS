package com.github.jprnp.dsc20191.chatprivado.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informar IP do servidor:");
        String ip = scanner.nextLine();
        System.out.println("Informar a porta do servidor:");
        int porta = Integer.parseInt(scanner.nextLine());
        System.out.println("Informar nickname");
        String nick = scanner.nextLine();

        try {
            Socket client = new Socket(ip, porta);
            PrintStream stream = new PrintStream(client.getOutputStream());
            stream.println(nick);	

			new Thread(new Receiver(client)).start();

            while(true) {
                stream.println(scanner.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Conexão com o servidor não alcançada.");
        }
    }
}
