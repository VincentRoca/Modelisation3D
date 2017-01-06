package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InitializeDB {
	public void initialize(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try{
			Connection connection = DriverManager.getConnection("jdbc:sqlite:modele.db");
			Statement statement = connection.createStatement();
			
			statement.executeUpdate("create table modele(id char(30), chemin text, date date, MotsCles text, constraint PK_id PRIMARY KEY (id));");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
}
