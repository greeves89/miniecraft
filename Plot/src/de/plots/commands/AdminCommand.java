package de.plots.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.plots.utils.AdminClass;
import de.plots.utils.messages;

public class AdminCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				AdminClass.autoAdmin(p);
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("join")) {
					AdminClass.addPlayer(p);
				} else if (args[0].equalsIgnoreCase("leave")) {
					AdminClass.removePlayer(p);
				} else if (args[0].equalsIgnoreCase("help")) {
					p.sendMessage("§c/admin <join,leave> (player)");
				} else if (args[0].equalsIgnoreCase("list")) {
					if (p.hasPermission("server.admin")) {
						String players = "";
						for (int i = 0; i < AdminClass.inAdminmode.size(); i++) {
							players = players + ", " + AdminClass.inAdminmode.get(i);
						}
						p.sendMessage(messages.prefix + " §cSpieler im Adminmode: " + players);
					}
				} else {
					if (p.hasPermission("server.admin")) {
						p.sendMessage("§c/admin <join,leave> (player)");
					}
				}
			} else if (args.length == 2) {
				Player target = Bukkit.getPlayer(args[1]);
				if (target != null) {
					if (args[0].equalsIgnoreCase("join")) {
						AdminClass.addPlayer(target);
					} else if (args[0].equalsIgnoreCase("leave")) {
						AdminClass.removePlayer(target);
					} else if (args[0].equalsIgnoreCase("auto")) {
						AdminClass.autoAdmin(target);
					}
				}
			} else {
				
			}
		}
		
		return true;
	}

}
