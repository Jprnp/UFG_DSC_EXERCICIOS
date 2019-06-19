import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ServerMain {

    private static ServerSocket server = null;

    private static int port = 9876;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(port);
            System.out.println("Servidor iniciado na porta: " + port);
        } catch (IOException e) {
            System.out.println("Erro ao iniciar servidor");
            System.exit(0);
        }

        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("Conexão recebida. Iniciando cálculos...");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Map<String, String> request = (Map<String, String>) ois.readObject();
                new Thread(new Connection(Integer.parseInt(request.get("nThreads")), request.get("initialIp"),
                        request.get("finalIp"), socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
