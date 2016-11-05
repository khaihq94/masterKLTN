package mySQLServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLAccess {
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public boolean connect(String url, String user, String password){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(url, user, password);
			statement = connect.createStatement();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void insertData(String tableName, String[] columns, String[] values){
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("INSERT INTO ");
			builder.append(tableName);
			builder.append("(");
			builder.append(columns[0]);
			for (int i = 1; i < columns.length; i++) {
				builder.append(",");
				builder.append(columns[i]);
			}
			builder.append(") VALUE( ?");
			for (int i = 1; i < values.length; i++) {
				builder.append(",?");
			}
			builder.append(")");
			
			preparedStatement = connect.prepareStatement(builder.toString());
			for (int i = 0; i < values.length; i++) {
				preparedStatement.setString(i + 1, values[i]);
			}
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			if(resultSet != null){
				resultSet.close();
			}
			
			if(statement != null){
				statement.close();
			}
			
			if(connect != null){
				connect.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Cannot disconnect MySQL");
		}
	}
}
