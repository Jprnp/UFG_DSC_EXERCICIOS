package com.github.jprnp.dsc.listatres.ex01;

public class Contador implements Runnable {

    private long low;
    private long high;
    private int count;

    public Contador(long low, long high) {
        this.low = low;
        this.high = high;
    }

    @Override
    public void run() {
        for (long i = this.low; i <= this.high; i++) {
            if (this.isPrimo(i)) {
                System.out.println(i);
                this.count++;
            }
        }
    }

    private boolean isPrimo(long num) {
        if (num == 2) {
            return true;
        }

        if (num % 2 == 0) {
            return false;
        }

        for (long i = 3; i * i <= num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    public int getCount() {
        return count;
    }
}
