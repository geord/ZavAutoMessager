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
				sender.sendMessage(ChatColor.GOLD + "7. /automessager about - Displays info about the plugin");
				sender.sendMessage(ChatColor.GOLD + "8. /automessager help - Displays this menu");
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
						plugin.broadcastMessage = "";
						for (int i = 1; i < args.length; i++) {
							plugin.broadcastMessage = plugin.broadcastMessage + args[i] + " ";
						}
						plugin.broadcastMessage = plugin.broadcastMessage.trim();
						plugin.broadcastMessage = plugin.chatFormat.replace("%msg", plugin.broadcastMessage);
						plugin.broadcastMessage = plugin.broadcastMessage.replace("&", "\u00A7");
						plugin.cutBroadcastList = ChatPaginator.wordWrap(plugin.broadcastMessage, 53);
						plugin.displayMessage(plugin.broadcastMessage, plugin.cutBroadcastList);
					}
				} else {
					sender.sendMessage(noPerm);
				}
			} else if (args[0].equalsIgnoreCase("about")) {
				if (sender.hasPermission("zavautomessager.about")) {
					sender.sendMessage(ChatColor.GOLD + "You are currently running ZavAutoMessage Version " + plugin.getDescription().getVersion() + ".");
					sender.sendMessage(ChatColor.GOLD + "The latest version is currently version " + plugin.versionConfig.getString("version") + ".");
					sender.sendMessage(ChatColor.GOLD + "This plugin was developed by the ZavCodingTeam.");
					sender.sendMessage(ChatColor.GOLD + "Please visit our Bukkit Dev Page for complete details on this plugin.");
				} else {
					sender.sendMessage(noPerm);
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (sender.hasPermission("zavautomessager.remove")) {
					if (args.length < 2) {
						sender.sendMessage(ChatColor.RED + "You need to enter a message number to delete.");
					} else {
						try {
							Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							sender.sendMessage(ChatColor.RED + "You have to enter a round number to remove.");
							return false;
						}
						if (Integer.parseInt(args[1]) < 0 || Integer.parseInt(args[1]) > plugin.messages.size() || plugin.messages.size() == 1) {
							sender.sendMessage(ChatColor.RED + "This is not a valid message number");
							sender.sendMessage(ChatColor.RED + "Use /automessager list for a list of messages");
						} else {
							plugin.messages.remove(Integer.parseInt(args[1]) - 1);
							plugin.config.set("messages", plugin.messages);
							plugin.saveConfig();
							plugin.autoReload();
						}
					}
				} else {
					sender.sendMessage(noPerm);
				}
			} else if (args[0].equalsIgnoreCase("list")) {
				if (sender.hasPermission("zavautomessager.list")) {
					if (!(args.length > 1)) {
						plugin.listPage(1, sender);
						return true;
					}
					try {
						plugin.messages.get(Integer.parseInt(args[1]));
					} catch (Exception e) {
						sender.sendMessage(ChatColor.RED + "You have to enter a valid number to show help page.");
						return false;
					}
					plugin.listPage(Integer.parseInt(args[1]), sender);
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
