//methods are declared here
import java.rmi.*;

interface ServerIntf extends Remote{
    //method declarations
    public double addition(double num1, double num2) throws RemoteException;
    public double substraction(double num1, double num2) throws RemoteException;
    public double multiplication(double num1, double num2) throws RemoteException;
    public double division(double num1, double num2) throws RemoteException;


}