package database;

//package BootCamp;
//-- download MySQL from: http://dev.mysql.com/downloads/
// Community Server version
//-- Installation instructions are here: http://dev.mysql.com/doc/refman/5.7/en/installing.html
//-- open MySQL Workbench to see the contents of the database
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//-- MAKE SURE THE JDBC CONNECTOR JAR IS IN THE BUILD PATH
// workspace -> properties -> Java Build Path -> Libraries -> Add External JARs...
// "C:\Users\reinhart\eclipse-workspace\CSC335\mysql-connector-java-8.0.27.jar"

//-- Preload MySQL with a Schema and Table
// In MySQL Workbench
// Create schema -- bootcamp: 
// CREATE SCHEMA 'bootcamp';
//
// Create table with records:
// CREATE TABLE 'bootcamp'.'users' (
//   'username' VARCHAR(32) NOT NULL,
//   'password' VARCHAR(32) NULL,
//   'emailaddress' VARCHAR(64) NULL,
//   'lockcount' INT NULL,
//   PRIMARY KEY ('username'));
public class DBaseConnection {

	// -- objects to be used for database access
 private Connection conn = null;
 private Statement stmt = null;
 private ResultSet rset = null;

 // -- connect to the world database
 // -- this is the connector to the database, default port is 3306
 //    private String url = "jdbc:mysql://localhost:3306/csc335project";
 //    private String url = "jdbc:mysql://127.0.0.1:3306/csc335project";
 private String url = "jdbc:mysql://127.0.0.1:3306/bootcamp";
 
