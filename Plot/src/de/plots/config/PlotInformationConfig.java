package de.plots.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import de.plots.mysql.mysql;
import de.plots.utils.PlotInformation;

public class PlotInformationConfig {
	private static File getConfigFile() {
        return new File("plugins/PlotPlugin", "info.yml");
    }

    private static YamlConfiguration getConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());
    }

    public static void setConfig() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true); 
        
        cfg.addDefault("worldname", "plotworld");
        cfg.addDefault("x", 0);
        cfg.addDefault("y", 70);
        cfg.addDefault("z", 0);
        
        cfg.addDefault("waysize", 3);
        cfg.addDefault("plotsize", 32);
        try {
            cfg.save(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readConfig() {
        
        PlotInformation.plotworld = getPlotWorldSpawn();
        PlotInformation.plotsize = getPlotsize();
        PlotInformation.waysize = getWaysize();
       
    }
    private static Location getPlotWorldSpawn() {
    	YamlConfiguration cfg = getConfiguration();	
    	int x = cfg.getInt("x");
    	int y = cfg.getInt("y");
    	int z = cfg.getInt("z");
    	String worldname = cfg.getString("worldname");
    	return new Location(Bukkit.getWorld(worldname), x, y, z);
    }
    private static int getWaysize() {
    	YamlConfiguration cfg = getConfiguration();	
    	return cfg.getInt("waysize");
    }
    private static int getPlotsize() {
    	YamlConfiguration cfg = getConfiguration();	
    	return cfg.getInt("plotsize");
    }
    public static void setPlotWorldSpawnlocation(Location loc) {
    	YamlConfiguration cfg = getConfiguration();	
    	cfg.set("x", loc.getBlockX());
    	cfg.set("y", loc.getBlockY());
    	cfg.set("z", loc.getBlockZ());
    	cfg.set("worldname", loc.getWorld().getName());
    }
}
