//@Authur Tyon Davis
import java.sql.*;
import java.util.concurrent.*;

public class OracleMultiThread {
    public static void main(String[] args) {
        String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "----";
        String password = "--";
      
        try {
            Connection conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected to database.");
         
            ExecutorService executor = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10; i++) {
                Runnable worker = new DatabaseWorker(conn, i);
                executor.execute(worker);
            }
         
            executor.shutdown();
            while (!executor.isTerminated()) {
                // wait for all threads to finish
            }
         
            conn.close();
            System.out.println("Disconnected from database.");
        } catch (SQLException ex) {
            System.out.println("Can't connect");
            ex.printStackTrace();
        }
    }
}

class DatabaseWorker implements Runnable {
    private Connection conn;
    private int id;
   
    public DatabaseWorker(Connection conn, int id) {
        this.conn = conn;
        this.id = id;
    }
   
    public void run() {
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM customers WHERE id = " + id;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Customer: " + name + " (" + email + ")");
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
