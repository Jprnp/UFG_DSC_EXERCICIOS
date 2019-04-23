package com.github.jprnp.dsc.listatres.ex02;

public class Finder implements Runnable {

    private int lastIndex;
    private int[] Indices;
    private int[] A;
    private int numberToFind;
    private boolean found = false;

    public Finder(int lastIndex, int[] A, int numberToFind) {
        this.lastIndex = lastIndex;
        this.A = A;
        this.numberToFind = numberToFind;
    }

    @Override
    public void run() {
        int count = 0;
        int found = 0;
        int[] IndicesProv = new int[A.length];

        for (int i = 0; i < this.A.length; i++) {
            if (this.A[i] == this.numberToFind) {
                IndicesProv[count++] = lastIndex + i;
                found++;
            }
        }

        this.Indices = new int[found];
        for (int i = 0; i < found; i++) {
            this.Indices[i] = IndicesProv[i];
        }
    }

    public int[] getIndices() {
        return this.Indices;
    }
}
