package eak;

import java.io.Serializable;

//Részfeladat (eak.Task):
//
//        sorszám (id): egész szám, elsődleges kulcs
//        projekt neve (project): sztring, külső kulcs az előző entitáshoz, kötelező
//        leírás (description): sztring, kötelező
//        felelős (manager): sztring, kötelező
//        fejlesztő (developer): sztring, opcionális
//        állapot (status): egy érték a következő felsorolásból (eak.TaskStatus):
//        új bejelentés (New)
//        fejlesztőhöz hozzárendelt (Assigned)
//        megoldott (Resolved)
//        lezárt (Closed)
public class Task implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    private int id;
    private String project;
    private String description;
    private String manager;
    private String developer;
    private TaskStatus status;

}
