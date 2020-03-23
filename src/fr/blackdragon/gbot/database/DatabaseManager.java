package fr.blackdragon.gbot.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {

	private String url;
	private String user;
	private String pass;
	
	private Connection connection;
	private Statement statement;
	
	public DatabaseManager(String url, String user, String pass) {
		this.url = url;
		this.user = user;
		this.pass = pass;
	}
	
	public void open() {
		Class c;
		
		try {
			c = Class.forName("com.mysql.cj.jdbc.Driver");
	        Driver pilote = (Driver)c.newInstance() ;
	        DriverManager.registerDriver(pilote);
		} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e1) {
			Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e1);
		}
		
		try {
			this.connection = DriverManager.getConnection(url, user, pass);
			this.statement = connection.createStatement();
		} catch (SQLException e) {
			Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public void close() {
		try {
			statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public Statement getStatement() {
		return this.statement;
	}
	
}
