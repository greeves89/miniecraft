package de.plots.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.plots.generation.PlotGeneration;
import de.plots.generation.SinglePlotGeneration;
import de.plots.main.main;
import de.plots.utils.PlotInformation;
import de.plots.utils.plotjava;

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
		
		SinglePlotGeneration.claimPlot(p, getPlotID(p));
		p.sendMessage("Das Plot wurde geclaimt!");
		// insert into owner_plot einfügen
		
		
	}
	
	public static void portToPlot(Player p){
		Location minPlotLocation = null;
		Location maxPlotLocation = null;
		
		ArrayList<Integer> plotIDList = PlotGeneration.getPlotIDFromPlayer(p.getName());
		
		p.sendMessage("§9Es wurden " + plotIDList.size()+" gefunden!");
		System.out.println("Gefundene PlotID: "+plotIDList.get(0));
		
		System.out.println(getPlotLocation(plotIDList.get(0)).size());
		
		minPlotLocation = getPlotLocation(plotIDList.get(0)).get(0);
		maxPlotLocation = getPlotLocation(plotIDList.get(0)).get(1);
		
		System.out.println("X: "+minPlotLocation.getBlockX()+"Y: "+minPlotLocation.getBlockY()+"Z: "+minPlotLocation.getBlockZ());
		System.out.println("X: "+maxPlotLocation.getBlockX()+"Y: "+maxPlotLocation.getBlockY()+"Z: "+maxPlotLocation.getBlockZ());
		
		minPlotLocation.setWorld(minPlotLocation.getWorld());
		minPlotLocation.setY(70);
		minPlotLocation.setZ(minPlotLocation.getZ());
		minPlotLocation.setX(minPlotLocation.getX() + PlotInformation.plotsize / 2);
		
		p.teleport(minPlotLocation);
		
	}
	
	public static void autoClaimPlot(Player p) {
		int plotID = 0;
		if(getOwnerID(p) == -1) {
			insertOwner(p);
		}
		
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT * FROM Plots LEFT OUTER JOIN owner_plot on Plots.ID = owner_plot.plot_id WHERE owner_plot.owner_id IS NULL ORDER BY ID LIMIT 1");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				plotID = rs.getInt("ID");
				p.sendMessage("Plot reserviert: "+ plotID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (plotID != 0){
			try {
				PreparedStatement ps = mysql.getConnection().prepareStatement("INSERT INTO owner_plot (owner_id,plot_id) VALUES (?,?)");
				ps.setInt(1, getOwnerID(p));
				ps.setInt(2, plotID);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SinglePlotGeneration.claimPlot(p, plotID);
			p.sendMessage("Das Plot wurde geclaimt!");	
		}else{
			p.sendMessage("Fehler beim beanspruchen eines Plots.");	
		}

	}
	
	public static ArrayList<Location> getPlotLocation(int _id) {
		ArrayList<Location> plotLocationList = new ArrayList<>();
		System.out.println("Suche die Location von PlotID:" +_id);
		Location min = new Location(PlotInformation.plotworld.getWorld(), 0, 0, 0);
		Location max = new Location(PlotInformation.plotworld.getWorld(), 0, 0, 0);
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
				min.setX(rs.getInt("start_x") - PlotInformation.waysize);
				min.setZ(rs.getInt("start_z") - PlotInformation.waysize);
				max.setX(rs.getInt("end_x"));
				max.setZ(rs.getInt("end_z"));
			}
			plotLocationList.add(min);
			plotLocationList.add(max);
			System.out.println("Es wurden "+plotLocationList.size()+" Locations des Plots "+_id+" gefunden.");
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
			p.sendMessage(plotjava.getPlotOwner(plotid) + " - " +  p.getName());	
		}
		
		
		if (plotjava.getPlotOwner(plotid).equals(p.getName())) {
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
					SinglePlotGeneration.unclaimPlot(plotid, p);
				} else {
					p.sendMessage("Ein Fehler ist aufgetreten!");
				}
			} else {
				p.sendMessage("Fehler bei Plot reset!");
			}
			
		} else {
			p.sendMessage("Dieses Plot gehört dir nicht!");
		}
			
		
		// insert into owner_plot einfÃ¼gen
	}	
	public static Integer getLastPlotID() {
		
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
