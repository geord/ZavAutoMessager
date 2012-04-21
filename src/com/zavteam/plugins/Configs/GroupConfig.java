package com.zavteam.plugins.configs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.zavteam.plugins.Main;

public class GroupConfig {
	public Main plugin;
	public GroupConfig(Main instance) {
		plugin = instance;
	}
	File groupFile;
	private FileConfiguration config;
	public void loadConfig() {
		InputStream defaultGroupConfigStream;
		if (groupFile == null) {
			groupFile = new File(plugin.getDataFolder(), "groups.yml");
		}
		config = YamlConfiguration.loadConfiguration(groupFile);
		defaultGroupConfigStream = plugin.getResource("groups.yml");
		if (defaultGroupConfigStream != null) {
			config.setDefaults(YamlConfiguration.loadConfiguration(defaultGroupConfigStream));
		}
		saveConfig();
	}
	public void saveConfig() {
		if (config == null || groupFile == null) {
			plugin.log.severe(this + " is unable to save the config.");
			return;
		}
		try {
			config.save(groupFile);
		} catch (IOException e) {
			e.printStackTrace();
			plugin.log.severe(this + " is unable to save config.");
		}
	}
	public void set(String s, Object o) {
		config.set(s, o);
		saveConfig();
	}
}
