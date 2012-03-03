package com.zavteam.plugins;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	private final static String noPerm = ChatColor.RED + "You do not have permission to do this.";
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
					plugin.messageIt = 0;
					plugin.autoReload();
					plugin.messages = plugin.config.getStringList("messages");
					sender.sendMessage(ChatColor.RED + "This reload command ONLY affects messages!");
					sender.sendMessage(ChatColor.GREEN + "ZavAutoMessager's config has been reloaded.");
				} else {
					sender.sendMessage(noPerm);
				}
			} else if (args[0].equalsIgnoreCase("on")) {
				if (sender.hasPermission("zavautomessager.toggle")) {
					if (plugin.messageToggle) {
						sender.sendMessage(ChatColor.RED + "Messages are already enabled");
					} else {
						plugin.messageToggle = true;
						plugin.config.set("enabled", plugin.messageToggle);
						sender.sendMessage(ChatColor.GREEN + "ZavAutoMessager is now on");
					}
				} else {
					sender.sendMessage(noPerm);
				}
			} else if (args[0].equalsIgnoreCase("off")) {
				if (sender.hasPermission("zavautomessager.toggle")) {
					if (!plugin.messageToggle) {
						sender.sendMessage(ChatColor.RED + "Messages are already disabled");
					} else {
						plugin.messageToggle = false;
						plugin.config.set("enabled", plugin.messageToggle);
						sender.sendMessage(ChatColor.GREEN + "ZavAutoMessager is now off");
					}
				} else {
					sender.sendMessage(noPerm);
				}
			} else if (args[0].equalsIgnoreCase("add")) {
				if (sender.hasPermission("zavautomessager.add")) {
					if (args.length < 2) {
						sender.sendMessage(ChatColor.RED + "You need to enter a chat message to add.");
					} else {
						plugin.freeVariable = "";
						for (int i = 1; i < args.length; i++) {
							plugin.freeVariable = plugin.freeVariable + args[i] + " ";
						}
						plugin.freeVariable = plugin.freeVariable.substring(0, plugin.freeVariable.length() - 1);
						plugin.messageIt = 0;
						plugin.messages.add(plugin.freeVariable);
						plugin.config.set("messages", plugin.messages);
						plugin.saveConfig();
						sender.sendMessage(ChatColor.GREEN + "Your message has been added to the message list.");
					}
				} else {
					sender.sendMessage(noPerm);
				}
			} else {
				sender.sendMessage(ChatColor.RED + "ZavAutoMessager did not recognize this command.");
				sender.sendMessage(ChatColor.RED + "Use /automessager help to get a list of commands!");
			}
		}
		return false;
	}
}
