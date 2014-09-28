package eak;


import java.sql.*;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskServiceImpl implements TaskService {
    private Connection conn;

    public TaskServiceImpl() {

        try {
            // Create a databasae connection to the local database server
            conn = DriverManager.getConnection("jdbc:derby:tasks;create=true");

            // Check that our table exists
            DatabaseMetaData metaData = conn.getMetaData();

            // Filter only by table name (note the upper case), and type
            ResultSet tables = metaData.getTables(null, null, "PROJECT", new String[]{"TABLE"});

            // Check that the result set is empty
            if (!tables.next()) {
                // Create a statement that will create the table
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(
                            "CREATE TABLE PROJECT (" +
                                    "name VARCHAR(255) NOT NULL PRIMARY KEY ," +
                                    "description VARCHAR(255) DEFAULT NULL," +
                                    "theyear INT NOT NULL ," +
                                    "leader VARCHAR(255) NOT NULL " +
                                    ")"
                    );
                    Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, "PROJECT table created.");

                }

            } else {
                Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, "PROJECT table not created, already exist.");

            }
            // Filter only by table name (note the upper case), and type
            tables = metaData.getTables(null, null, "TASK", new String[]{"TABLE"});

            // Check that the result set is empty
            if (!tables.next()) {
                // Create a statement that will create the table
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(
                            "CREATE TABLE TASK (" +
                                    "id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY ," +
                                    "project VARCHAR(255)," +
                                    "description VARCHAR(255) NOT NULL," +
                                    "manager VARCHAR(255) NOT NULL," +
                                    "developer VARCHAR(255)," +
                                    "status VARCHAR(255) default 'NEW'," +
                                    "CONSTRAINT PRJ_NAME_FK FOREIGN KEY (project) REFERENCES PROJECT(name) )"
                    );
                    Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, "TASK table created.");

                }
            } else {
                Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, "TASK table not created, already exist.");

            }
            tables.close();
        } catch (SQLException ex) {
            Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void addProject(Project newProject) throws TaskException {
        Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, "addProject");
        if (newProject == null) {
            throw new TaskException("Invalid project (null value)");
        } else if (!newProject.isMandatoryFilled()) {
            throw new TaskException("Mandatory parameters are not filled.");
        }
        try {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO PROJECT (name, description, theyear, leader) VALUES (?,?,?,?)")) {
                stmt.setString(1, newProject.getName());
                stmt.setString(2, newProject.getDescription());
                stmt.setInt(3, newProject.getYear());
                stmt.setString(4, newProject.getLeader());
                stmt.execute();
                Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, newProject + " inserted.");

            }
        } catch (SQLIntegrityConstraintViolationException scvex) {
            throw new TaskException("Project name " + newProject.getName() + " already exists. " /*+ scvex*/);

        } catch (SQLException sqlex) {
            Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.SEVERE, null, sqlex);
        }
    }

    @Override
    public synchronized Task addTask(String project, String description, String manager) throws TaskException {

        Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, "addTask");
        if (project == null) {
            throw new TaskException("Invalid project (null value)");
        } else if (description == null) {
            throw new TaskException("Invalid description (null value)");
        } else if (manager == null) {
            throw new TaskException("Invalid manager (null value)");
        }
        try {
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO TASK (project, description, manager) VALUES (?,?,?)", new String[]{"ID"})) {
                stmt.setString(1, project);
                stmt.setString(2, description);
                stmt.setString(3, manager);
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getInt(1));
                    task.setProject(project);
                    task.setDescription(description);
                    task.setManager(manager);
                    task.setStatus(TaskStatus.New);
                    Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, "Task: " + task.getId() + " inserted.");
                    return task;
                } else {
                    throw new TaskException("Valami bug");
                }

            }
        } catch (SQLIntegrityConstraintViolationException scvex) {
            throw new TaskException("Reccs" + scvex);

        } catch (SQLException sqlex) {
            Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.SEVERE, null, sqlex);
            throw new TaskException("Reccs" + sqlex);
        }
    }

    @Override
    public synchronized Map<String, Integer> getStatistics() {
        Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.INFO, "getStatistics");
        TreeMap<String, Integer> result = new TreeMap<String, Integer>();
        try {
            try (Statement stmt = conn.createStatement()) {

                ResultSet rs = stmt.executeQuery("SELECT project FROM task WHERE status = 'NEW' ");

                while (rs.next()) {
                    String project = rs.getString(1);
                    if (result.containsKey(project)) {
                        result.put(project, result.get(project) + 1);
                    } else {
                        result.put(project, 1);
                    }
                }
            }
        } catch (SQLException sqlex) {
            Logger.getLogger(TaskServiceImpl.class.getName()).log(Level.SEVERE, null, sqlex);
        }
        return result;
    }
}
