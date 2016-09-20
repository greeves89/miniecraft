package de.plots.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.plots.generation.PlotGeneration;
import de.plots.generation.SinglePlotGeneration;
import de.plots.generation.WorldGeneration;
import de.plots.main.main;
import de.plots.utils.WorldTeleportation;

public class PlotCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			if (args[0].equalsIgnoreCase("createworld")) {
				if (sender.hasPermission("plot.command.createworld")) {
					if (args.length == 2) {
						WorldGeneration.createVoidWorld(args[1].toString(), sender);
					} else {
						sender.sendMessage("§cUsage: /plot createworld <worldname>");
					}
				}
			} else if (args[0].equalsIgnoreCase("worldtp")) {
				if (sender.hasPermission("plot.command.worldtp")) {
					if (args.length == 2) {
						WorldTeleportation.teleportEntityInWorld((Player) sender, args[1].toString(), true);
					} else {
						sender.sendMessage("§cUsage: /plot worldtp <worldname>");
					}
				}
			} else if (args[0].equalsIgnoreCase("createplots")) {
				if (sender.hasPermission("plot.command.createplots")) {
					PlotGeneration.generatePlots(((Player) sender).getWorld(), ((Player) sender).getLocation(), main.plotsize + main.waysize, 3);
				}
			}
			
		}
		
		return true;
	}

}
