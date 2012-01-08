package com.zavteam.plugins;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	private final static String noPerm = ChatColor.RED + "You do not have permission to do this";
	//private static int Interval, taskId = -1, counter = 0;
	public Main plugin;
	public Commands(Main instance) {
		plugin = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				if (sender.hasPermission("zavautomessager.view")) {
					sender.sendMessage(ChatColor.GOLD + "========= ZavAutoMessager Help =========");
					sender.sendMessage(ChatColor.GOLD + "1. /automessager reload - Reloads config");
					sender.sendMessage(ChatColor.GOLD + "2. /automessager on - Start the messages");
					sender.sendMessage(ChatColor.GOLD + "2. /automessager off - Stops the messages");
					sender.sendMessage(ChatColor.GOLD + "========================================");
				} else {
					sender.sendMessage(noPerm);
				}
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("zavautomessager.reload")) {
						plugin.reloadConfig();
						plugin.messages = plugin.config.getStringList("messages");
						plugin.delay = plugin.config.getInt("delay", 60);
						plugin.chatFormat = plugin.config.getString("chatformat", "[&6ZavAutoMessager&f]: %msg");
						plugin.delay = plugin.delay * 20;
						plugin.permissionsBV = plugin.config.getBoolean("permissions", false);
						plugin.saveConfig();
					} else {
						sender.sendMessage(noPerm);
					}
				} else if (args[0].equalsIgnoreCase("on")) {
					if (sender.hasPermission("zavautomessager.toggle")) {
						plugin.messageToggle = true;
					} else {
						sender.sendMessage(noPerm);
						sender.sendMessage(ChatColor.GREEN + "ZavAutoMessage is now on");
					}
				} else if (args[0].equalsIgnoreCase("off")) {
					if (sender.hasPermission("zavautomessager.toggle")) {
						plugin.messageToggle = false;
						sender.sendMessage(ChatColor.GREEN + "ZavAutoMessage is now off");
					} else {
						sender.sendMessage(noPerm);
					}
				}
			}
		}
		return false;
	}
}
