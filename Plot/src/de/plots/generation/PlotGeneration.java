package de.plots.generation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import de.plots.main.main;
import de.plots.mysql.plotmysql;

public class PlotGeneration {

	public static void generatePlots(World world, Location middle, int gswidht, int plotamount) {
		
		for (int x = 0; x < gswidht * plotamount; x+=gswidht)  {
			for (int z = 0; z < gswidht * plotamount; z += gswidht) {
				Location min = new Location(world, x, 0, z);
				Location max = new Location(world, x + gswidht, 0, z + gswidht);
				
				//World: world | start_x start_z | end_x end_y | id | owner 
				
				SinglePlotGeneration.generateSinglePlot(world, min, max, main.waysize, 5, 65, Material.QUARTZ_BLOCK, Material.ANVIL);
				
				plotmysql.insertPlot(min, max, main.waysize);
			}
		}
	}
	
}
