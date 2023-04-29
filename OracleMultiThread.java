
//@Authur Tyon Davis
import java.sql.*;
import java.util.concurrent.*;

public class OracleMultiThread {
    public static void main(String[] args) {
        // database connection details
        String dbURL = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "----";
        String password = "--";

        try {
            // establish a database connection
            Connection conn = DriverManager.getConnection(dbURL, username, password);
            System.out.println("Connected to database.");

            // create a new table called "Customer" with five columns: id, name, email,
            // phone, and address
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE Customer (id NUMBER(10) NOT NULL, name VARCHAR2(50) NOT NULL, email VARCHAR2(50) NOT NULL, phone VARCHAR2(20) NOT NULL, address VARCHAR2(100) NOT NULL)";
            stmt.execute(sql);
            System.out.println("Table created.");

            // create a thread pool with 10 threads
            ExecutorService executor = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 10; i++) {
                // create a new DatabaseWorker object and add it to the thread pool
                Runnable worker = new DatabaseWorker(conn);
                executor.execute(worker);
            }

            // shut down the thread pool and wait for all threads to finish
            executor.shutdown();
            while (!executor.isTerminated()) {
                // wait for all threads to finish
            }

            // close the database connection
            conn.close();
            System.out.println("Disconnected from database.");
        } catch (SQLException ex) {
            System.out.println("Can't connect");
            ex.printStackTrace();
        }
    }
}

// this class implements the Runnable interface, so it can be executed by a
// thread
class DatabaseWorker implements Runnable {
    private Connection conn;
    private Random rand;

    // constructor that sets the database connection and creates a new Random object
    public DatabaseWorker(Connection conn) {
        this.conn = conn;
        this.rand = new Random();
    }

    // the run method that will be executed when the thread is started
    public void run() {
        try {
            Statement stmt = conn.createStatement();
            // insert 1000 records into the "Customer" table
            for (int i = 0; i < 1000; i++) {
                // generate random values for each column in the current record
                int id = rand.nextInt(10000);
                String name = getRandomName();
                String email = getRandomEmail();
                String phone = getRandomPhone();
                String address = getRandomAddress();
                // create an SQL INSERT statement and execute it
                String sql = "INSERT INTO Customer (id, name, email, phone, address) VALUES (" + id + ", '" + name
                        + "', '" + email + "', '" + phone + "', '" + address + "')";
                stmt.executeUpdate(sql);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.out.print("Ty we have a problem");
            ex.printStackTrace();
        }
    }

    // helper method that returns a random name from a predefined list of first and
    // last names
    private String getRandomName() {
        String[] firstNames = { "Tyon", "Reggie", "Bob", "Alice", "David", "Sarah", "Emily", "Mike", "Lisa", "Black" };
        String[] lastNames = { "Smith", "Davis", "Brown", "Lee", "Wilson", "Davis", "Garcia", "Miller", "Jones",
                "Taylor" };
        String firstName = firstNames[rand.nextInt(firstNames.length)];
        String lastName = lastNames[rand.nextInt(lastNames.length)];
        return firstName + " " + lastName;
    }

    // Method to generate a random email address using a list of pre-defined domain
    // names and providers
    private String getRandomEmail() {
        String[] domains = { "gmail.com", "yahoo.com", "hotmail.com", "aol.com", "outlook.com", "icloud.com",
                "live.com" };
        String[] providers = { "johndoe", "janedoe", "Tkdavis21", "alicesmith", "davidjones", "sarahbrown",
                "miketaylor", "lisawilson", "tomlee" };
        String domain = domains[rand.nextInt(domains.length)];
        String provider = providers[rand.nextInt(providers.length)];
        return provider + "@" + domain;
    }

    // Method to generate a random phone number with a random area code, prefix, and
    // suffix
    private String getRandomPhone() {
        String[] areaCodes = { "201", "202", "203", "204", "205", "206", "207", "208", "209", "210" };
        String[] prefixes = { "555", "666", "777", "888", "999" };
        String[] suffixes = new String[10000];
        // Create an array of 10000 suffixes with leading zeros
        for (int i = 0; i < suffixes.length; i++) {
            suffixes[i] = String.format("%04d", i);
        }
        String areaCode = areaCodes[rand.nextInt(areaCodes.length)];
        String prefix = prefixes[rand.nextInt(prefixes.length)];
        String suffix = suffixes[rand.nextInt(suffixes.length)];
        return areaCode + "-" + prefix + "-" + suffix;
    }

    // Method to generate a random address using a list of pre-defined street names,
    // cities, states, and zip codes
    private String getRandomAddress() {
        String[] streetNames = { "Main St.", "First St.", "Second St.", "Third St.", "Fourth St." };
        String[] cities = { "New York", "Los Angeles", "Covington", "Houston", "Phoenix" };
        String[] states = { "NY", "CA", "GA", "TX", "AZ" };
        String[] zipCodes = { "10001", "90001", "60601", "77001", "85001" };
        String streetNumber = Integer.toString(rand.nextInt(1000) + 1);
        String streetName = streetNames[rand.nextInt(streetNames.length)];
        String city = cities[rand.nextInt(cities.length)];
        String state = states[rand.nextInt(states.length)];
        String zipCode = zipCodes[rand.nextInt(zipCodes.length)];
        return streetNumber + " " + streetName + ", " + city + ", " + state + " " + zipCode;
    }

}