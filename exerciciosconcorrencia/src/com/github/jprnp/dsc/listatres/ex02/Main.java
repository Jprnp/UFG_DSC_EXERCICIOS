package com.github.jprnp.dsc.listatres.ex02;

public class Main {

    public static void main(String[] args) {
        int[] Array = new int[70];
        int count =0;

        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 10; i++) {
                Array[count] = i;
                count++;
            }
        }

        int[] Indices = parallelSearch(1, Array, 3);
        System.out.println("Índices onde foi encontrado:");
        for (int i : Indices) {
            System.out.println(i);
        }
    }

    public static int[] parallelSearch(int x, int[] A, int numThreads) {
        int length = A.length / numThreads;
        int remainder = A.length % numThreads;
        int lastIndex = 0;
        int sliceLength;
        int slice[];
        int count;

        Finder[] finders = new Finder[numThreads];
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            sliceLength = length;
            if (remainder > 0 && i == numThreads - 1) {
                sliceLength++;
            }
            slice = new int[sliceLength];

            count = 0;
            for (int j = lastIndex; j < sliceLength; j++) {
                slice[count++] = A[j];
            }

            finders[i] = new Finder(lastIndex, slice, x);
            threads[i] = new Thread(finders[i]);
            threads[i].start();

            lastIndex += length - 1;
        }

        int lengthRetorno = 0;
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                lengthRetorno += finders[i].getIndices().length;
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(4);
            }
        }

        int[] Indices = new int[lengthRetorno];
        count = 0;
        for (int i = 0; i < numThreads; i ++) {
            for (int j : finders[i].getIndices()) {
                Indices[count++] = j;
            }
        }

        return Indices;
    }
}
