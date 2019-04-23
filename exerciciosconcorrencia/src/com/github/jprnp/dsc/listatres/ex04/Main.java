package com.github.jprnp.dsc.listatres.ex04;

public class Main {

    public static void main(String[] args){
        Mailbox mailbox = new Mailbox();

        Producer producer = new Producer(mailbox, "Produtor 1");
        Producer producer2 = new Producer(mailbox, "Produtor 2");
        Consumer consumer = new Consumer(mailbox, "Consumidor 1");

        producer.start();
        producer2.start();
        consumer.start();
    }
}