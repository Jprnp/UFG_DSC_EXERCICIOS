package com.github.jprnp.dsc.quebrasenhasnew;

import java.util.concurrent.Callable;

public class Quebrador implements Callable {

    public static final String CARACTERES1 = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final String CARACTERES2 = "9876543210zyxwvutsrwponmlkjihgfedcba";
    public static final String CARACTERES3 = "vwxyztu9876543210jklmnopqrsabcdefghi";

    private int variation;
    private String hash;

    public Quebrador(int variation, String hash) {
        this.variation = variation;
        this.hash = hash;
    }

    public String call() throws Exception {
        String senha = "";
        String charSequence = this.determineCharSequence(this.variation);
        char[] charArray = charSequence.toCharArray();

        for (char a : charArray) {
            for (char b : charArray) {
                for (char c : charArray) {
                    for (char d : charArray) {
                        for (char e : charArray) {
                            if (Thread.currentThread().isInterrupted()) {
                                return null;
                            }

                            senha = String.valueOf(a) + b + c + d + e;

                            if (Util.md5(senha).equals(hash)) {
                               return senha;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    private String determineCharSequence(int variation) {
        switch (variation) {
            case 1:
                return this.CARACTERES1;
            case 2:
                return this.CARACTERES2;
            case 3:
                return this.CARACTERES3;
        }

        return null;
    }
}
