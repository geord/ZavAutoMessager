package com.zavteam.plugins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	String chatFormat;
	String chatString;
	protected FileConfiguration config;
	private int delay;
	String freeVariable;
	Logger log = Logger.getLogger("Minecraft");
	int messageIt;
	List<String> messages = new ArrayList<String>();
	boolean messageRandom;
	boolean messageToggle;
	boolean permissionsBV;
	Random random = new Random();
	RunnableMessager rm = new RunnableMessager(this);
	@Override
	public void onDisable() {
		autoReload();
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
	void autoReload() {
		reloadConfig();
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
	}
	void addMessage(String m) {
		messages.add(freeVariable);
		config.set("messages", messages);
		saveConfig();
	}
}