package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbDriver {
	
	private String username;
	private String password;
	private String host;
	private String port;
	private String url;
	private String db;
	private Connection connection;
	
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
			try {
				connection = DriverManager.getConnection(url, username, password);
			} catch (Exception e) {
				System.out.println("Connection failed.");
			}
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void insert(String date, float weight, float glucose, float ketones, String table) {
		String sql = "INSERT INTO " + table + 
				     " (date, weight, glucose, ketones) VALUES (?, ?, ?, ?)";  
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setDate(1, java.sql.Date.valueOf(date));
			statement.setFloat(2, weight);
			statement.setFloat(3, glucose);
			statement.setFloat(4, ketones);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public List<RowData> retrieveAll() {
		List<RowData> results = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM stats");

			while (resultSet.next()) {
				RowData row = new RowData(resultSet.getString("day_num"), resultSet.getString("date"), 
						                  resultSet.getString("weight"), resultSet.getString("glucose"), 
						                  resultSet.getString("ketones"));
				results.add(row);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return results;
	}
	
	public void update(String table, int day_num, String column, float value) {
		String sql = "UPDATE " + table + " SET " + column + " = ? WHERE day_num = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setFloat(1, value);
			statement.setInt(2, day_num);
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void destroy(String table, int day_num) {
		String sql = "DELETE FROM " + table + " WHERE day_num = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, day_num);
			statement.executeUpdate();
			statement.close();
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
