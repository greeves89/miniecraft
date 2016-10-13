package de.plots.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class WorldTeleportation {

	public static void teleportEntityInWorld(LivingEntity e, String worldname) {
		World w = Bukkit.getWorld(worldname);
		if (w != null) {
			e.teleport(w.getSpawnLocation());
			e.sendMessage("§8Du wurdest in die Welt §c" + worldname + " §8teleportiert!");
		}
	}
	
}
