package com.github.jprnp.dsc.quebrasenhasnew;

import java.util.Vector;
import java.util.concurrent.*;

public class Main {

    public static final String HASH1 = "17a0a00212dde12b063af7dc22fdf02b";
    public static final String HASH2 = "75abfe3020804dd73a2a6040da9df96c";
    public static final String HASH3 = "c77aeec24015ad7e6e0b1db9d9deed68";

    public static long tempoInicio;

    /**
     * Para quebrar as senhas, as threads serão dividas em 3 grupos, cada grupo responsável por uma HASH
     * @param args argumentos da linha de comando
     */
    public static void main(String[] args) {
        Vector<Quebrador> tasks1 = new Vector<Quebrador>();
        Vector<Quebrador> tasks2 = new Vector<Quebrador>();
        Vector<Quebrador> tasks3 = new Vector<Quebrador>();

        tempoInicio = System.currentTimeMillis();

        ExecutorService executor1 = Executors.newCachedThreadPool();
        ExecutorService executor2 = Executors.newCachedThreadPool();
        ExecutorService executor3 = Executors.newCachedThreadPool();

        // Inicializa grupo 1
        for (int i = 1; i <= 3; i++) {
            Quebrador quebrador = new Quebrador(i, HASH1);
            tasks1.add(quebrador);
        }
        // Executa as threads através de uma Thread auxiliar que controla o retorno do grupo 1
        Future<String> future1 = executor1.submit(new Watcher(tasks1, executor1));

        // Inicializa grupo 2
        for (int i = 1; i <= 3; i++) {
            Quebrador quebrador = new Quebrador(i, HASH2);
            tasks2.add(quebrador);
        }
        // Executa as threads através de uma Thread auxiliar que controla o retorno do grupo 2
        Future<String> future2 = executor2.submit(new Watcher(tasks2, executor2));

        // Inicializa grupo 3
        for (int i = 1; i <= 3; i++) {
            Quebrador quebrador = new Quebrador(i, HASH3);
            tasks3.add(quebrador);
        }
        // Executa as threads através de uma Thread auxiliar que controla o retorno do grupo 3
        Future<String> future3 = executor3.submit(new Watcher(tasks3, executor3));

        boolean found1 = false;
        boolean found2 = false;
        boolean found3 = false;

        // Enquanto o future das Threads auxiliares não estiverem prontos, contiar rodando
        while (true) {
            try {
                if (!found1 && future1.isDone()) {
                    System.out.println("Senha 1: " + future1.get());
                    found1 = true;
                }

                if (!found2 && future2.isDone()) {
                    System.out.println("Senha 2: " + future2.get());
                    found2 = true;
                }

                if (!found3 && future3.isDone()) {
                    System.out.println("Senha 3: " + future3.get());
                    found3 = true;
                }

                if (found1 && found2 && found3) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        long tempoTotal = (System.currentTimeMillis() - tempoInicio) / 1000;
        System.out.println("Tempo total gasto: " + tempoTotal);
    }
}
