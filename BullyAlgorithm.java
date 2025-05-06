import java.util.*;

public class BullyAlgorithm {
    int currentCoordinator;            // Stores the current leader process ID
    int totalProcesses;                // Total number of processes
    boolean[] isProcessAlive;          // Stores whether each process is up (true) or down (false)

    public BullyAlgorithm(int total) {
        totalProcesses = total;
        isProcessAlive = new boolean[totalProcesses];
        currentCoordinator = total;    // Initially, the last process is the coordinator

        System.out.println("Creating processes...");
        for (int i = 0; i < totalProcesses; i++) {
            isProcessAlive[i] = true;
            System.out.println("Process P" + (i + 1) + " created and is up.");
        }
        System.out.println("Process P" + currentCoordinator + " is the initial coordinator.");
    }

    // Display the status of each process and the coordinator
    void showProcessStatus() {
        for (int i = 0; i < totalProcesses; i++) {
            System.out.println("Process P" + (i + 1) + " is " + (isProcessAlive[i] ? "up" : "down"));
        }
        System.out.println("Current coordinator is: P" + currentCoordinator);
    }

    // Bring a process up
    void bringProcessUp(int processId) {
        if (!isProcessAlive[processId - 1]) {
            isProcessAlive[processId - 1] = true;
            System.out.println("Process P" + processId + " is now up.");
        } else {
            System.out.println("Process P" + processId + " is already up.");
        }
    }

    // Bring a process down
    void bringProcessDown(int processId) {
        if (!isProcessAlive[processId - 1]) {
            System.out.println("Process P" + processId + " is already down.");
        } else {
            isProcessAlive[processId - 1] = false;
            System.out.println("Process P" + processId + " is now down.");
        }
    }

    // Run the Bully election algorithm
    void startElection(int initiatorProcessId) {
        if (!isProcessAlive[initiatorProcessId - 1]) {
            System.out.println("Process P" + initiatorProcessId + " is down and cannot start the election.");
            return;
        }

        System.out.println("Process P" + initiatorProcessId + " is initiating an election...");
        boolean foundHigherProcess = false;

        // Send election messages to all higher-numbered processes
        for (int i = initiatorProcessId; i < totalProcesses; i++) {
            if (isProcessAlive[i]) {
                System.out.println("Election message sent from P" + initiatorProcessId + " to P" + (i + 1));
                foundHigherProcess = true;
            }
        }

        // Elect the highest-numbered alive process as the coordinator
        if (foundHigherProcess) {
            for (int i = totalProcesses - 1; i >= 0; i--) {
                if (isProcessAlive[i]) {
                    currentCoordinator = i + 1;
                    break;
                }
            }
        } else {
            currentCoordinator = initiatorProcessId;
        }

        System.out.println("Process P" + currentCoordinator + " is elected as the new coordinator.");
    }

    // Main menu-driven program
    public static void main(String[] args) {
        BullyAlgorithm bully = null;
        int totalProcesses, chosenProcessId, menuChoice;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Bully Election Algorithm ===");
            System.out.println("1. Create processes");
            System.out.println("2. Show process status");
            System.out.println("3. Bring up a process");
            System.out.println("4. Bring down a process");
            System.out.println("5. Start election");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            menuChoice = scanner.nextInt();

            switch (menuChoice) {
                case 1:
                    System.out.print("Enter total number of processes: ");
                    totalProcesses = scanner.nextInt();
                    bully = new BullyAlgorithm(totalProcesses);
                    break;
                case 2:
                    if (bully != null)
                        bully.showProcessStatus();
                    else
                        System.out.println("Please create processes first.");
                    break;
                case 3:
                    System.out.print("Enter process number to bring up: ");
                    chosenProcessId = scanner.nextInt();
                    bully.bringProcessUp(chosenProcessId);
                    break;
                case 4:
                    System.out.print("Enter process number to bring down: ");
                    chosenProcessId = scanner.nextInt();
                    bully.bringProcessDown(chosenProcessId);
                    break;
                case 5:
                    System.out.print("Enter process number to start election: ");
                    chosenProcessId = scanner.nextInt();
                    bully.startElection(chosenProcessId);
                    bully.showProcessStatus();
                    break;
                case 6:
                    System.out.println("Exiting program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


