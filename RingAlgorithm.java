import java.util.*;

public class RingAlgorithm{
    int totalProcesses;
    int currentCoordinator;
    boolean[] isProcessAlive;
    ArrayList<Integer> ringMessageList;

    public RingAlgorithm(int numberOfProcesses) {
        totalProcesses = numberOfProcesses;
        isProcessAlive = new boolean[totalProcesses];
        ringMessageList = new ArrayList<>();
        currentCoordinator = totalProcesses;

        for (int i = 0; i < totalProcesses; i++) {
            isProcessAlive[i] = true;
            System.out.println("Process P" + (i + 1) + " created.");
        }

        System.out.println("P" + currentCoordinator + " is the coordinator");
    }

    void displayProcesses() {
        for (int i = 0; i < totalProcesses; i++) {
            System.out.println("Process P" + (i + 1) + " is " + (isProcessAlive[i] ? "up" : "down"));
        }
        System.out.println("Process P" + currentCoordinator + " is the coordinator");
    }

    void bringProcessUp(int processId) {
        if (!isProcessAlive[processId - 1]) {
            isProcessAlive[processId - 1] = true;
            System.out.println("Process P" + processId + " is now up.");
        } else {
            System.out.println("Process P" + processId + " is already up.");
        }
    }

    void bringProcessDown(int processId) {
        if (!isProcessAlive[processId - 1]) {
            System.out.println("Process P" + processId + " is already down.");
        } else {
            isProcessAlive[processId - 1] = false;
            System.out.println("Process P" + processId + " is down.");
        }
    }

    void displayRingList(ArrayList<Integer> ringList) {
        System.out.print("[ ");
        for (Integer id : ringList) {
            System.out.print(id + " ");
        }
        System.out.println("]");
    }

    void startElection(int initiatorId) {
        if (!isProcessAlive[initiatorId - 1]) {
            System.out.println("Process P" + initiatorId + " is down. Cannot initiate election.");
            return;
        }

        ringMessageList.clear();
        int current = initiatorId - 1;

        do {
            if (isProcessAlive[current]) {
                ringMessageList.add(current + 1);
                System.out.print("Process P" + (current + 1) + " sending message. Current list: ");
                displayRingList(ringMessageList);
            }
            current = (current + 1) % totalProcesses;
        } while (current != (initiatorId - 1));

        currentCoordinator = Collections.max(ringMessageList);
        System.out.println("Process P" + initiatorId + " has declared P" + currentCoordinator + " as the new coordinator.");
        ringMessageList.clear();
    }

    public static void main(String[] args) {
        RingAlgorithm ring = null;
        int total, processId, choice;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Ring Algorithm ---");
            System.out.println("1. Create processes");
            System.out.println("2. Display processes");
            System.out.println("3. Bring up a process");
            System.out.println("4. Bring down a process");
            System.out.println("5. Run election algorithm");
            System.out.println("6. Exit Program");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the number of processes: ");
                    total = sc.nextInt();
                    ring = new RingAlgorithm(total);
                    break;
                case 2:
                    ring.displayProcesses();
                    break;
                case 3:
                    System.out.print("Enter the process number to bring up: ");
                    processId = sc.nextInt();
                    ring.bringProcessUp(processId);
                    break;
                case 4:
                    System.out.print("Enter the process number to bring down: ");
                    processId = sc.nextInt();
                    ring.bringProcessDown(processId);
                    break;
                case 5:
                    System.out.print("Enter the process number to initiate election: ");
                    processId = sc.nextInt();
                    ring.startElection(processId);
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

