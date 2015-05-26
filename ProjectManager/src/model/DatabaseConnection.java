package model;
import java.sql.*;
import javax.swing.*;

public class DatabaseConnection {
	
	Connection dbConnection = null;
	
	public static Connection dbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\StephnyO\\Documents\\workspace\\ProjectManager4\\PMDatabase.sqlite");
		//	JOptionPane.showMessageDialog(null, "Connection successful!");
			return dbConnection;
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}

}
