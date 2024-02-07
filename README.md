# OracleMultiThread
- This Java program creates a table called "Customer" in an Oracle database and populates it with 10,000 random customer records. It uses multithreading to speed up the insertion process.

- The program uses the JDBC API to establish a connection to the Oracle database and execute SQL statements. It creates a thread pool with 10 threads and creates a new DatabaseWorker object for each thread. Each DatabaseWorker object is responsible for inserting 1,000 records into the "Customer" table.

- The DatabaseWorker class implements the Runnable interface, so it can be executed by a thread. It has a constructor that sets the database connection and creates a new Random object. It also has four helper methods that generate random names, email addresses, phone numbers, and addresses for each record.

- The run method of the DatabaseWorker class generates random values for each column in the current record, creates an SQL INSERT statement, and executes it. It repeats this process 1,000 times to insert 1,000 records into the "Customer" table.

- The main method of the OracleMultiThread class creates a new table called "Customer" with five columns: id, name, email, phone, and address. It then creates a thread pool with 10 threads and adds a new DatabaseWorker object to the thread pool for each thread. It waits for all threads to finish before closing the database connection.

