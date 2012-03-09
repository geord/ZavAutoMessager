package com.zavteam.plugins.configs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.zavteam.plugins.Main;

public class VersionConfig {
	public Main plugin;
	public VersionConfig(Main instance) {
		plugin = instance;
	}
	FileConfiguration config;
	boolean upToDate;
	public void loadConfig() {
		BufferedInputStream versionConfigStream = null;
		try {
			versionConfigStream = new BufferedInputStream(new URL("https://sites.google.com/site/zachoooo/version.yml").openStream());
		} catch (MalformedURLException e) {
			plugin.log.warning("Please Contact the developer regarding this error.");
			e.printStackTrace();
		} catch (IOException e) {
			plugin.log.warning("Please Contact the developer regarding this error.");
			e.printStackTrace();
		}
		if (versionConfigStream != null) {
			config = YamlConfiguration.loadConfiguration(versionConfigStream);
		} else {
			plugin.log.warning(this + " was unable to retrieve current version.");
		}	
	}
	public String getVersion() {
		handleUpToDateness();
		return config.getString("version");
	}
	public void handleUpToDateness() {
		if(!upToDate) {
			loadConfig();
		}
	}
}
