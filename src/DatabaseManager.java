import java.sql.*;

class DatabaseManager {

    // JDBC URL for SQLite
    private static final String DB_URL = "jdbc:sqlite:ToDoList.db";
    private Connection conn;

    public DatabaseManager() throws SQLException {
        this.conn = this.connect();
    }

    private Connection connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite has been established.");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found.", e);
        }
    }

    public void createListTable(String listName) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + listName + " (\n"
                + " id integer PRIMARY KEY,\n"
                + " priority integer NOT NULL,\n"
                + " description text,\n"
                + " iscompleted integer NOT NULL\n"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public int addTaskToList(String listName, Task task) {
        try {
            String sql = "INSERT INTO " + listName + "(description, priority, iscompleted) VALUES (?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, task.getDescription());
            pstmt.setInt(2, task.getPriority());
            pstmt.setBoolean(3, task.getIsCompleted());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Unable to connect to the database. Error: " + e.getMessage());
        }

        return -1;
    }

    public void updateTaskInList(String listName, Task task) {
        try {
            String sql = "UPDATE " + listName + " SET description = ?, priority = ?, iscompleted = ? WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, task.getDescription());
            pstmt.setInt(2, task.getPriority());
            pstmt.setBoolean(3, task.getIsCompleted());
            pstmt.setInt(4, task.getId());

            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Unable to connect to the database. Error: " + e.getMessage());
        }
    }

    public ToDoList findAllTasks(String listName) {
        ToDoList toDoList = new ToDoList();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + listName);

            while(rs.next()) {
                Task task = new Task(rs.getInt("id"), rs.getString("description"), rs.getInt("priority"), rs.getBoolean("iscompleted"));
                toDoList.addTask(task);
            }

        } catch (SQLException e) {
            System.out.println("Unable to connect to the database. Error: " + e.getMessage());
        }

        return toDoList;
    }

    public Task findTaskById(String listName, int taskToChange) {
        Task task = null;

        try {
            String sql = "SELECT * FROM " + listName + " WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, taskToChange);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                task = new Task(rs.getInt("id"), rs.getString("description"), rs.getInt("priority"), rs.getBoolean("iscompleted"));
            }

        } catch (SQLException e) {
            System.out.println("Unable to connect to the database. Error: " + e.getMessage());
        }

        return task;
    }

    public void removeTaskFromList(String listName, int removeTaskId) {
        try {
            String sql = "DELETE FROM " + listName + " WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, removeTaskId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Unable to connect to the database. Error: " + e.getMessage());
        }
    }

    public void closeConnection() throws SQLException {
        this.conn.close();
    }
}