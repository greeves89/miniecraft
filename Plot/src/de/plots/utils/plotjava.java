package de.plots.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.plots.generation.PlotGeneration;
import de.plots.generation.PlotObject;
import de.plots.generation.SinglePlotGeneration;
import de.plots.main.main;
import de.plots.mysql.mysql;
import de.plots.mysql.plotmysql;

public class plotjava {
	
	public static int getPlotID(Player p) {
	
		int i = -1;
		
	//Select plotid from plots where 
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT ID FROM Plots WHERE (start_x < ? AND end_x > ?) AND (? > start_z AND ? < end_z)");
			ps.setInt(1, p.getLocation().getBlockX());
			ps.setInt(2, p.getLocation().getBlockX());
			ps.setInt(3, p.getLocation().getBlockZ());
			ps.setInt(4, p.getLocation().getBlockZ());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				p.sendMessage(rs.getInt("ID") + "");
				return rs.getInt("ID");
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return i;
	}
	
	
	public static ArrayList<Integer> getPlotIDFromOwner(Player p) {
		return PlotGeneration.getPlotIDFromPlayer(p.getName());
	}
	
	public static String getPlotOwner(int _plotid) {
		String s = null;
		
		if (PlotGeneration.getCorrespondingPlotObject(_plotid).getOwner() == null) {
			return "Kein Besitzer";
		} else {
			return PlotGeneration.getCorrespondingPlotObject(_plotid).getOwner().getName();
		}
	}
	
	
	
	/**
	 * return's true if Player is on a plot where he can build
	 * 'PLOTJAVA Methode' - erfoldert update Methode bevor Methode zu 100% funktioniert
	 * There can be bug's and the method can cause lags
	 *´@author Eric
	 */
	
	
	public static boolean isPlotOwnedByPlayer(Player p) {
		Location pLoc = p.getLocation();
		for (PlotObject plot : PlotGeneration.plotLists) {
			Location min = plot.getMin();
			Location max = plot.getMax();
			//Checking if Player is on the current Plot...
			if (min.getBlockX() < pLoc.getBlockX() && min.getBlockZ() < pLoc.getBlockZ() && max.getBlockX() > pLoc.getBlockX() && max.getBlockZ() > pLoc.getBlockZ()) {
				//Check if the Player own the PLot
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
}
