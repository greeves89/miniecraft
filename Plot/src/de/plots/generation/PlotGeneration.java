package de.plots.generation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class PlotGeneration {

	/**
	 * Notice that the min and max parameter is the totaly outside of the plot!
	 * 
	 * @author Eric D.
	 */
	
	public static void generateSinglePlot(World world, Location min, Location max, int widht, int way, int grassheight, int stoneheight, Material bordermaterial, Material waymaterial) {
		
		//Removing every single Block at the Plot
		
		for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
			for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
				for (int y = min.getBlockY(); y <= max.getBlockY(); y++ ) {
					Location temp = new Location(world, x, y, z);
					world.getBlockAt(temp).setType(Material.AIR);
				}
			}
		}
		
		//generating the plot without border & way
		
	}
	
}
