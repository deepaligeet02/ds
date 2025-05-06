import java.rmi.*;

public class Server{
    public static void main(String[] args) {
        try {
            ServerImpl serverobj =new ServerImpl();
            Naming.rebind("Server", serverobj);
        } catch (Exception e) {
            System.out.println("Exception occured at server" +e.getMessage());
        }
        
    }
}