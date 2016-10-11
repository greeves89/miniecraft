package de.plots.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import de.plots.generation.PlotGeneration;
import de.plots.generation.SinglePlotGeneration;
import de.plots.main.main;

public class plotmysql {
	
	public static void insertPlot(Location min, Location max, int waysize) {
		
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("INSERT INTO Plots (start_x,start_z,end_x,end_z) VALUES (?,?,?,?)");
			ps.setString(1, (min.getBlockX() + waysize) + "");
			ps.setString(2, (min.getBlockZ() + waysize) + "");
			ps.setString(3, max.getBlockX() + "");
			ps.setString(4, max.getBlockZ() + "");
			System.out.println("test");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	
	
	public static int getOwnerID(Player p) {
	int i = -1;
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT ownerid, ownername, uuid FROM owner WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				p.sendMessage(rs.getInt("ownerid") + "");
				return rs.getInt("ownerid");
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return i;
	
	}
	
	public static void insertOwner(Player p) {
	
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("INSERT INTO owner (ownername,uuid) VALUES (?,?)");
		
			ps.setString(1, p.getName());
			ps.setString(2, p.getUniqueId().toString());
			System.out.println("User: " + p.getName() + " wurde erstellt!");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static String getPlotOwner(int plotid) {
		String s = null;
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT * FROM owner_plot LEFT OUTER JOIN owner ON owner.ownerid=owner_plot.owner_id where plot_id = ?");
			ps.setInt(1, plotid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("ownername"));
				return rs.getString("ownername");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public static void claimPlot(Player p) {
		
		if(getOwnerID(p) == -1) {
			insertOwner(p);
		}
		
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("INSERT INTO owner_plot (owner_id,plot_id) VALUES (?,?)");
			ps.setInt(1, getOwnerID(p));
			ps.setInt(2, getPlotID(p));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		p.sendMessage("Das Plot wurde geclaimt!");
		// insert into owner_plot einfügen
		
		
	}
	public static ArrayList<Location> getPlotLocation(int _id) {
		ArrayList<Location> plotLocationList = new ArrayList<>();
		int start_x;
		int start_z;
		int end_x;
		int end_z;
		Location min = new Location(PlotGeneration.plotworld, 0, 0, 0);
		Location max = new Location(PlotGeneration.plotworld, 0, 0, 0);
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT * FROM Plots WHERE ID = ?");
			ps.setInt(1, _id);
			ResultSet rs = ps.executeQuery();
			if(main.debug == true){
				if(rs == null){
					System.out.println("Resultset ist null!");	
				}
				
			}
			while(rs.next()) {
				min.setX(rs.getInt("start_x") - main.waysize);
				min.setZ(rs.getInt("start_z") - main.waysize);
				max.setX(rs.getInt("end_x"));
				max.setZ(rs.getInt("end_z"));
			}
			plotLocationList.add(min);
			plotLocationList.add(max);
			return plotLocationList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void unclaimPlot(Player p) {
		int plotid =  getPlotID(p);
		boolean reset = false;
		
		if(main.debug == true){
			p.sendMessage(getPlotOwner(plotid) + " - " +  p.getName());	
		}
		
		
		if (getPlotOwner(plotid).equals(p.getName())) {
			p.sendMessage("Dieses Plot gehört dir!");
			
			
			
			try {
				PreparedStatement ps = mysql.getConnection().prepareStatement("DELETE FROM owner_plot WHERE plot_id = ?");
				ps.setInt(1,plotid);
				ps.executeUpdate();
				reset = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (reset == true){
				p.sendMessage("Das Plot wurde unclaimet!");
				p.sendMessage(getPlotLocation(plotid).size() + "");
				
				if (getPlotLocation(plotid) != null) {
					Location min = getPlotLocation(plotid).get(0);
					Location max = getPlotLocation(plotid).get(1);
					SinglePlotGeneration.generateSinglePlot(PlotGeneration.plotworld, min, max, main.waysize, 5, 65, Material.QUARTZ_BLOCK, Material.ANVIL);
				} else {
					p.sendMessage("Ein Fehler ist aufgetreten!");
				}
			} else {
				p.sendMessage("Fehler bei Plot reset!");
			}
			
		} else {
			p.sendMessage("Dieses Plot gehört dir nicht!");
		}
			
		
		// insert into owner_plot einfügen
	}	
	public static Integer getLastPlotID() {
		
		//ICH HOLE EBEN WAS ZU ESSEN
		
		
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT ID FROM Plots ORDER BY ID DESC Limit 1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				return rs.getInt("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
