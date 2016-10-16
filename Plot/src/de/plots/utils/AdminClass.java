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
		} else {
			System.out.println("Somebody is trying to get in Adminmode without having access!");
			System.out.println("Playername: " + p.getName());
			System.out.println("UUID: " + p.getUniqueId().toString());
			System.out.println("IP: " + p.getAddress().getHostName());
		}
	}
	public static void removePlayer(Player p) {
		if (p.hasPermission("server.admin")) {
			if (inAdminmode.contains(p) == true) {
				inAdminmode.remove(p);
				p.sendMessage(messages.prefix + " §cDu hast den Adminode betreten!");
			}
		} else {
			System.out.println("Somebody is trying to get in Adminmode without having access!");
			System.out.println("Playername: " + p.getName());
			System.out.println("UUID: " + p.getUniqueId().toString());
			System.out.println("IP: " + p.getAddress().getHostName());
		}
	}
	public static void autoAdmin(Player p) {
		if (p.hasPermission("server.admin")) {
			if (inAdminmode.contains(p)) {
				inAdminmode.remove(p);
				p.sendMessage(messages.prefix + " §cDu hast den Adminode betreten!");
			} else if (!inAdminmode.contains(p)) {
				inAdminmode.add(p);
				p.sendMessage(messages.prefix + " §cDu hast den Adminode verlassen!");
			}
		}
	}
	public static boolean checkAdminmode(Player p) {
		return inAdminmode.contains(p);
	}
	
}
