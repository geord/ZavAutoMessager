package com.zavteam.plugins;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	@Override
	public void onDisable() {
		saveConfig();
		log.info(this + " has been disabled");
		
	}

	@Override
	public void onEnable() {
		
		log.info(this + " has been enabled");
	}

}