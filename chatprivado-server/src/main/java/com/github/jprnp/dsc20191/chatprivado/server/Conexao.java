package com.github.jprnp.dsc20191.chatprivado.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Conexao implements Runnable {

    private Socket client;
    private Scanner entrada;
    private String nickName;

    public Conexao(Socket client) throws IOException {
        this.client = client;
        this.entrada = new Scanner(this.client.getInputStream());
        this.nickName = this.entrada.nextLine();
    }

    public String getNickName() {
        return this.nickName;
    }

    public void run() {
        String comando;

        while(true) {
            comando = entrada.nextLine();
            System.out.println(this.nickName + ": " + comando);
        }
    }
}
