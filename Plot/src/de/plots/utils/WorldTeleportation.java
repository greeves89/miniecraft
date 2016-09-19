package de.plots.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class WorldTeleportation {

	public static void teleportEntityInWorld(LivingEntity e, String worldname, boolean check) {
		World w = Bukkit.getWorld(worldname);
		if (w != null) {
			if (check) {
				e.teleport(w.getSpawnLocation());
				e.sendMessage("§8Du wurdest in eine andere Welt teleportiert!");
			}
		} else {
			if (e instanceof Player) {
				e.sendMessage("§8Du willst in eine Welt welche nicht existiert!");
			}
		}
	}
	
}
