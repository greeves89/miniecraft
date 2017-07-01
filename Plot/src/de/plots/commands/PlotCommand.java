package de.plots.commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.plots.config.PlotInformationConfig;
import de.plots.generation.GenerationGUI;
import de.plots.generation.PlotGeneration;
import de.plots.generation.PlotObject;
import de.plots.generation.SinglePlotGeneration;
import de.plots.generation.WorldGeneration;
import de.plots.main.main;
import de.plots.mysql.plotmysql;
import de.plots.utils.PlotInformation;
import de.plots.utils.WorldTeleportation;
import de.plots.utils.plotjava;

public class PlotCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
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
						WorldTeleportation.teleportEntityInWorld((Player) sender, args[1].toString());
					} else if (args.length == 1){
						p.teleport(PlotInformation.plotworld);
					} else {
						sender.sendMessage("§cUsage: /plot worldtp <worldname>");
					}
				}
			} else if (args[0].equalsIgnoreCase("createplots")) {
				if (sender.hasPermission("plot.command.createplots")) {
					
					//PlotGeneration.generatePlots(((Player) sender).getWorld(), ((Player) sender).getLocation(), main.plotsize + main.waysize, 3);
					GenerationGUI.openInventory((Player) sender);
				}
			} else if (args[0].equalsIgnoreCase("autoclaim")) {
				if (sender.hasPermission("plot.command.claim")) {
					plotmysql.autoClaimPlot((Player) sender);
				}
			} else if (args[0].equalsIgnoreCase("tp")) {
				if (sender.hasPermission("plot.command.claim")) {
					plotmysql.portToPlot((Player) sender);
				}
			} else if (args[0].equalsIgnoreCase("claim")) {
				if (sender.hasPermission("plot.command.claim")) {
					plotmysql.claimPlot((Player) sender);
				}
			} else if (args[0].equalsIgnoreCase("unclaim")) {
				if (sender.hasPermission("plot.command.unclaim")) {
					plotmysql.unclaimPlot(p);
				}
			} else if (args[0].equalsIgnoreCase("reset")) {
				if (sender.hasPermission("plot.command.reset")) {
					ArrayList<Location> locations = plotmysql.getPlotLocation(plotmysql.getPlotID(p));
					Location min = locations.get(0);
					Location max = locations.get(1);
					SinglePlotGeneration.generateSinglePlot(PlotInformation.plotworld.getWorld(), min, max, PlotInformation.waysize, 5, 65, PlotGeneration.waymaterial, PlotGeneration.boardermaterial);
				}
			} else if (args[0].equalsIgnoreCase("check")) {
				if (sender.hasPermission("plot.command.reset")) {
					p.sendMessage("§9Registrierte Plots: §c" + PlotGeneration.plotLists.size());
				}
			} else if (args[0].equalsIgnoreCase("getInfo")) {
				if (sender.hasPermission("plot.command.getid")) {
					int plotid = plotjava.getPlotID(p);
					String owner = plotjava.getPlotOwner(plotid);
					if (owner == null) {
						owner = "Kein Besitzer";
					}
					if (plotid != -1) {
						p.sendMessage("§c--- PlotInformationen ---");
						p.sendMessage("§9ID: §c" + plotid);
						p.sendMessage("§9Owner: §c" + owner);
						
					} else {
						p.sendMessage("§cDu stehst auf keinem Plot!");
					}
					
				}
			} else if (args[0].equalsIgnoreCase("insertPlotworld")) {
				if (sender instanceof Player) {
					if (p.hasPermission("plot.command.insertworld")) {
						PlotInformationConfig.setPlotWorldSpawnlocation(p.getLocation());
					}	
				}
			}
			
		}
		
		return true;
	}

}
