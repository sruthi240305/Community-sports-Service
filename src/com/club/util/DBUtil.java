package com.club.util;

import java.sql.DriverManager;

public class DBUtil {
	static String URL ="jdbc:mysql://localhost:3306/club_db";
	static String user="root";
	static String password="sruthi240305";
	public static java.sql.Connection getDBConnection() {
		try {
			return DriverManager.getConnection(URL, user, password);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
