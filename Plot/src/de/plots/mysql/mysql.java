package de.plots.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class mysql {
	
	public static String host = "miniecraft.de";
	public static String port = "3306";
	public static String database = "Plotserver";
	public static String username = "plot";
	public static String password = "banane";
	public static Connection con;
	
	public static void connect() {
		if (!isConnected()) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
				Bukkit.getConsoleSender().sendMessage("§2[MYSQL Database] wurde verbunden!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
	}
	public static void close() {
		if (isConnected()) {
			try {
				con.close();
				Bukkit.getConsoleSender().sendMessage("§c[MYSQL Database] wurde getrennt!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isConnected() {
		return (con == null ? false : true);
	}
	
	public static Connection getConnection() {
		return con;
	}
	
	public static void createTable() {
		String s = "CREATE TABLE IF NOT EXISTS Plots (ID INT(100), Playername VARCHAR(100), World VARCHAR(100), UUID VARCHAR(100), start_x VARCHAR(100), start_z VARCHAR(100), end_x VARCHAR(100), end_z VARCHAR(100))";
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
