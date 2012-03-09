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
	public void loadConfig() {
		plugin.reloadConfig();
		config = plugin.getConfig();
		config.options().copyDefaults(true);
		plugin.saveConfig();
		plugin.messageToggle = getEnabled();
		plugin.messages = getMessages();
	}
	public String getChatFormat() {
		return config.getString("chatformat");
	}

	public int getDelay() {
		return config.getInt("delay");
	}

	public List<String> getMessages() {
		return config.getStringList("messages");
	}
	public boolean getMessageRandom() {
		return config.getBoolean("messageinrandomorder");
	}
	public boolean getChatWrap() {
		return config.getBoolean("wordwrap");
	}
	public boolean getEnabled() {
		return config.getBoolean("enabled");
	}
	public boolean getPermissionEnabled() {
		return config.getBoolean("permissionsenabled");
	}
	public void set(String s, Object o) {
		config.set(s, o);
		plugin.saveConfig();
		loadConfig();
	}
}