package com.github.jprnp.dsc.listatres.ex04;

public class Consumer extends Thread {

    private Mailbox mailbox;
    private String nome;

    public Consumer(Mailbox mailbox, String nome){
        this.mailbox = mailbox;
        this.nome = nome;
    }

    @Override
    public void run(){
        try {
            while(true) {
                this.mailbox.retrieveMessage(this.nome);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
