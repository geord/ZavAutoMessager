package com.zavteam.plugins;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.zavteam.plugins.configs.MainConfig;

public class Main extends JavaPlugin {
	
	boolean messageToggle;
	
	List<String> messages = new ArrayList<String>();
	
	String[] cutBroadcastList;
	
	protected FileConfiguration config;
	
	File ignoreFile;
	
	protected FileConfiguration versionConfig;
	
	protected FileConfiguration ignoreConfig;
	
	Logger log = Logger.getLogger("Minecraft");
	
	BufferedInputStream versionConfigStream;
	
	InputStream defaultIgnoreConfigStream;
	
	List<String> ignorePlayers = new ArrayList<String>();
	
	int messageIt;
	
	YamlConfiguration defaultIgnoreConfig;
	
	RunnableMessager rm = new RunnableMessager(this);
	
	MainConfig MConfig = new MainConfig(this);
	
	@Override
	public void onDisable() {
		log.info(this + " has been disabled");

	}

	@Override
	public void onEnable() {
		autoReload();
		reloadIgnoreConfig();
		getVersionConfig();
		getCommand("automessager").setExecutor(new Commands(this));
		getCommand("am").setExecutor(new Commands(this));
		getServer().getPluginManager().registerEvents(new ZavListener(this), this);
		log.info(this + " has been enabled");
		log.info(this + ": Sending messages is now set to " + MConfig.getEnabled());
		if (!(getDescription().getVersion().equals(versionConfig.getString("version")))) {
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
		getServer().getScheduler().cancelTasks(this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, rm, 0L, (long) MConfig.getDelay());
	}
	void addMessage(String m) {
		messages.add(m);
		MConfig.set("messages", messages);
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
		ignorePlayers = config.getStringList("players");
		saveIgnoreConfig();
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
			log.warning("Please Contact the developer regarding this error.");
			e.printStackTrace();
		} catch (IOException e) {
			log.warning("Please Contact the developer regarding this error.");
			e.printStackTrace();
		}
		if (versionConfigStream != null) {
			versionConfig = YamlConfiguration.loadConfiguration(versionConfigStream);
		} else {
			log.warning(this + " was unable to retrieve current version.");
		}
		return versionConfig;
	}
	void displayMessage(String string, String[] stringArray) {
		boolean chatWrapEnabled = MConfig.getChatWrap();
		boolean permissionsBV = MConfig.getPermissionEnabled();
		if (permissionsBV) {
			for (Player player : getServer().getOnlinePlayers()) {
				if (player.hasPermission("zavautomessager.see") || !(ignorePlayers.contains(player.getName()))) {
					if (chatWrapEnabled) {
						for (String s : stringArray) {
							player.sendMessage(s);
						}
					} else {
						player.sendMessage(string);
					}
				}
				log.info(string);
			}
		} else {
			for (Player player : getServer().getOnlinePlayers()) {
				if (!ignorePlayers.contains(player.getName())) {
					if (chatWrapEnabled) {
						for (String s : stringArray) {
							player.sendMessage(s);
						}
					} else {
						player.sendMessage(string);
					}
				}
			}
			log.info(string);
		}
	}
	void listPage(int i, CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "ZavAutoMessager Messages Page: " + i);
		int pagenumber = (i-1) * 5;
		for (int i2 = pagenumber; i2 > (pagenumber + 4); i2++) {
			sender.sendMessage(ChatColor.GOLD + String.valueOf(i2 + 1) + ". " + messages.get(i2));
		}
	}
}