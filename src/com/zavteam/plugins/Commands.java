package com.zavteam.plugins;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
	public Main plugin;
	public Commands(Main instance) {
		plugin = instance;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return false;
	}

}
