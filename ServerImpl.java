import java.rmi.*;
import java.rmi.server.*;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf{
    public ServerImpl() throws RemoteException {
        super(); // Calls the UnicastRemoteObject constructor
    }
    public double addition(double num1, double num2) throws RemoteException{
        return num1 + num2;
    }
    public double substraction(double num1, double num2) throws RemoteException{
        return num1 - num2;
    }
    public double multiplication(double num1, double num2) throws RemoteException{
        return num1 * num2;
    }
    public double division(double num1, double num2) throws RemoteException{
        if(num2!= 0){
            return num1 / num2;
        }
        else {
            System.out.println("can not divide the no by 0");
            return 0;
        }
    }

}
