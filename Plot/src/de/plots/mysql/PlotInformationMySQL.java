package de.plots.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.plots.utils.PlotInformation;

public class PlotInformationMySQL {

	// GO TO PlotInformationConfig Class - outdated
	
	private static void setInformation(String _worldname, int _waysize, int _plotSize) {
		//delete
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("DELETE FROM PlotInfo WHERE worldname = ?");
			ps.setString(1, _worldname);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//create
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("INSERT INTO PlotInfo (worldname,waysize,plotsize) VALUES (?,?,?)");
			ps.setString(1, _worldname);
			ps.setInt(2, _waysize);
			ps.setInt(3, _plotSize);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private static void readInformationTable() {
		int waysize = -1;
		int plotsize = -1;
		World plotworld = Bukkit.getWorld("not_init");
		
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT waysize, plotsize");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				plotworld = Bukkit.getWorld(rs.getString("worldname"));
				waysize = rs.getInt("waysize");
				plotsize = rs.getInt("plotsize");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PlotInformation.plotsize = plotsize;
		PlotInformation.waysize = waysize;
		PlotInformation.plotworld = plotworld;
	}
}
