import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) {
        Map<String, String> request = new HashMap<String, String>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o IP do servidor: ");
        String ipServidor = scanner.nextLine();

        System.out.print("Digite a porta de comunicação com o servidor: ");
        int port = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite o número de Threads: ");
        request.put("nThreads", scanner.nextLine());

        System.out.print("Digite o IP inicial: ");
        request.put("initialIp", scanner.nextLine());

        System.out.print("Digite o IP final: ");
        request.put("finalIp", scanner.nextLine());

        Socket socket = null;
        ObjectOutputStream oos = null;

        try {
            socket = new Socket(ipServidor, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(request);

            System.out.println("Dados enviados ao servidor. Aguardando resultados...");

            Scanner receiver = new Scanner(socket.getInputStream());

            while (true) {
                try {
                    System.out.println(receiver.nextLine());
                } catch (NoSuchElementException e) {
                    System.out.println("Conexão encerrada.");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Não foi possível acessar o servidor");
            System.exit(0);
        }
    }
}
