import mpi.MPI;

public class ArrSum {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int processRank = MPI.COMM_WORLD.Rank(); // Unique ID for each process
        int totalProcesses = MPI.COMM_WORLD.Size(); // Total number of processes

        int elementsPerProcess = 5; // Each process will handle 5 elements
        int masterProcess = 0;

        int[] fullArray = null; // Array to hold all elements (used by master only)
        fullArray = new int[elementsPerProcess * totalProcesses];

        int[] localArray = new int[elementsPerProcess]; // Each process gets part of the array
        int[] partialSums = new int[totalProcesses];     // Each process will send one partial sum

        // Master process initializes the array
        if (processRank == masterProcess) {
            int totalElements = elementsPerProcess * totalProcesses;
            System.out.println("Enter " + totalElements + " elements:");
            for (int i = 0; i < totalElements; i++) {
                fullArray[i] = i; // Can use scanner for custom input if needed
                System.out.println("Element " + i + " = " + i);
            }
        }

        // Distribute chunks of array to all processes
        MPI.COMM_WORLD.Scatter(
            fullArray,
            0,
            elementsPerProcess,
            MPI.INT,
            localArray,
            0,
            elementsPerProcess,
            MPI.INT,
            masterProcess
        );

        // Each process calculates sum of its own elements
        for (int i = 1; i < elementsPerProcess; i++) {
            localArray[0] += localArray[i];
        }
        System.out.println("Intermediate sum at process " + processRank + " is " + localArray[0]);

        // Gather all partial sums to master
        MPI.COMM_WORLD.Gather(
            localArray,
            0,
            1,
            MPI.INT,
            partialSums,
            0,
            1,
            MPI.INT,
            masterProcess
        );

        // Master adds up all partial sums
        if (processRank == masterProcess) {
            int totalSum = 0;
            for (int i = 0; i < totalProcesses; i++) {
                totalSum += partialSums[i];
            }
            System.out.println("Final sum : " + totalSum);
        }

        MPI.Finalize();
    }
}
