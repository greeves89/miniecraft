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
import de.plots.utils.PlotInformation;

public class PlotGeneration {

	public static Material boardermaterial = Material.DIAMOND_BLOCK;
	public static Material waymaterial = Material.QUARTZ_BLOCK;
	public static ArrayList<PlotObject> plotLists = new ArrayList<>();
			
			//new PlotObject();
	
	
	public static void generatePlots(World world, Location middle, int gswidht, int plotamount, Material boarder, Material way, int waysize) {
		
		
		
//		mysql.deleteOwnerPlotTable();
//		mysql.deletePlotsTable();
		
		for (int x = middle.getBlockX(); x < middle.getBlockX() + (gswidht * plotamount) - 1; x+=gswidht)  {
			for (int z = middle.getBlockZ(); z < middle.getBlockZ() + (gswidht * plotamount) - 1; z += gswidht) {
				System.out.println("test");
				Location min = new Location(world, x, 0, z);
				Location max = new Location(world, x + gswidht, 0, z + gswidht);
				
				//World: world | start_x | start_z | end_x | end_y | id | owner 
				
				SinglePlotGeneration.generateSinglePlot(world, min, max, waysize, PlotInformation.grasshight, PlotInformation.stonehight, way, boarder);
	
				
				
				plotmysql.insertPlot(min, max, PlotInformation.waysize);
				int plotid = plotmysql.getLastPlotID();
				PlotObject plot = new PlotObject(min, max, plotid, null);
				addPlottoList(plot);
			}
		}
	}
	
	public static PlotObject getCorrespondingPlotObject(int _plotid) {
		
		for(int i= 0 ; i< plotLists.size();i++){
			if (plotLists.get(i).getPlotId() == _plotid){
				return plotLists.get(i);
			}
		}
		return null;
	}
	
	public static ArrayList<Integer> getPlotIDFromPlayer(String _playername){
		System.out.println("Es wird nach Plots für "+ _playername +" gesucht!");
		ArrayList<Integer> plotIDList = new ArrayList<Integer>();
		
		System.out.println("Es werden "+plotLists.size()+" nach "+_playername+" durchsucht.");
		
		for(int i = 0; i < plotLists.size();i++){
			String plotPlayername = null;
			try{
				plotPlayername = plotLists.get(i).getOwner().getName();
			}catch (NullPointerException e){
				System.out.println("Es ist ein Fehler aufgetreten -> Kein Spieler hinterlegt.");
			}
			
			if(_playername.equals(plotPlayername)){
				plotIDList.add(plotLists.get(i).getPlotId());
				System.out.println("Plot: "+plotLists.get(i).getPlotId()+" Besitzer:"+ plotPlayername);
				System.out.println("Vergleiche: _playername == plotPlayername");
				System.out.println("Ergebnis: " + _playername.equals(plotPlayername));
			}
		}
		return plotIDList;
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
				Location min = new Location(PlotInformation.plotworld.getWorld(), rs.getInt("start_x"), 0, rs.getInt("start_z"));
				Location max = new Location(PlotInformation.plotworld.getWorld(), rs.getInt("end_x"), 0, rs.getInt("end_z"));
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
