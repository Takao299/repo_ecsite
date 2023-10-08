package com.bh.ecsite.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String DRIVER_NAME = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/ecsite";
	private static final String USER = "ecsite";
	private static final String PASSWORD = "ecsite";
//	private static final String URL = "jdbc:postgresql://localhost:5432/jdbc";
//	private static final String USER = "jdbc";
//	private static final String PASSWORD = "jdbc";

	private Connection connection;

	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName(DRIVER_NAME);
		connection = DriverManager.getConnection(URL, USER, PASSWORD);
		connection.setAutoCommit(false);
		return connection;
	}

	public void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public void commit() throws SQLException {
		if (connection != null) {
			connection.commit();
		}
	}

	public void rollback() throws SQLException {
		if (connection != null) {
			connection.rollback();
		}
	}
}
