package com.zavteam.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	Random random = new Random();
	Logger log = Logger.getLogger("Minecraft");
	protected FileConfiguration config;
	public List<String> messages = new ArrayList();
	public int delay;
	RunnableMessager rm = new RunnableMessager(this);
	@Override
	public void onDisable() {
		saveConfig();
		log.info(this + " has been disabled");
		
	}

	@Override
	public void onEnable() {
		config = getConfig();
		config.options().copyDefaults(true);
		messages = config.getStringList("messages");
		for (String message : messages) {
			message = message.replace("&", "\u00A7");
		}
		delay = config.getInt("delay", 60);
		delay = delay * 20;
		getServer().getScheduler().scheduleSyncRepeatingTask(this, rm, 0L, (long) delay);
		log.info(this + " has been enabled");
	}

}