package de.plots.generation;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.generator.ChunkGenerator;

public class WorldGeneration {

	public static void createVoidWorld(String worldname, CommandSender sender) {
		
		if (Bukkit.getWorld(worldname) == null) {
			WorldCreator wc = new WorldCreator(worldname);
//			String s = "-7118551805318844725";
//			long l = Long.parseLong(s);
//			wc.seed(l);
			wc.type(WorldType.FLAT);
			wc.generator("0;0;0");
			wc.createWorld();
			World w = Bukkit.getWorld(worldname);
			if (w != null) {
				sender.sendMessage("§8Welt erfolgreich erstellt!");
				sender.sendMessage("§8Worldname: �c" + worldname);
			} else {
				sender.sendMessage("�cEs ist ein Problem beim erstellen der Welt aufgetreten!");
			}
		} else {
			sender.sendMessage("�cDiese Welt existiert bereits!");
			sender.sendMessage("�8Worldname: �c" + worldname);
		}
		
	}
	
}
