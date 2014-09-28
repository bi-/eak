package eak;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientMain {
    private static void testProjectTable(TaskService service) throws RemoteException {
        try {
            service.addProject(null);
        } catch (TaskException te) {
            System.err.println(te);
        }
        try {
            service.addProject(new Project());
        } catch (TaskException te) {
            System.err.println(te);
        }
        try {
            Project project = new Project();
            project.setName("proba");
            project.setLeader("proba");
            project.setYear(2014);
            service.addProject(project);
        } catch (TaskException te) {
            System.err.println(te);
        }
    }

    private static void testTaskTable(TaskService service) throws RemoteException {
        try {
            Task task = service.addTask("proba", "b", "c");
            System.out.println(task.getId());
        } catch (TaskException te) {
            System.err.println(te);
        }
        try {
            Task task = service.addTask("probaxxx", "b", "c");
            System.out.println(task.getId());
        } catch (TaskException te) {
            System.err.println(te);
        }
    }

    public static void main(String[] args) {
        // Set up a security manager to allow remote code loading
        if (null == System.getSecurityManager()) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Locate the RMI registry
            Registry registry = LocateRegistry.getRegistry();

            // Get a client stub for the service
            TaskService service
                    = (TaskService) registry.lookup(TaskService.NAME);

            // Invoke the remote methods and show the results
            testProjectTable(service);
            testTaskTable(service);
            fillValidData(service);
            System.out.println(service.getStatistics());


        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void fillValidData(TaskService service) {
        try {
            service.addProject(new Project("project1", 11, "Leader Janos"));
            service.addProject(new Project("project2", 12, "Leader Karoly"));
            service.addProject(new Project("project3", 13, "Leader Laszlo"));
            service.addTask("project1", "desc1", "Manager Ivan");
            service.addTask("project1", "desc2", "Manager Ivan");
            service.addTask("project1", "desc3", "Manager Ivan");

            service.addTask("project2", "desc1", "Manager Gabor");
            service.addTask("project2", "desc2", "Manager Gabor");


            service.addTask("project3", "desc1", "Manager Zsolt");
            service.addTask("project3", "desc2", "Manager Zsolt");
            service.addTask("project3", "desc3", "Manager Zsolt");
            service.addTask("project3", "desc3", "Manager Zsolt");

        } catch (TaskException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}