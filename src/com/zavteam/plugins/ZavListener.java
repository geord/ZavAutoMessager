package com.zavteam.plugins;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ZavListener implements Listener {
	public Main plugin;
	public ZavListener(Main instance) {
		plugin = instance;
	}
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		if (plugin.getServer().getOfflinePlayers() == null) {
			plugin.MConfig.set("enabled", false);
		}
	}
	public void playerJoin(PlayerJoinEvent e) {
		plugin.MConfig.set("enabled", true);
	}
}
