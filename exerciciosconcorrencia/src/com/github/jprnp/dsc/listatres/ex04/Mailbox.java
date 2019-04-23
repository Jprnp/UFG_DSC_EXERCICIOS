package com.github.jprnp.dsc.listatres.ex04;

public class Mailbox {

    private String mensagem;

    public synchronized void storeMessage(String mensagem) throws InterruptedException {
        if(this.mensagem == null) {
            this.mensagem = mensagem;
            this.notifyAll();
        } else {
            this.wait();
        }

    }

    public synchronized void retrieveMessage(String nome) throws InterruptedException {
        if(this.mensagem == null) {
            this.wait();
        } else {
            System.out.println(nome + " consumindo: " + this.mensagem);
            this.mensagem = null;
            this.notifyAll();
        }
    }
}
