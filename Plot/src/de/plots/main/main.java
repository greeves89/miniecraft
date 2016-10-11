package de.plots.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.plots.commands.PlotCommand;
import de.plots.generation.PlotGeneration;
import de.plots.listener.PlotBuildBreak;
import de.plots.mysql.mysql;

public class main extends JavaPlugin {
 
	public static int waysize = 3;
	public static int plotsize = 32;
	
	public static boolean debug = true;
	
	public void onEnable() {
		registerEvents();
		registerCommands();
		
		//MySQL Connection
		mysql.connect();
		mysql.createTable();
		
		PlotGeneration.registerPlots();
		
		System.out.println("[Plots] wurde gestartet!");
	}
	
	public void onDisable() {
		
		mysql.close();
		
		System.out.println("[Plots] wurde deaktiviert!");
	}
	private void registerCommands() {
		
		this.getCommand("plot").setExecutor(new PlotCommand());
	}
	private void registerEvents() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		pm.registerEvents(new PlotBuildBreak(), this);
	}
}
