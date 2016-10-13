package de.plots.utils;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.mysql.jdbc.Messages;

public class AdminClass {

	private static ArrayList<Player> inAdminmode = new ArrayList<Player>();
	
	public static void addPlayer(Player p) {
		if (p.hasPermission("server.admin")) {
			if (inAdminmode.contains(p) == false) {
				inAdminmode.add(p);
				p.sendMessage(messages.prefix + " §cDu hast den Adminode verlassen!");
			}
		}
	}
	public static void removePlayer(Player p) {
		if (p.hasPermission("server.admin")) {
			if (inAdminmode.contains(p) == true) {
				inAdminmode.remove(p);
				p.sendMessage(messages.prefix + " §cDu hast den Adminode betreten!");
			}
		}
	}
	public static void autoAdmin(Player p) {
		//TODO:
	}
	
}
