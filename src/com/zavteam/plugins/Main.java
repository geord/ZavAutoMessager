package com.zavteam.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	protected FileConfiguration config;
	List<String> messages = new ArrayList();
	@Override
	public void onDisable() {
		saveConfig();
		log.info(this + " has been disabled");
		
	}

	@Override
	public void onEnable() {
		
		log.info(this + " has been enabled");
	}
	public void fullReload() {
		saveConfig();
		reloadConfig();
		messages = config.getStringList("messages");
	}

}