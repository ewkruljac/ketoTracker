package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DbDriver {
	
	String username;
	String password;
	String host;
	String port;
	String url;
	String db;
	
	public DbDriver() {
		try (InputStream input = new FileInputStream("resources/config.properties")) {
			Properties props = new Properties();
				
			props.load(input);
				
			username = props.getProperty("username");
			password = props.getProperty("password");
			host = props.getProperty("host");
			port = props.getProperty("port");
			db = props.getProperty("db");
			url = "jdbc:postgresql://" + host + ":" + port + "/" + db;
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void insert(String date, float weight, float glucose, float ketones, String table) {
		Connection connection = null;
		String sql = "INSERT INTO " + table + 
				     " (date, weight, glucose, ketones) VALUES (" +
				     "date '" + date + "', " + 
				     weight  + ", " + 
				     glucose + ", " + 
				     ketones + ")";
		try {
			connection = DriverManager.getConnection(url, username, password);
			if (connection != null) {
				Statement statement = connection.createStatement();
				statement.executeUpdate(sql);
				statement.close();
				connection.close();
			} else {
				System.out.println("Connection failed.");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public String[][] retrieveAll() {
		Connection connection = null;
		String[][] results = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
			if (connection !=null) {
				Statement statement = connection.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				ResultSet rs = statement.executeQuery("SELECT * FROM stats");
				ResultSetMetaData metadata = rs.getMetaData();
				
				int columnCount = metadata.getColumnCount();
				rs.last();
				int rowCount = rs.getRow();
				rs.beforeFirst();
				results = new String[rowCount][columnCount];
				
				while (rs.next()) {
					String[] row = new String[columnCount];
					for (int i=1; i<=columnCount; i++) {
						row[i-1] = rs.getString(i);
					}
					results[rs.getRow()-1] = row;
				}
				rs.close();
				statement.close();
				connection.close();
			} else {
				System.out.println("Connection failed.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return results;
	}
	
	public void update(String table, String day_num, String column, String value) {
		Connection connection = null;
		String sql = "UPDATE " + table + " SET " + 
		             column + "='" + value + 
		             "' WHERE day_num=" + day_num;
		try {
			connection = DriverManager.getConnection(url, username, password);
			if (connection != null) {
				Statement statement = connection.createStatement();
				statement.executeUpdate(sql);
				statement.close();
				connection.close();
			} else {
				System.out.println("Connection failed.");
			} 
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void destroy(String table, String day_num) {
		Connection connection = null;
		String sql = "DELETE FROM " + table + 
				     " WHERE day_num" +
		             " = " + day_num;
		System.out.println(sql);
		try {
			connection = DriverManager.getConnection(url, username, password);
			if (connection != null) {
				Statement statement = connection.createStatement();
				statement.executeUpdate(sql);
				statement.close();
				connection.close();
			} else {
				System.out.println("Connection failed.");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
/*	@SuppressWarnings("rawtypes")
	public String[][] guiQuery(String sql) {
		//Connection connection = null;
		String[][] results = null;
		String sql = "SELECT * FROM " + table + " WHERE ";
		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry pair = (Map.Entry)iter.next();
			sql += pair.getKey() + " " + operator + " " +
			pair.getValue() + " AND ";
		}
		System.out.println(sql.substring(0, 0)));
		return results;
	}*/
	
}
