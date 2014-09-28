package eak;

import java.io.Serializable;

//
//Fejlesztési projekt (eak.Project):
//
//        név (name): sztring, elsődleges kulcs
//        leírás (description): sztring, opcionális
//        kezdés éve (year): egész szám, kötelező
//        a vezető felhasználó neve (leader): sztring, kötelező
public class Project implements Serializable {
    private String name;
    private String description;
    public Project() {}
    public Project(String name, Integer year, String leader) {
        this.name = name;
        this.year = year;
        this.leader = leader;
    }

    private Integer year;
    private String leader;


    public boolean isMandatoryFilled() {
        return name != null && year != null && leader != null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }


    public String toString() {
        return "[ " + name + ", " + description + ", " + year + ", " + leader + " ]";
    }
}
