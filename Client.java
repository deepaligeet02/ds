import java.rmi.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        Scanner scobj = new Scanner(System.in);
        try {
            //server url is in the string which is in rmi regidtory
            String serverurl = "rmi://localhost/Server";
            ServerIntf serverIntf = (ServerIntf)Naming.lookup(serverurl);
            System.out.println("Enter 1st num: ");
            double num1 = scobj.nextDouble();
            System.out.println("Enter 2nd num: ");
            double num2 = scobj.nextDouble();
             System.out.println("---------result----------"  );
             System.out.println(serverIntf.addition(num1, num2));
             System.out.println(serverIntf.substraction(num1, num2));
             System.out.println(serverIntf.multiplication(num1, num2));
             System.out.println(serverIntf.division(num1, num2));
             

        } catch (Exception e) {
            System.out.println("exceotion occured at client" + e.getMessage());
        }
    }
    
}
