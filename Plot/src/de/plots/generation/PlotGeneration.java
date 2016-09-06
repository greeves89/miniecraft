package de.plots.generation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class PlotGeneration {

	/**
	 * Notice that the min and max parameter is the totaly outside of the plot!
	 * Notice that widht is the amiunt of Block within the way and border (normally 37)
	 * 
	 * @author Eric D.
	 */
	
	public static void generateSinglePlot(World world, Location min, Location max, int widht, int way, int grassheight, int stoneheight, Material bordermaterial, Material waymaterial) {
		
		//Removing every single Block at the Plot
		
		for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
			for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
				for (int y = 0; y <= 256; y++ ) {
					Location temp = new Location(world, x, y, z);
					world.getBlockAt(temp).setType(Material.AIR);
				}
			}
		}
		
		//generating the plot without border & way
		
		for (int x = min.getBlockX() + way; x < max.getBlockX() - way; x++) {
			for (int z = min.getBlockZ() + way; z < max.getBlockZ() - way; z++) {
				for (int y = 2; y < stoneheight; y++ ) {
					//Generating the stone blocks
					Location stone = new Location(world, x, y, z);
					world.getBlockAt(stone).setType(Material.STONE);
					
					//setting the Bedrock
					Location bedrock = new Location(world, x, 1, z);
					world.getBlockAt(bedrock).setType(Material.BEDROCK);
				}
			}
		}
	}
	
}
