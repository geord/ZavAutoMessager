package com.zavteam.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public String chatFormat;
	public String chatString;
	protected FileConfiguration config;
	public int delay;
	public String freeVariable;
	Logger log = Logger.getLogger("Minecraft");
	public int messageIt;
	public List<String> messages = new ArrayList<String>();
	public boolean messageRandom;
	public boolean messageToggle;
	public boolean permissionsBV;
	Random random = new Random();
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
		messageRandom = config.getBoolean("messageinrandomorder");
		chatFormat = config.getString("chatformat", "[&6AutoMessager&f]: %msg");
		delay = delay * 20;
		permissionsBV = config.getBoolean("permissionsenabled", false);
		saveConfig();
		getCommand("automessager").setExecutor(new Commands(this));
		getServer().getScheduler().scheduleSyncRepeatingTask(this, rm, 0L, (long) delay);
		log.info(this + " has been enabled");
		log.info(this + ": Sending messages is now set to " + messageToggle);
		log.info("Thank you for using " + this + " by the ZavTeam!");
	}
	public void autoReload() {
		reloadConfig();
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
	}

}