package com.github.jprnp.dsc.quebrasenhasnew;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Watcher implements Callable {

    private Vector<Quebrador> tasks;
    private ExecutorService service;
    private Vector<Future<String>> futures = new Vector<Future<String>>();

    public Watcher(Vector<Quebrador> tasks, ExecutorService service) {
        this.tasks = tasks;
        this.service = service;

        for (Quebrador q : this.tasks) {
            this.futures.add(service.submit(q));
        }
    }

    @Override
    public String call() throws Exception {
        this.service.shutdown();

        String senha = "";
        boolean found = false;

        while (true) {
            for (Future<String> f : futures) {
                if (f.isDone()) {
                    found = true;
                    senha = f.get();
                    break;
                }
            }

            if (found) {
                this.service.shutdownNow();
                break;
            }
        }

        return senha;
    }
}
