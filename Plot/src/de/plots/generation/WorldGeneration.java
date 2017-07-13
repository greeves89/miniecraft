package de.plots.generation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;

import de.plots.config.PlotInformationConfig;

public class WorldGeneration {

	public static void createVoidWorld(String worldname, CommandSender sender) {
		
		if (Bukkit.getWorld(worldname) == null) {
			getWorldCreator(worldname).createWorld();
			World w = Bukkit.getWorld(worldname);
			if (w != null) {
				sender.sendMessage("§8Welt erfolgreich erstellt!");
				sender.sendMessage("§8Worldname: §c" + worldname);
				if (sender instanceof Player) {
					Player p = (Player) sender;
					p.teleport(w.getSpawnLocation());
					PlotInformationConfig.setPlotWorldSpawnlocation(p.getLocation());
				}
				
				PlotInformationConfig.readConfig();
			} else {
				sender.sendMessage("§cEs ist ein Problem beim Erstellen er Welt aufgetreten!");
			}
		} else {
			sender.sendMessage("§cDiese Welt existiert bereis!");
			sender.sendMessage("§8Worldname: §c" + worldname);
		}
		
	}
	public static WorldCreator getWorldCreator(String worldname) {
		WorldCreator wc = new WorldCreator(worldname);
		wc.type(WorldType.FLAT);
		wc.generator("0;0;0");
		return wc;
	}
	
}
