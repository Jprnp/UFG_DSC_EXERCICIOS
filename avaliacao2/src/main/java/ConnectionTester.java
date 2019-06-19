public class ConnectionTester implements Runnable {

    private short ipSlot1;

    private short ipSlot2;

    private short ipSlot3;

    private short ipSlot4;

    private long stepsTaken = 0;

    private long totalQuantity;

    public ConnectionTester(long inicio, long fim, long totalQuantity) {
        System.out.println("Nova instância");
        this.totalQuantity = totalQuantity;
        this.initializeIp(inicio);
        System.out.println("IP inicializado: " + this.ipAsString());
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

    private boolean nextIpInit(long totalSteps) {
        if (this.stepsTaken == totalSteps - 1) {
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
        while (this.nextIpInit(steps)) {
        }

        this.stepsTaken = 0;
    }

    public void run() {
        do {
            System.out.println(this.ipAsString());
            // TODO: Verificar Conexão / Adicionar na lista os válidos
        } while (this.nextIp());
    }
}
