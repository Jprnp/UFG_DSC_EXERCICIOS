package com.github.jprnp.dsc.corridasapos;

import java.util.concurrent.ThreadLocalRandom;

public class Sapo implements Runnable {

    private String id;
    private int distanciaCorrida;
    private int distanciaPercorrida;
    private int distanciaDoPulo;
    private int tempoEmSegundos;
    private static final int DEMORA_MIN = 500;
    private static final int DEMORA_MAX = 1500;
    private static final int DISTANCIA_MIN = 40;
    private static final int DISTANCIA_MAX = 100;
    private static final int PITSTOP_MIN = 2500;
    private static final int PITSTOP_MAX = 3500;

    public Sapo(String id, int distanciaCorrida) {
        this.id = id;
        this.distanciaCorrida = distanciaCorrida;
    }

    @Override
    public void run() {
        long tempoInicio = System.currentTimeMillis();
        while (!this.isFinished()) {
            try {
                this.jump();
                if (this.isAtHalf()) {
                    this.pitStop();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        this.tempoEmSegundos = (int) Math.round((System.currentTimeMillis() - tempoInicio) / 60.0);
        System.out.println(this.getId() + " finalizou a corrida.");
    }

    private void jump() throws InterruptedException {
        this.distanciaDoPulo = ThreadLocalRandom.current().nextInt(DISTANCIA_MIN, DISTANCIA_MAX + 1);
        System.out.println(this.getId() + " saltou " + this.distanciaDoPulo + " cm.");
        Thread.sleep(ThreadLocalRandom.current().nextInt(DEMORA_MIN, DEMORA_MAX + 1));
        this.distanciaPercorrida += this.distanciaDoPulo;
    }

    private boolean isFinished() {
        return this.distanciaPercorrida >= this.distanciaCorrida;
    }

    private boolean isAtHalf() {
        return this.distanciaPercorrida >= this.distanciaCorrida / 2;
    }

    private void pitStop() throws InterruptedException {
        Thread.sleep(ThreadLocalRandom.current().nextInt(PITSTOP_MIN, PITSTOP_MAX + 1));
    }

    public String getId() {
        return this.id;
    }

    public int getTempoEmSegundos() {
        return this.tempoEmSegundos;
    }
}
