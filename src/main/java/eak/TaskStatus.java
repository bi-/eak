package eak;

import java.io.Serializable;

//        állapot (status): egy érték a következő felsorolásból (eak.TaskStatus):
//        új bejelentés (New)
//        fejlesztőhöz hozzárendelt (Assigned)
//        megoldott (Resolved)
//        lezárt (Closed)
public enum TaskStatus implements Serializable {
    New, Assigned, Resolved, Closed
}
