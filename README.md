# SQLmult-ithread
Oracle database and performs multi-threaded database operations using the Executor framework. The program starts by setting up the database connection with the specified URL, username, and password. It then creates an ExecutorService with a fixed thread pool of 10 threads.

For each thread, it creates a new instance of the DatabaseWorker class, passing in the connection object and a unique identifier. The run() method of the DatabaseWorker class performs a specific database operation, which in this case is a SELECT statement to retrieve customer information based on the provided ID.

The worker thread executes the SELECT statement and prints out the retrieved customer name and email to the console. Finally, the connection, statement, and result set are closed, and any SQLExceptions are caught and printed to the console.
