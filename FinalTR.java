import java.util.Scanner;

class FinalTR {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of nodes: ");
        int n = sc.nextInt();
        int token = 0;  // Token starts with node 0

        System.out.println("Nodes in Ring:");
        for (int i = 0; i < n; i++)
            System.out.print(" " + i);
        System.out.println(" " + 0);

        try {
            while (true) {
                System.out.println("\nToken is with node " + token);
                System.out.print("Does node " + token + " want to enter Critical Section? (yes/no): ");
                String choice = sc.next();

                if (choice.equalsIgnoreCase("yes")) {
                    System.out.println("Node " + token + " is ENTERING Critical Section...");
                
                    System.out.print("Do you want to send data to another node? (yes/no): ");
                    String sendData = sc.next();
                
                    if (sendData.equalsIgnoreCase("yes")) {
                        System.out.print("Enter receiver node (0 to " + (n - 1) + "): ");
                        int receiver = sc.nextInt();
                
                        System.out.print("Enter data to send: ");
                        String data = sc.next();
                
                        System.out.println("Sending data: '" + data + "' from Node " + token + " to Node " + receiver);
                
                        // Simulate data passing through intermediate nodes
                        int i = (token + 1) % n;
                        while (i != receiver) {
                            System.out.println("Data '" + data + "' forwarded by Node " + i);
                            i = (i + 1) % n;
                        }
                
                        System.out.println("Data '" + data + "' received by Node " + receiver);
                    }
                
                    Thread.sleep(1000);
                    System.out.println("Node " + token + " is EXITING Critical Section...");
                } else {
                    System.out.println("Node " + token + " passed without entering CS.");
                }
                

                // Pass token to next node
                token = (token + 1) % n;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
