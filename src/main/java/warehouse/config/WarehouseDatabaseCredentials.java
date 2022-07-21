package warehouse.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class WarehouseDatabaseCredentials {
	
	private static WarehouseDatabaseCredentials instance;
	private String url, username, password;
	
	/**
	 * Constructor that gets access to the database.
	 */
	private WarehouseDatabaseCredentials() {
		try {
			//Always load the driver to ensure it is running.
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (InputStream input = WarehouseDatabaseCredentials.class.getClassLoader().getResourceAsStream("application.properties")) {
				
				//Get the url, username, and password from the application.properties file.
				Properties props = new Properties();
				props.load(input);
				this.url = props.getProperty("db.url");
				this.username = props.getProperty("db.username");
				this.password = props.getProperty("db.password");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets an instance of the Credentials singleton.
	 * @return Access to the database.
	 */
	public static WarehouseDatabaseCredentials getInstance() {
		if (instance == null) {
			instance = new WarehouseDatabaseCredentials();
		}
		return instance;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	/**
	 * Gets a connection to the database.
	 * @return A connection to the database.
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url,username,password);
	}
}
