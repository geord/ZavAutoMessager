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
	public List<String> messages = new ArrayList<String>();
	public int delay;
	public boolean messageToggle;
	public int messageIt;
	public String chatFormat;
	public String chatString;
	public boolean permissionsBV;
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
		delay = config.getInt("delay", 60);
		messageToggle = config.getBoolean("enabled", true);
		chatFormat = config.getString("chatformat", "[&6ZavAutoMessager&f]: %msg");
		delay = delay * 20;
		permissionsBV = config.getBoolean("permissions", false);
		saveConfig();
		getCommand("automessager").setExecutor(new Commands(this));
		getServer().getScheduler().scheduleSyncRepeatingTask(this, rm, 0L, (long) delay);
		log.info(this + " has been enabled");
		log.info(this + ": Sending messages is now set to " + messageToggle);
	}

}