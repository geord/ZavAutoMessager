package com.zavteam.plugins.configs;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.zavteam.plugins.Main;

public class MainConfig {
	// Main Config Handlers
	public Main plugin;
	private static FileConfiguration config;
	public MainConfig(Main instance) {
		plugin = instance;
	}
	boolean upToDate;
	public void loadConfig() {
		plugin.reloadConfig();
		config = plugin.getConfig();
		config.options().copyDefaults(true);
		plugin.saveConfig();
		upToDate = true;
	}
	public String getChatFormat() {
		handleUpToDateness();
		return config.getString("chatformat");
	}

	public int getDelay() {
		handleUpToDateness();
		return config.getInt("delay");
	}

	public List<String> getMessages() {
		handleUpToDateness();
		return config.getStringList("messages");
	}
	public boolean getMessageRandom() {
		handleUpToDateness();
		return config.getBoolean("messageinrandomorder");
	}
	public boolean getChatWrap() {
		handleUpToDateness();
		return config.getBoolean("wordwrap");
	}
	public boolean getEnabled() {
		handleUpToDateness();
		return config.getBoolean("enabled");
	}
	public boolean getPermissionEnabled() {
		handleUpToDateness();
		return config.getBoolean("permissionsenabled");
	}
	public void set(String s, Object o) {
		config.set(s, o);
		plugin.saveConfig();
		upToDate = false;
	}
	public void handleUpToDateness() {
		if (upToDate) {
			return;
		}
		loadConfig();
	}
}