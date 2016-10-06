package de.plots.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Location;

public class plotmysql {
	
	public static void insertPlot(Location min, Location max) {
		
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("INSERT INTO Plots (start_x,start_z,end_x,end_z) VALUES (?,?,?,?)");
			ps.setString(1, min.getBlockX() + "");
			ps.setString(2, min.getBlockZ() + "");
			ps.setString(3, max.getBlockX() + "");
			ps.setString(4, max.getBlockZ() + "");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
