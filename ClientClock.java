import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8080);
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.execute(() -> {
            try (OutputStream os = socket.getOutputStream()) {
                while (true) {
                    String now = LocalDateTime.now().toString();
                    os.write((now + "\n").getBytes());
                    os.flush();
                    System.out.println("Sent: " + now);
                    Thread.sleep(5000);
                }
            } catch (Exception ignored) {}
        });

        pool.execute(() -> {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("Synchronized time: " + line);
                }
            } catch (Exception ignored) {}
        });
    }
}

