package de.plots.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

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
        
        cfg.addDefault("waysize", 3);
        cfg.addDefault("plotsize", 32);
        cfg.addDefault("claimedBoarderMaterial", 126);
        cfg.addDefault("unclaimedBoarderMaterial", 44);
        cfg.addDefault("wayMaterial", 155);
        cfg.addDefault("grasshight", 5);
        cfg.addDefault("stonehight", 65);
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
        PlotInformation.claimedBorderMaterial = getClaimedBoarderMaterial();
        PlotInformation.unclaimedBorderMaterial = getUnclaimedBoarderMaterial();
        PlotInformation.grasshight = getGrasshight();
        PlotInformation.stonehight = getStonehight();
        PlotInformation.wayMaterial = getWayMaterial();
       
    }
    private static Material getWayMaterial() {
    	YamlConfiguration cfg = getConfiguration();
    	@SuppressWarnings("deprecation")
		ItemStack item = new ItemStack(cfg.getInt("wayMaterial"));
    	return item.getType(); 
	}

	private static int getGrasshight() {
		YamlConfiguration cfg = getConfiguration();
		return cfg.getInt("grasshight");
	}
    private static int getStonehight() {
		YamlConfiguration cfg = getConfiguration();
		return cfg.getInt("stonehight");
	}
	@SuppressWarnings("deprecation")
	private static Material getUnclaimedBoarderMaterial() {
    	YamlConfiguration cfg = getConfiguration();
    	return new ItemStack(cfg.getInt("unclaimedBoarderMaterial")).getType();
	}

	@SuppressWarnings("deprecation")
	private static Material getClaimedBoarderMaterial() {
    	YamlConfiguration cfg = getConfiguration();
    	return new ItemStack(cfg.getInt("claimedBoarderMaterial")).getType();
    }
    private static Location getPlotWorldSpawn() {
    	YamlConfiguration cfg = getConfiguration();	
    	double x = cfg.getDouble("x");
    	double y = cfg.getDouble("y");
    	double z = cfg.getDouble("z");
    	float pitch = (float) cfg.getDouble("pitch");
    	float yaw = (float) cfg.getDouble("yaw");
    	String worldname = cfg.getString("worldname");
    	return new Location(Bukkit.getWorld(worldname), x, y, z, yaw, pitch);
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
    	cfg.set("x", loc.getX());
    	cfg.set("y", loc.getY());
    	cfg.set("z", loc.getZ());
    	cfg.set("pitch", loc.getPitch());
    	cfg.set("yaw", loc.getYaw());
    	cfg.set("worldname", loc.getWorld().getName());
    	try {
			cfg.save(getConfigFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @SuppressWarnings("deprecation")
	public static void setClaimedBoarderMaterial(Material mat) {
    	YamlConfiguration cfg = getConfiguration();
    	cfg.set("claimedBoarderMaterial", mat.getId());
    	try {
			cfg.save(getConfigFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @SuppressWarnings("deprecation")
	public static void setUnclaimedBoarderMaterial(Material mat) {
    	YamlConfiguration cfg = getConfiguration();
    	cfg.set("unclaimedBoarderMaterial", mat.getId());
    	try {
			cfg.save(getConfigFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @SuppressWarnings("deprecation")
	public static void setWayMaterial(Material mat) {
    	YamlConfiguration cfg = getConfiguration();
    	cfg.set("wayMaterial", mat.getId());
    	try {
			cfg.save(getConfigFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
