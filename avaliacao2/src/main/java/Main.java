public class Main {

    public static final int numeroThreads = 3;

    public static String beginIp = "1.1.1.1";

    public static String endIp = "1.1.1.13";//"1.1.3.1";

    public static void main(String[] args) {
        long rangeQuantity = calcQuantityInRange(beginIp, endIp);
        System.out.println("Quantidade de IPs: " + rangeQuantity);

        long quantityPerThread = rangeQuantity / numeroThreads;
        long remainder = rangeQuantity % numeroThreads;
        System.out.println("Quantidade por Thread: " + quantityPerThread);
        System.out.println("Sobra: " + remainder);

        long inicio = ipToNumber(beginIp) + 1;
        for (int i = 1; i <= numeroThreads; i++) {
            if (i == numeroThreads) {
                quantityPerThread++;
            }

            //TODO: Chamar Thread
            ConnectionTester connectionTester = new ConnectionTester(inicio, inicio + quantityPerThread, quantityPerThread);
            connectionTester.run();
            inicio += quantityPerThread;
        }
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
}
