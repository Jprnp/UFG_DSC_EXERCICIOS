import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Callable;

public class ConnectionTester implements Callable {

    private short ipSlot1;

    private short ipSlot2;

    private short ipSlot3;

    private short ipSlot4;

    private long stepsTaken = 0;

    private long totalQuantity;

    private Vector<String> reachableIps = new Vector<String>();

    public ConnectionTester(long begin, long totalQuantity) {
        this.totalQuantity = totalQuantity;
        this.initializeIp(begin);
    }

    private String ipAsString() {
        return ipSlot1 + "." + ipSlot2 + "." + ipSlot3 + "." + ipSlot4;
    }

    private boolean nextIp() {
        if (this.stepsTaken == this.totalQuantity - 1) {
            return false;
        }

        this.addOneToIp();
        return true;
    }

    private void addOneToIp() {
        if (this.ipSlot4 == 255) {
            this.ipSlot4 = 0;
            this.addOneToSlot3();
        } else {
            this.ipSlot4++;
        }
        this.stepsTaken++;
    }

    private void addOneToSlot3() {
        if (this.ipSlot3 == 255) {
            this.ipSlot3 = 0;
            this.addOneToSlot2();
        } else {
            this.ipSlot3++;
        }
    }

    private void addOneToSlot2() {
        if (this.ipSlot2 == 255) {
            this.ipSlot2 = 0;
            this.addOneToSlot1();
        } else {
            this.ipSlot2++;
        }
    }

    private void addOneToSlot1() {
        this.ipSlot1++;
    }

    private void initializeIp(long steps) {
        this.ipSlot1 = (short) ((steps >> 24) & 0xFF);
        this.ipSlot2 = (short) ((steps >> 16) & 0xFF);
        this.ipSlot3 = (short) ((steps >> 8) & 0xFF);
        this.ipSlot4 = (short) (steps & 0xFF);
    }

    private long isReachable(String hostAddress) {
        InetAddress inetAddress = null;
        Date start, stop;
        try {
            inetAddress = InetAddress.getByName(hostAddress);
        } catch (UnknownHostException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        try {
            start = new Date();
            if (inetAddress.isReachable(5000)) {
                stop = new Date();
                return (stop.getTime() - start.getTime());
            }
        } catch (IOException e1) {
            System.out.println("Erro: " + e1.getMessage());
        } catch (IllegalArgumentException e1) {
            System.out.println("Timeout inválido:" + e1.getMessage());
        }
        return -1; // para indicar erro
    }

    public Vector<String> call() throws Exception {
        do {
            System.out.println("Testando: " + this.ipAsString());
            if (this.isReachable(this.ipAsString()) != -1) {
                this.reachableIps.add(this.ipAsString());
            }
        } while (this.nextIp());

        return this.reachableIps;
    }
}
