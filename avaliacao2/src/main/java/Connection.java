import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Connection implements Runnable {

    private int threadCount;

    private String beginIp;

    private String endIp;

    private Socket socket;

    private ArrayList<Future<Vector<String>>> futures = new ArrayList<Future<Vector<String>>>();

    private Vector<String> reachableIps = new Vector<String>();

    public Connection(int threadCount, String beginIp, String endIp, Socket socket) {
        this.threadCount = threadCount;
        this.beginIp = beginIp;
        this.endIp = endIp;
        this.socket = socket;
    }

    public static long calcQuantityInRange(String ip1, String ip2) {
        return ipToNumber(ip2) - ipToNumber(ip1) + 1;
    }

    public static long ipToNumber(String ip) {
        long ipNumber = 0;
        int powerCount = 3;

        String[] ipNumbers = ip.split("\\.");

        for (String number : ipNumbers) {
            ipNumber += Integer.parseInt(number) * (long) Math.pow(256, powerCount--);
        }

        return ipNumber;
    }

    private void startTasks() {
        long rangeQuantity = calcQuantityInRange(this.beginIp, this.endIp);
        long quantityPerThread = rangeQuantity / this.threadCount;
        long remainder = rangeQuantity % this.threadCount;
        long begin = ipToNumber(beginIp);

        ExecutorService executor = Executors.newFixedThreadPool(this.threadCount);

        for (int i = 1; i <= this.threadCount; i++) {
            if (i == this.threadCount) {
                quantityPerThread+= remainder;
            }

            ConnectionTester tester = new ConnectionTester(begin, quantityPerThread);
            futures.add(executor.submit(tester));
            begin += quantityPerThread;
        }

        executor.shutdown();
    }

    private void awaitResults() {
        for (Future<Vector<String>> f : this.futures) {
            try {
                this.reachableIps.addAll(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(this.reachableIps);
    }

    private void communicateResults() throws IOException {
        PrintStream printer = new PrintStream(this.socket.getOutputStream());
        this.reachableIps.forEach(printer::println);
        this.socket.close();
    }

    public void run() {
        System.out.println("Iniciando cálculo p/ faixa: " + this.beginIp + " - " + this.endIp);
        this.startTasks();
        this.awaitResults();
        try {
            this.communicateResults();
        } catch (IOException e) {
            System.out.println("Perda de conexão ao cliente");
        }
    }
}
