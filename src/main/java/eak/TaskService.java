package eak;

import java.rmi.Remote;
import java.rmi.RemoteException;

interface TaskService extends Remote {
    String NAME = "TaskService";

    void addProject(Project newProject) throws TaskException, RemoteException;
    // Hozzáad egy új projektet. Ha nem töltöttük ki valamelyik kötelező mezőt,
    // dobjon eak.TaskException kivételt. Ha már létezik ilyen nevű projekt, szintén.

    Task addTask(String project, String description, String manager) throws TaskException, RemoteException;
    // Hozzáad egy új részfeladatot. Ha nem töltöttük ki valamelyik paraméter mezőt, dobjon
    // eak.TaskException kivételt. Ha nem létezik a kapcsolódó nevű projekt, szintén.
    // Az új feladatnak az adatbázis alapján kell új azonosítót osztani. Ezt az
    // értéket a visszaadott objektumnak tartalmaznia kell, az állapota mező értéke
    // pedig legyen "új". A részfeladat fejlesztőjét tekintsük kitöltetlennek.

    java.util.Map<String, Integer> getStatistics() throws RemoteException;
// Statisztikát ad vissza a projektekről. A kulcs a projektek neve, az érték
// pedig a projekthez tartozó "új" állapotú részfeladatok száma.
}