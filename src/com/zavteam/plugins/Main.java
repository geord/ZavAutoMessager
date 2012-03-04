package com.zavteam.plugins;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	String chatFormat;
	String chatString;
	protected FileConfiguration config;
	File ignoreFile;
	protected FileConfiguration versionConfig;
	protected FileConfiguration ignoreConfig;
	private int delay;
	String freeVariable;
	Logger log = Logger.getLogger("Minecraft");
	int messageIt;
	List<String> messages = new ArrayList<String>();
	BufferedInputStream versionConfigStream;
	InputStream defaultIgnoreConfigStream;
	List<String> ignorePlayers = new ArrayList<String>();
	boolean messageRandom;
	YamlConfiguration defaultIgnoreConfig;
	String version = "v1.7";
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
		versionConfig = getVersionConfig();
		ignoreConfig = getIgnoreConfig();
		config.options().copyDefaults(true);
		messages = config.getStringList("messages");
		delay = config.getInt("delay", 60);
		messageToggle = config.getBoolean("enabled", true);
		messageRandom = config.getBoolean("messageinrandomorder");
		chatFormat = config.getString("chatformat", "[&6AutoMessager&f]: %msg");
		delay = delay * 20;
		permissionsBV = config.getBoolean("permissionsenabled", false);
		ignorePlayers = ignoreConfig.getStringList("players");
		saveConfig();
		saveIgnoreConfig();
		getCommand("automessager").setExecutor(new Commands(this));
		getServer().getScheduler().scheduleSyncRepeatingTask(this, rm, 0L, (long) delay);
		log.info(this + " has been enabled");
		log.info(this + ": Sending messages is now set to " + messageToggle);
		if (!(version.equals(versionConfig.getString("version")))) {
			log.info(this + " is not up to date. Check the latest version on BukkitDev.");
		} else {
			log.info(this + " is up to date!");
		}
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
	void reloadIgnoreConfig() {
		if (ignoreFile == null) {
			ignoreFile = new File(getDataFolder(), "ignore.yml");
		}
		ignoreConfig = YamlConfiguration.loadConfiguration(ignoreFile);
		defaultIgnoreConfigStream = getResource("ignore.yml");
		if (defaultIgnoreConfigStream != null) {
			defaultIgnoreConfig = YamlConfiguration.loadConfiguration(defaultIgnoreConfigStream);
			ignoreConfig.setDefaults(defaultIgnoreConfig);
		}
	}
	FileConfiguration getIgnoreConfig() {
		if (ignoreConfig == null) {
			reloadIgnoreConfig();
		}
		return ignoreConfig; 
	}
	void saveIgnoreConfig() {
		if (ignoreConfig == null || ignoreFile == null) {
			log.severe(this + " is unable to save the config.");
			return;
		}
		try {
			ignoreConfig.save(ignoreFile);
		} catch (IOException e) {
			e.printStackTrace();
			log.severe(this + " is unable to save config.");
		}
	}
	public FileConfiguration getVersionConfig() {
		try {
			versionConfigStream = new BufferedInputStream(new URL("https://sites.google.com/site/zachoooo/version.yml").openStream());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (versionConfigStream != null) {
			versionConfig = YamlConfiguration.loadConfiguration(versionConfigStream);
		} else {
			log.warning(this + " was unable to retrieve current version.");
		}
		return versionConfig;
	}
}