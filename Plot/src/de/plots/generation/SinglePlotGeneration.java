package de.plots.generation;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class SinglePlotGeneration {

	/**
	 * Notice that the min and max parameter is the totaly outside of the plot!
	 * Notice that widht is the amiunt of Block within the way and border
	 * (normally 37)
	 * 
	 * @author Eric D.
	 */

	private static ArrayList<Material> ores = new ArrayList<>();
	
	public static void generateSinglePlot(World world, Location min, Location max, int way, int grasshight,
			int stonehight, Material wayMaterial, Material boarderMaterial) {

		// Removing every single Block at the Plot

		for (int x = min.getBlockX(); x <= max.getBlockX() - 1; x++) {
			for (int z = min.getBlockZ(); z <= max.getBlockZ() - 1; z++) {
				for (int y = 0; y <= 130; y++) {
					Location temp = new Location(world, x, y, z);
					world.getBlockAt(temp).setType(Material.AIR);
				}
			}
		}

		// generating the way

		for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
			for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
				for (int y = 1; y < stonehight + grasshight; y++) {
					// Generating the way blocks
					Location temp = new Location(world, x, y, z);
					world.getBlockAt(temp).setType(wayMaterial);
				}
			}
		}
		// generating the stone
		for (int x = min.getBlockX() + way; x < max.getBlockX() - way; x++) {
			for (int z = min.getBlockZ() + way; z < max.getBlockZ() - way; z++) {
				for (int y = 2; y < stonehight + grasshight; y++) {
					// Generating the stone blocks
					Location stone = new Location(world, x, y, z);
					world.getBlockAt(stone).setType(Material.STONE);
				}
			}
		}
		// generating the grass
		for (int x = min.getBlockX() + way; x < max.getBlockX() - way; x++) {
			for (int z = min.getBlockZ() + way; z < max.getBlockZ() - way; z++) {
				for (int y = stonehight; y < stonehight + grasshight; y++) {
					// Generating the grass blocks
					Location grass = new Location(world, x, y, z);
					world.getBlockAt(grass).setType(Material.GRASS);
				}
			}
		}
		for (int x = min.getBlockX() + way; x < max.getBlockX() - way; x++) {
			for (int z = min.getBlockZ() + way; z < max.getBlockZ() - way; z++) {
				// Generating the boarder blocks
				Location boarder = new Location(world, x, stonehight + grasshight, z);
				world.getBlockAt(boarder).setType(boarderMaterial);
			}
		}
		for (int x = min.getBlockX() + way + 1; x < max.getBlockX() - way - 1; x++) {
			for (int z = min.getBlockZ() + way + 1; z < max.getBlockZ() - way - 1; z++) {
				int y = stonehight + grasshight;
				// Removing the other Blocks blocks
				Location boarder = new Location(world, x, y, z);
				world.getBlockAt(boarder).setType(Material.AIR);

			}
		}
		for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
			for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
				//Create the Bedrock
				Location bedrock = new Location(world, x, 1, z);
				world.getBlockAt(bedrock).setType(Material.BEDROCK);

			}
		}
		fillMaterials();
		for (int x = min.getBlockX() + way + 1; x < max.getBlockX() - way - 1; x++) {
			for (int z = min.getBlockZ() + way + 1; z < max.getBlockZ() - way - 1; z++) {
				for (int y = 1; y < 32; y++) {
					//Generate Materials
					Random rn = new Random();
					if (rn.nextInt(10) + 1 == 7) {
						Location oreloc = new Location(world, x, y, z);
						world.getBlockAt(oreloc).setType(getRandomMaterial());
					}
				}
			}
		} 
	}
	public static Material getRandomMaterial() {
		Random rn = new Random();
		return ores.get(rn.nextInt(ores.size()));
	}
	public static void fillMaterials() {
		ores.add(Material.DIAMOND_ORE);
		
		ores.add(Material.IRON_ORE);
		ores.add(Material.IRON_ORE);
		ores.add(Material.IRON_ORE);
		ores.add(Material.IRON_ORE);
		ores.add(Material.IRON_ORE);
		ores.add(Material.IRON_ORE);
		
		ores.add(Material.GOLD_ORE);
		ores.add(Material.GOLD_ORE);
		ores.add(Material.GOLD_ORE);
		
		ores.add(Material.COAL_ORE);
		ores.add(Material.COAL_ORE);
		ores.add(Material.COAL_ORE);
		ores.add(Material.COAL_ORE);
		ores.add(Material.COAL_ORE);
		ores.add(Material.COAL_ORE);
		ores.add(Material.COAL_ORE);
		
	}

}
