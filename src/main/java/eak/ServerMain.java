package eak;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {

    public static void main(String[] args) {
        // Set up a security manager to allow remote code loading
        if (null == System.getSecurityManager()) {
            System.setSecurityManager(new SecurityManager());
        }

        try {

            // Instantiate the service implementation
            TaskServiceImpl service = new TaskServiceImpl();

            // Export it on a random port (start to listen to clients)
            Remote stub = UnicastRemoteObject.exportObject(service, 0);

            // Locate the RMI registry
            Registry registry = LocateRegistry.createRegistry(1099);

            // Make the service available through a fixed name
            registry.rebind(TaskService.NAME, stub);

            // Indicate that the server started successfully
            System.err.println("Server started ...");
        } catch (RemoteException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}