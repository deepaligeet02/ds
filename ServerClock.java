import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;
import java.time.format.DateTimeFormatter;

public class Server {
    static class ClientInfo {
        Socket socket;
        Duration offset;
        ClientInfo(Socket s) { socket = s; }
    }

    static Map<String, ClientInfo> clients = new ConcurrentHashMap<>();
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(8080);
        ExecutorService pool = Executors.newCachedThreadPool();

        pool.execute(() -> {
            while (true) {
                try {
                    Socket client = server.accept();
                    String id = client.getInetAddress() + ":" + client.getPort();
                    clients.put(id, new ClientInfo(client));
                    System.out.println(id + " connected.");

                    pool.execute(() -> handleClient(id, client));
                } catch (Exception ignored) {}
            }
        });

        pool.execute(() -> {
            while (true) {
                try {
                    if (!clients.isEmpty()) {
                        Duration avg = clients.values().stream()
                            .map(c -> c.offset)
                            .reduce(Duration.ZERO, Duration::plus)
                            .dividedBy(clients.size());

                        for (ClientInfo c : clients.values()) {
                            String syncTime = LocalDateTime.now().plus(avg).format(formatter);
                            c.socket.getOutputStream().write((syncTime + "\n").getBytes());
                        }
                    }
                    Thread.sleep(5000);
                } catch (Exception ignored) {}
            }
        });
    }

    static void handleClient(String id, Socket client) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                LocalDateTime clientTime = LocalDateTime.parse(line, formatter);
                Duration diff = Duration.between(clientTime, LocalDateTime.now());
                clients.get(id).offset = diff;
                System.out.println(id + " updated offset: " + diff.getSeconds() + "s");
            }
        } catch (Exception e) {
            clients.remove(id);
            System.out.println(id + " disconnected.");
        }
    }
}
