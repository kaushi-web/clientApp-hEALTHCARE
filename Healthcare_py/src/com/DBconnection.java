package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
	public Connection connection;
	private static DBconnection single_instance = null;

	/**
	 * 
	 */
	private DBconnection() {
		try {
			if (connection == null || connection.isClosed()) {

				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/paf?serverTimezone=UTC", "root",
						"");

				System.out.println(connection);
				System.out.println("connect to db");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static DBconnection getDBconnection() {

		if (single_instance == null) {
			System.out.println("success");
			single_instance = new DBconnection();
		}
		return single_instance;
	}

}
