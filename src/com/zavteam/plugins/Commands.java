package com.zavteam.plugins;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	private final static String noPerm = ChatColor.RED + "You do not have permission to do this";
	public Main plugin;
	public Commands(Main instance) {
		plugin = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("zavautomessager.view")) {
					sender.sendMessage(ChatColor.GOLD + "========= ZavAutoMessager Help =========");
					sender.sendMessage(ChatColor.GOLD + "1. /automessager reload - Reloads config");
					sender.sendMessage(ChatColor.GOLD + "2. /automessager on - Start the messages");
					sender.sendMessage(ChatColor.GOLD + "3. /automessager off - Stops the messages");
					sender.sendMessage(ChatColor.GOLD + "4. /automessager help - Displays this menu");
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
						sender.sendMessage(ChatColor.GREEN + "ZavAutoMessager's config has been reloaded.");
					} else {
						sender.sendMessage(noPerm);
					}
				} else if (args[0].equalsIgnoreCase("on")) {
					if (sender.hasPermission("zavautomessager.toggle")) {
						plugin.messageToggle = true;
						plugin.config.set("enabled", plugin.messageToggle);
						sender.sendMessage(ChatColor.GREEN + "ZavAutoMessager is now on");
					} else {
						sender.sendMessage(noPerm);
					}
				} else if (args[0].equalsIgnoreCase("off")) {
					if (sender.hasPermission("zavautomessager.toggle")) {
						plugin.messageToggle = false;
						plugin.config.set("enabled", plugin.messageToggle);
						sender.sendMessage(ChatColor.GREEN + "ZavAutoMessager is now off");
					} else {
						sender.sendMessage(noPerm);
					}
				} else if (args[0].equalsIgnoreCase("add")) {
					if (sender.hasPermission("zavautomessager.add")) {
						if (args.length < 1) {
							sender.sendMessage(ChatColor.RED + "You need to enter a message to add to the message list.");
						} else {
							plugin.freeVariable = "";
							for (String s : args) {
								plugin.freeVariable = plugin.freeVariable + s + " ";
							}
							plugin.freeVariable = plugin.freeVariable.substring(plugin.freeVariable.length() - 2);
							plugin.messages.add(plugin.freeVariable);
							plugin.config.set("messages", plugin.messages);
						}
					} else {
						sender.sendMessage(noPerm);
					}
				} else {
					sender.sendMessage(ChatColor.RED + "ZavAutoMessager did not recognize this command.");
				}
			}
		return false;
	}
}
