package com.github.jprnp.dsc.quebrasenhas;

public class quebraSenhaSequencial {

    public static final String HASH1 = "17a0a00212dde12b063af7dc22fdf02b";
    public static final String HASH2 = "75abfe3020804dd73a2a6040da9df96c";
    public static final String HASH3 = "c77aeec24015ad7e6e0b1db9d9deed68";
    public static final String CARACTERES = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static void main(String[] args) {
        long tempoIni = System.currentTimeMillis();

        // Hash 1
        System.out.println("Quebrando a senha 1...");
        System.out.println("Senha 1: \"" + quebra(HASH1) + "\"");;

        // Hash 2
        System.out.println("Quebrando a senha 2...");
        System.out.println("Senha 2: \"" + quebra(HASH2) + "\"");;

        // Hash 3
        System.out.println("Quebrando a senha 3...");
        System.out.println("Senha 3: \"" + quebra(HASH3) + "\"");;

        long tempoTot = System.currentTimeMillis() - tempoIni;
        System.out.println("Tempo total: " + tempoTot);
    }

    public static String quebra(String hash) {
        char[] charArray = CARACTERES.toCharArray();
        String senha = "";

        for (char a : charArray) {
            for (char b : charArray) {
                for (char c : charArray) {
                    for (char d : charArray) {
                        for (char e : charArray) {
                            senha = String.valueOf(a) + b + c + d + e;

                            if (Utilitario.md5(senha).equals(hash)) {
                                return senha;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
}
