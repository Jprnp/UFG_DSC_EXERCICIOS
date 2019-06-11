package com.github.jprnp.dsc.quebrasenhas;

public class quebraSenhaParalelo {

    public static final String HASH1 = "17a0a00212dde12b063af7dc22fdf02b";
    public static final String HASH2 = "75abfe3020804dd73a2a6040da9df96c";
    public static final String HASH3 = "c77aeec24015ad7e6e0b1db9d9deed68";

    public static long tempoInicio;

    public static void main(String[] args) {
        tempoInicio = System.currentTimeMillis();

        ThreadGroup g1 = new ThreadGroup("grupo1");
        ThreadGroup g2 = new ThreadGroup("grupo2");
        ThreadGroup g3 = new ThreadGroup("grupo3");

        for (int i = 1; i <= 3; i++) {
            (new Thread(g1, new Quebrador(i, 1, HASH1))).start();
        }

        for (int i = 1; i <= 3; i++) {
            (new Thread(g2, new Quebrador(i, 2, HASH2))).start();
        }

        for (int i = 1; i <= 3; i++) {
            (new Thread(g3, new Quebrador(i, 3, HASH3))).start();
        }
    }

    private static class Quebrador implements Runnable {

        public static final String CARACTERES1 = "abcdefghijklmnopqrstuvwxyz0123456789";
        public static final String CARACTERES2 = "9876543210zyxwvutsrwponmlkjihgfedcba";
        public static final String CARACTERES3 = "vwxyztu9876543210jklmnopqrsabcdefghi";

        private int variacao;
        private int numHash;
        private String hash;

        public Quebrador(int variacao, int numHash, String hash) {
            this.variacao = variacao;
            this.numHash = numHash;
            this.hash = hash;
        }

        private void quebra(String hash) {
            String caracteres = "";

            switch (this.variacao) {
                case 1:
                    caracteres = CARACTERES1;
                    break;
                case 2:
                    caracteres = CARACTERES2;
                    break;
                case 3:
                    caracteres = CARACTERES3;
                    break;
            }

            char[] charArray = caracteres.toCharArray();
            String senha = "";

            for (char a : charArray) {
                for (char b : charArray) {
                    for (char c : charArray) {
                        for (char d : charArray) {
                            for (char e : charArray) {
                                senha = String.valueOf(a) + b + c + d + e;

                                if (Utilitario.md5(senha).equals(hash)) {
                                    System.out.println("Senha " + this.numHash + ": \"" + senha + "\"");
                                    long tempoTotal = System.currentTimeMillis() - quebraSenhaParalelo.tempoInicio;
                                    System.out.println("Tempo p/ quebrar: " + tempoTotal);
                                    Thread.currentThread().getThreadGroup().stop();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void run() {
            this.quebra(hash);
        }
    }
}
