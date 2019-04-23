package com.github.jprnp.dsc.listatres.ex01;

public class Main {

    public static final long LOWONE = 1_000_000;
    public static final long HIGHONE = 30_000_000;
    public static final long LOWTWO = 90_000_000;
    public static final long HIGHTWO = 120_000_000;

    public static void main(String[] args) {
        Contador c1 = new Contador(LOWONE, HIGHONE);
        Contador c2 = new Contador(LOWTWO, HIGHTWO);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);

        long tempoInicio = System.currentTimeMillis();
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(4);
        }

        int qtdPrimos = c1.getCount() + c2.getCount();
        long tempoTot = System.currentTimeMillis() - tempoInicio;

        System.out.println("Quantidade total de números primos: " + qtdPrimos);
        System.out.println("Tempo total gasto (ms): " + tempoTot);
    }
}
