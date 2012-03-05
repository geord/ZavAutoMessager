package com.zavteam.plugins;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

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
				sender.sendMessage(ChatColor.GOLD + "4. /automessager add <message> - Adds a message to the list");
				sender.sendMessage(ChatColor.GOLD + "5. /automessager ignore - Toggles ignoring messages");
				sender.sendMessage(ChatColor.GOLD + "6. /automessager broadcast <message> - Send a message now");
				sender.sendMessage(ChatColor.GOLD + "7. /automessager help - Displays this menu");
				sender.sendMessage(ChatColor.GOLD + "========================================");
			} else {
				sender.sendMessage(noPerm);
			}
		} else if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("reload")) {
				if (sender.hasPermission("zavautomessager.reload")) {
					plugin.messageIt = 0;
					plugin.autoReload();
					plugin.reloadIgnoreConfig();
					sender.sendMessage(ChatColor.RED + "This is not a full reload.");
					sender.sendMessage(ChatColor.RED + "To perform a full reload use /reload");
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
						plugin.saveConfig();
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
						plugin.saveConfig();
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
						plugin.freeVariable = plugin.freeVariable.trim();
						plugin.messageIt = 0;
						plugin.addMessage(plugin.freeVariable);
						sender.sendMessage(ChatColor.GREEN + "Your message has been added to the message list.");
					}
				} else {
					sender.sendMessage(noPerm);
				}
			} else if (args[0].equalsIgnoreCase("ignore")) {
				if (sender instanceof Player) {
					if (sender.hasPermission("zavautomessager.ignore")) {
						if (plugin.ignorePlayers.contains(sender.getName())) {
							plugin.ignorePlayers.remove(sender.getName());
							sender.sendMessage(ChatColor.GREEN + "You are no longer ignoring automatic messages");
						} else {
							plugin.ignorePlayers.add(sender.getName());
							sender.sendMessage(ChatColor.GREEN + "You are now ignoring automatic messages");
						}
						plugin.ignoreConfig.set("players", plugin.ignorePlayers);
						plugin.saveConfig();
					} else {
						sender.sendMessage(noPerm);
					}
				} else {
					plugin.log.info("The console cannot use this command.");
				}
			} else if (args[0].equalsIgnoreCase("broadcast")) {
				if (sender.hasPermission("zavautomessager.broadcast")) {
					if (args.length < 2) {
						sender.sendMessage(ChatColor.RED + "You must enter a broadcast message");
					} else {
						for (int i = 1; i < args.length; i++) {
							plugin.broadcastMessage = plugin.broadcastMessage + args[i] + " ";
						}
						plugin.broadcastMessage = plugin.broadcastMessage.trim();
						plugin.broadcastMessage = plugin.chatFormat.replace("%msg", plugin.broadcastMessage);
						plugin.broadcastMessage = plugin.broadcastMessage.replace("&", "\u00A7");
						plugin.cutBroadcastList = ChatPaginator.wordWrap(plugin.broadcastMessage, 53);
						if (plugin.permissionsBV) {
							for (Player player : plugin.getServer().getOnlinePlayers()) {
								if (player.hasPermission("zavautomessager.see") || !(plugin.ignorePlayers.contains(player.getName()))) {
									if (plugin.chatWrapEnabled) {
										for (String s : plugin.cutBroadcastList) {
											player.sendMessage(s);
										}
									} else {
										player.sendMessage(plugin.broadcastMessage);
									}
								}
								plugin.log.info(plugin.broadcastMessage);
							}
						} else {
							for (Player player : plugin.getServer().getOnlinePlayers()) {
								if (!plugin.ignorePlayers.contains(player.getName())) {
									if (plugin.chatWrapEnabled) {
										for (String s : plugin.cutBroadcastList) {
											player.sendMessage(s);
										}
									} else {
										player.sendMessage(plugin.broadcastMessage);
									}
								}
							}
							plugin.log.info(plugin.broadcastMessage);
						}
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
