import java.util.Arrays;

public class BerkeleyAlgorithm {
    public static void main(String[] args) {
        
        double masterTime = System.currentTimeMillis() / 1000.0;
        
        
        double[] slaveTimes = {masterTime + Math.random() * 10 - 5, masterTime + Math.random() * 10 - 5, masterTime + Math.random() * 10 - 5};
        
        System.out.println("Master time: " + masterTime);
        System.out.println("Slave times: " + Arrays.toString(slaveTimes));
        
        // differences
        double[] differences = new double[slaveTimes.length];
        double totalDifference = 0;
        for (int i = 0; i < slaveTimes.length; i++) {
            differences[i] = slaveTimes[i] - masterTime;
            totalDifference += differences[i];
        }
        
        double averageDifference = totalDifference / (slaveTimes.length + 1);
        System.out.println("Average time difference: " + averageDifference);
        
     
        masterTime += averageDifference;
        for (int i = 0; i < slaveTimes.length; i++) {
            slaveTimes[i] -= differences[i];
            slaveTimes[i] += averageDifference;
        }
        
        System.out.println("Synchronized master time: " + masterTime);
        System.out.println("Synchronized slave times: " + Arrays.toString(slaveTimes));
    }
}