package de.plots.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.plots.commands.createCommand;

public class main extends JavaPlugin {
 
	public void onEnable() {
		registerEvents();
		registerCommands();
		
		System.out.println("[Plots] wurde gestartet!");
	}
	
	public void onDisable() {
		System.out.println("[Plots] wurde deaktiviert!");
	}
	private void registerCommands() {
		
		this.getCommand("create").setExecutor(new createCommand());
	}
	private void registerEvents() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
	}
	
}
