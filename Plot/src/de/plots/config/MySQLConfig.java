package de.plots.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import de.plots.mysql.mysql;



public class MySQLConfig {
	private static File getConfigFile() {
        return new File("plugins/PlotPlugin", "mysql.yml");
    }

    private static YamlConfiguration getConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());
    }

    public static void setConfig() {
        YamlConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true); 
        
        cfg.addDefault("host", "host");
        cfg.addDefault("port", "3306");
        cfg.addDefault("database", "database");
        cfg.addDefault("username", "user");
        cfg.addDefault("password", "123");
        try {
            cfg.save(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readConfig() {
        YamlConfiguration cfg = getConfiguration();	
        
        mysql.username = cfg.getString("username");
        mysql.password = cfg.getString("password");
        mysql.port = cfg.getString("port");
        mysql.host = cfg.getString("host");
        mysql.database = cfg.getString("database");
    }
}
