package com.github.jprnp.dsc.listatres.ex03;

import java.util.concurrent.ThreadLocalRandom;

public class Lebre implements Runnable {

    private int saltos = 0;
    private long tempoDecorrido;
    private int id;

    public Lebre(int id) {
        this.id = id;
    }

    @Override
    public void run() {

        short distancia = 0;
        int pulo;

        long tempoInicio = System.currentTimeMillis();

        while (distancia < 20) {
            pulo = ThreadLocalRandom.current().nextInt(1, 4);
            distancia += pulo;
            System.out.println("Lebre " + this.id + " saltou " + pulo + "m.");
            this.saltos++;
            Thread.yield();
        }

        this.tempoDecorrido = System.currentTimeMillis() - tempoInicio;
    }

    public int getSaltos() {
        return this.saltos;
    }

    public int getId() {
        return this.id;
    }

    public long getTempo() {
        return this.tempoDecorrido;
    }
}
