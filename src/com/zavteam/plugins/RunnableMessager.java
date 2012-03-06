package com.zavteam.plugins;

import org.bukkit.util.ChatPaginator;

public class RunnableMessager implements Runnable {
	public Main plugin;
	public RunnableMessager(Main instance) {
		plugin = instance;
	}
	@Override
	public void run() {
		if (plugin.messageToggle) {
			if (plugin.messages.size() == 1) {
				plugin.messageIt = 0;
			} else {
				if (plugin.messageRandom) {
					plugin.messageIt = plugin.random.nextInt(plugin.messages.size());
				}
			}
			plugin.chatString = plugin.chatFormat.replace("%msg", plugin.messages.get(plugin.messageIt));
			plugin.chatString = plugin.chatString.replace("&", "\u00A7");
			plugin.cutMessageList = ChatPaginator.wordWrap(plugin.chatString, 53);
			plugin.displayMessage(plugin.chatString, plugin.cutMessageList);
			if (plugin.messageIt == plugin.messages.size() - 1) {
				plugin.messageIt = 0;
			} else {
				plugin.messageIt = plugin.messageIt + 1;
			}
		}
	}

}
