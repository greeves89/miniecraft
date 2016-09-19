package de.plots.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.plots.generation.PlotGeneration;

public class createCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			
			if (args[0].equalsIgnoreCase("createworld")) {
				if (args.length == 2) {
					
				} else {
					sender.sendMessage("");
				}
			}
			
		}
		
		return true;
	}

}
