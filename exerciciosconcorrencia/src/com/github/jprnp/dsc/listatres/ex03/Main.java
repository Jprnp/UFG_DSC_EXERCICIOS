package com.github.jprnp.dsc.listatres.ex03;

import java.util.Arrays;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        Lebre[] lebres = new Lebre[5];
        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            lebres[i] = new Lebre(i + 1);
            threads[i] = new Thread(lebres[i]);
            threads[i].start();
        }

        for (int i = 0; i < 5; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(4);
            }
        }

        Arrays.sort(lebres, Comparator.comparingLong(Lebre::getTempo));

        for (int i = 1; i <= 5; i++) {
            Lebre lebre = lebres[i - 1];
            System.out.println("Lebre " + lebre.getId() + " finalizou em " + i + "º lugar com " + lebre.getSaltos()
                    + " saltos.");
        }
    }
}
