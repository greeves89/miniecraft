package de.plots.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.plots.mysql.plotmysql;
import de.plots.utils.PlotInformation;
import de.plots.utils.plotjava;

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
				for (int y = 1; y < stonehight + grasshight; y++) {
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
		// Generating the boarder blocks
		for (int x = min.getBlockX() + way; x < max.getBlockX() - way; x++) {
			for (int z = min.getBlockZ() + way; z < max.getBlockZ() - way; z++) {
				Location boarder = new Location(world, x, stonehight + grasshight, z);
				world.getBlockAt(boarder).setType(boarderMaterial);
			}
		}

		// Removing the other boarder Blocks
		for (int x = min.getBlockX() + way + 1; x < max.getBlockX() - way - 1; x++) {
			for (int z = min.getBlockZ() + way + 1; z < max.getBlockZ() - way - 1; z++) {
				int y = stonehight + grasshight;
				Location boarder = new Location(world, x, y, z);
				world.getBlockAt(boarder).setType(Material.AIR);

			}
		}
		//Create the Bedrock
		for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
			for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
				
				Location bedrock = new Location(world, x, 0, z);
				world.getBlockAt(bedrock).setType(Material.BEDROCK);

			}
		}
		//Generate Materials and Ores
		fillMaterials();
		for (int x = min.getBlockX() + way + 1; x < max.getBlockX() - way - 1; x++) {
			for (int z = min.getBlockZ() + way + 1; z < max.getBlockZ() - way - 1; z++) {
				for (int y = 1; y < 32; y++) {
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
	private static void fillMaterials() {
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
	public static void unclaimPlot(int plotID, Player p) {
		Location min = plotmysql.getPlotLocation(plotID).get(0);
		Location max = plotmysql.getPlotLocation(plotID).get(1);
		SinglePlotGeneration.generateSinglePlot(PlotInformation.plotworld.getWorld(), min, max, PlotInformation.waysize, PlotInformation.grasshight, PlotInformation.stonehight, PlotInformation.wayMaterial, PlotInformation.unclaimedBorderMaterial);
		PlotGeneration.getCorrespondingPlotObject(plotmysql.getPlotID(p)).setOwner(null);
	}
	public static void claimPlot(Player p, int plotID) {
		PlotGeneration.getCorrespondingPlotObject(plotID).setOwner(p);
		setBoarder(plotID, true);
	}
	public static void setBoarder(int plotID, boolean claim) {
		ArrayList<Location> locs = plotmysql.getPlotLocation(plotID);
		Location min = locs.get(0);
		Location max = locs.get(1);
		int way = PlotInformation.waysize;
		int grasshight = PlotInformation.grasshight;
		int stonehight = PlotInformation.stonehight;
		World world = PlotInformation.plotworld.getWorld();
		Material materialToFill;
		if (claim) {
			materialToFill = PlotInformation.claimedBorderMaterial;
		} else {
			materialToFill = PlotInformation.unclaimedBorderMaterial;
		}
		// Generating the boarder blocks
		for (int x = min.getBlockX() + way; x < max.getBlockX() - way; x++) {
			for (int z = min.getBlockZ() + way; z < max.getBlockZ() - way; z++) {
				Location boarder = new Location(world, x, stonehight + grasshight, z);
				world.getBlockAt(boarder).setType(materialToFill);
			}
		}
		//DELETING THE MIDDLE AND MAKE THE BOARDER COMPLETE
		for (int x = min.getBlockX() + way + 1; x < max.getBlockX() - way - 1; x++) {
			for (int z = min.getBlockZ() + way + 1; z < max.getBlockZ() - way - 1; z++) {
				Location boarder = new Location(world, x, stonehight + grasshight, z);
				world.getBlockAt(boarder).setType(Material.AIR);
			}
		}
	}
}
