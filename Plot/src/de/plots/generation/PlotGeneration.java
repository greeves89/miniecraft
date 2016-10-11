package de.plots.generation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.material.MaterialData;

import de.plots.main.main;
import de.plots.mysql.mysql;
import de.plots.mysql.plotmysql;

public class PlotGeneration {

	public static Material boardermaterial = Material.DIAMOND_BLOCK;
	public static Material waymaterial = Material.QUARTZ_BLOCK;
	public static World plotworld = Bukkit.getWorld("world");
	public static ArrayList<PlotObject> plotLists = new ArrayList<>();
			
			//new PlotObject();
	
	
	public static void generatePlots(World world, Location middle, int gswidht, int plotamount) {
		
		mysql.deleteOwnerPlotTable();
		mysql.deletePlotsTable();
		
		for (int x = 0; x < gswidht * plotamount; x+=gswidht)  {
			for (int z = 0; z < gswidht * plotamount; z += gswidht) {
				Location min = new Location(world, x, 0, z);
				Location max = new Location(world, x + gswidht, 0, z + gswidht);
				
				//World: world | start_x start_z | end_x end_y | id | owner 
				
				SinglePlotGeneration.generateSinglePlot(world, min, max, main.waysize, 5, 65, waymaterial, boardermaterial);
	
				
				
				plotmysql.insertPlot(min, max, main.waysize);
				int plotid = plotmysql.getLastPlotID();
				PlotObject plot = new PlotObject(min, max, plotid, null);
				addPlottoList(plot);
			}
		}
	}
	
	
	public static void addPlottoList (PlotObject _plot){
		plotLists.add(_plot);	
	}
	@SuppressWarnings("deprecation")
	public static void registerPlots() {
		
		try {
			PreparedStatement ps = mysql.getConnection().prepareStatement("SELECT * from Plots left outer join owner_plot on Plots.ID = owner_plot.plot_id left outer join owner on owner_plot.owner_id = owner.ownerid");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Location min = new Location(PlotGeneration.plotworld, rs.getInt("start_x"), 0, rs.getInt("start_z"));
				Location max = new Location(PlotGeneration.plotworld, rs.getInt("end_x"), 0, rs.getInt("end_z"));
				OfflinePlayer of;
				PlotObject plot;
				System.out.println(rs.getString("ownername") + " ");
				if (rs.getString("ownername") == null) {
					of = Bukkit.getOfflinePlayer("e");
					plot = new PlotObject(min, max, rs.getInt("ID"), null);
				} else {
					of = Bukkit.getOfflinePlayer(rs.getString("ownername"));
					plot = new PlotObject(min, max, rs.getInt("ID"), of);
				}
				
				PlotGeneration.addPlottoList(plot);
			}
			System.out.println("Es wurden " + plotLists.size() + " Plots hinzugefügt!");
 		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
