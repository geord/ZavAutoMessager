package com.zavteam.plugins.messageshandler;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zavteam.plugins.Main;

public class MessagesHandler {
	public Main plugin;
	public MessagesHandler(Main instance) {
		plugin = instance;
	}
	public void handleMessage(String s, String[] sarray) {
		boolean chatWrapEnabled = plugin.MConfig.getChatWrap();
		boolean permissionsBV = plugin.MConfig.getPermissionEnabled();
		if (permissionsBV) {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (player.hasPermission("zavautomessager.see") || !(plugin.IConfig.getIgnorePlayers().contains(player.getName()))) {
					if (chatWrapEnabled) {
						for (String s1 : sarray) {
							player.sendMessage(s1);
						}
					} else {
						player.sendMessage(s);
					}
				}
				plugin.log.info(s);
			}
		} else {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (!plugin.IConfig.getIgnorePlayers().contains(player.getName())) {
					if (chatWrapEnabled) {
						for (String s1 : sarray) {
							player.sendMessage(s1);
						}
					} else {
						player.sendMessage(s);
					}
				}
			}
			plugin.log.info(s);
		}
	}
	public void addMessage(String m) {
		plugin.messages.add(m);
		plugin.MConfig.set("messages", plugin.messages);
	}
	public void listPage(int i, CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "ZavAutoMessager Messages Page: " + i);
		int pagenumber = (i-1) * 5;
		for (int i2 = pagenumber; i2 > (pagenumber + 4); i2++) {
			sender.sendMessage(ChatColor.GOLD + String.valueOf(i2 + 1) + ". " + plugin.messages.get(i2));
		}
	}
}
