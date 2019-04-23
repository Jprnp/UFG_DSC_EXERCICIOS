package com.github.jprnp.dsc.corridasapos;

import java.util.Arrays;
import java.util.Comparator;

public class PistaCorridaSapos {

    public static final int NUM_SAPOS = 4;
    public static final int DISTANCIA_CORRIDA = 350;

    public static void main(String[] args) {
        Sapo[] sapos = new Sapo[NUM_SAPOS];
        Thread[] corredores = new Thread[NUM_SAPOS];

        // Inicia a corrida
        for (int i = 0; i < NUM_SAPOS; i++) {
            sapos[i] = new Sapo("SAPO_" + i, DISTANCIA_CORRIDA);
            corredores[i] = new Thread(sapos[i]);
            corredores[i].start();
        }

        // Aguarda a chegada de todos
        for (int i = 0; i < NUM_SAPOS; i++) {
            try {
                corredores[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(4);
            }
        }

        // Imprime ordem de chegada
        Arrays.sort(sapos, Comparator.comparingInt(Sapo::getTempoEmSegundos));

        System.out.println("\nOrdem de chegada:");
        for (int i = 0; i < NUM_SAPOS; i++) {
            System.out.println(sapos[i].getId());
        }
    }
}
