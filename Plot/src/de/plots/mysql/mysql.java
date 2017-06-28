package de.plots.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class mysql {
	
	public static String host = "host";
	public static String port = "3306";
	public static String database = "database";
	public static String username = "username";
	public static String password = "password";
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
	/**
	 * CREATE TABLE METHODS
	 */
	public static void createPlotInfoTable() {
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PlotInfo(worldname VARCHAR(100), waysize INT(100), plotsize INT(100))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void createPlotsTable() {
		String s = "CREATE TABLE IF NOT EXISTS Plots (ID INT NOT NULL primary key AUTO_INCREMENT, start_x VARCHAR(100), start_z VARCHAR(100), end_x VARCHAR(100), end_z VARCHAR(100))";
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void createOwnerTable() {
		String s = "CREATE TABLE IF NOT EXISTS owner (ownerid INT NOT NULL primary key AUTO_INCREMENT, ownername VARCHAR(100), uuid VARCHAR (100))";
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void createOwnerPlotTable() {
		String s = "CREATE TABLE IF NOT EXISTS owner_plot (owner_id INT(100), plot_id INT(100))";
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement(s);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * DELETE TABEL METHODS
	 */
	public static void deletePlotsTable() {
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("DELETE FROM Plots");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void deleteOwnerPlotTable() {
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("DELETE FROM owner_plot");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