 // -- this is the username/password, created during installation and in MySQL Workbench
 //    When you add a user make sure you give them the appropriate Administrative Roles
 //    (DBA sets all which works fine)
 //    private static String username = "<<Your MySQL username>>";
 //    private static String password = "<<Your MySQL password>>";
 public DBaseConnection(String username, String password) {
 	try {
			// -- make the connection to the database
			conn = DriverManager.getConnection(url, username, password);
	        
			// -- These will be used to send queries to the database
	        stmt = conn.createStatement();
	        rset = stmt.executeQuery("SELECT VERSION()");
	
	        if (rset.next()) {
	            System.out.println("MySQL version: " + rset.getString(1) + "\n=====================\n");
	        }
		} 
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}    	
 }
 
	public void accessDatabase() {
		try {
         
         // -- delete this record in case it exists
         stmt.executeUpdate("DELETE FROM users WHERE username='ccreinhart';");

         // -- a query will return a ResultSet
         //    get and print all records in the table
         System.out.println("Original Contents");
         rset = stmt.executeQuery("SELECT * FROM users;");
         printResultSet(rset);
        
         // -- insert a record into the table
         System.out.println("Inserted Contents");
         stmt.executeUpdate("INSERT INTO users VALUE('ccreinhart', 'ccreinhart1234', 'reinhart@yahoo.com', 0);");

         // -- get and print all records in the table
         rset = stmt.executeQuery("SELECT * FROM users;");
         printResultSet(rset);
         
         // -- modify a record in the table
         //    get the result set of records
         rset = stmt.executeQuery("SELECT * FROM users WHERE username='ccreinhart';");
         //    move the iterator to the record, if there is no record this will throw an exception
         rset.next();
         //    get the lockcount column and convert it to int
         int lockcount = Integer.parseInt(rset.getString(4));
         System.out.println("Updated Contents");
         stmt.executeUpdate("UPDATE users SET lockcount=" + (lockcount + 1) + " WHERE username='ccreinhart';");

         // -- get and print all records in the table
         rset = stmt.executeQuery("SELECT * FROM users;");
         printResultSet(rset);            
		} 
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public void printResultSet(ResultSet rset) {
		try {
	        // -- the metadata tells us how many columns in the data
			ResultSetMetaData rsmd = rset.getMetaData();
	        int numberOfColumns = rsmd.getColumnCount();
	        System.out.println("columns: " + numberOfColumns);
	        
	        // -- loop through the ResultSet one row at a time
	        //    Note that the ResultSet starts at index 1
	        while (rset.next()) {
	        	// -- loop through the columns of the ResultSet
	        	for (int i = 1; i < numberOfColumns; ++i) {
	        		System.out.print(rset.getString(i) + "\t");
	        	}
	        	System.out.println(rset.getString(numberOfColumns));
	        }
		}
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	public int getLockCount(String username) {
 		int lockCount = 0;
 		try {
 			rset = stmt.executeQuery("SELECT * FROM users WHERE username='" + username + "';");
 			rset.next();
 			lockCount = rset.getInt(4);
 		}
 		catch (SQLException ex) {
 			// handle any errors
 			System.out.println("SQLException: " + ex.getMessage());
 			System.out.println("SQLState: " + ex.getSQLState());
 			System.out.println("VendorError: " + ex.getErrorCode());
 		}
 		return lockCount;
 	}

 	// Increase lock count of a user by 1
 	public void increaseLockCount(String username) {
 		int lockCount = getLockCount(username);
 		try {
 			stmt.executeUpdate("UPDATE users SET lockcount=" + (lockCount + 1)
 					+ " WHERE username='" + username + "';");
 		}
 		catch (SQLException ex) {
 			// handle any errors
 			System.out.println("SQLException: " + ex.getMessage());
 			System.out.println("SQLState: " + ex.getSQLState());
 			System.out.println("VendorError: " + ex.getErrorCode());
 		}
 	}

 	// Reset a user's lock count to 0
 	public void resetLockCount(String username) {
 		try {
 			stmt.executeUpdate("UPDATE users SET lockcount=0 WHERE username='" + username + "';");
 		}
 		catch (SQLException ex) {
 			// handle any errors
 			System.out.println("SQLException: " + ex.getMessage());
 			System.out.println("SQLState: " + ex.getSQLState());
 			System.out.println("VendorError: " + ex.getErrorCode());
 		}
 	}

 	// Returns number of registered users
 	public int getUserCount() {
 		int userCount = 0;
 		try {
 			rset = stmt.executeQuery("SELECT COUNT(*) FROM users;");
 			rset.next();
 			userCount = rset.getInt(1);
 		}
 		catch (SQLException ex) {
 			// handle any errors
 			System.out.println("SQLException: " + ex.getMessage());
 			System.out.println("SQLState: " + ex.getSQLState());
 			System.out.println("VendorError: " + ex.getErrorCode());
 		}
 		return userCount;
 	}
 	
	
	public String getPassword(String username) {
 		String password = "";
 		try {
 			rset = stmt.executeQuery("SELECT * FROM users WHERE username='" + username + "';");
 			rset.next();
 			password = rset.getString(2);
 		}
 		catch (SQLException ex) {
 			// handle any errors
 			System.out.println("SQLException: " + ex.getMessage());
 			System.out.println("SQLState: " + ex.getSQLState());
 			System.out.println("VendorError: " + ex.getErrorCode());
 		}
 		return password;
 	}

 	public String getEmailAddress(String username) {
 		String emailAddress = "";
 		try {
 			rset = stmt.executeQuery("SELECT * FROM users WHERE username='" + username + "';");
 			rset.next();
 			emailAddress = rset.getString(3);
 		}
 		catch (SQLException ex) {
 			// handle any errors
 			System.out.println("SQLException: " + ex.getMessage());
 			System.out.println("SQLState: " + ex.getSQLState());
 			System.out.println("VendorError: " + ex.getErrorCode());
 		}
 		return emailAddress;
 	}

 	public boolean userExists(String username) {
 		boolean userExists = false;
 		try {
 			rset = stmt.executeQuery("SELECT EXISTS(SELECT * FROM users WHERE username='" + username + "') AS TRUTH;");
 			rset.next();
 			userExists = (Integer.parseInt(rset.getString(1)) == 1);
 		}
 		catch (SQLException ex) {
 			// handle any errors
 			System.out.println("SQLException: " + ex.getMessage());
 			System.out.println("SQLState: " + ex.getSQLState());
 			System.out.println("VendorError: " + ex.getErrorCode());
 		}
 		return userExists;
 	}

 	// returns true if successful, false if the user already exists
 	public void registerNewUser(String username, String password, String emailAddress) {
 		try {
 			stmt.executeUpdate("INSERT INTO users VALUE('"
 					+ username + "', '" + password + "', '" + emailAddress + "', 0);");
 		}
 		catch (SQLException ex) {
 			// handle any errors
 			System.out.println("SQLException: " + ex.getMessage());
 			System.out.println("SQLState: " + ex.getSQLState());
 			System.out.println("VendorError: " + ex.getErrorCode());
 		}
 	}
 	
	
	public static void main(String[] args) {

		// -- username = csc335, password = C$C335
		//    username = root, password = root
//		Scanner kb = new Scanner(System.in);
//		System.out.print("MySQL username: ");
//		String username = kb.next();
//		System.out.print("MySQL password: ");
//		String password = kb.next();
//		
//		DBaseConnection dbc = new DBaseConnection(username, password);
//		dbc.accessDatabase();
	}

}