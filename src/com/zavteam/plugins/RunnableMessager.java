package com.zavteam.plugins;

import java.util.Random;

import org.bukkit.util.ChatPaginator;

public class RunnableMessager implements Runnable {
	public Main plugin;
	public RunnableMessager(Main instance) {
		plugin = instance;
	}
	
	@Override
	public void run() {
		Random random = new Random();
		
		String chatString;
		
		boolean messageRandom = plugin.MConfig.getMessageRandom();
		if (plugin.messageToggle) {
			if (plugin.messages.size() == 1) {
				plugin.messageIt = 0;
			} else {
				if (messageRandom) {
					plugin.messageIt = random.nextInt(plugin.messages.size());
				}
			}
			chatString = plugin.chatFormat.replace("%msg", plugin.messages.get(plugin.messageIt));
			chatString = chatString.replace("&", "\u00A7");
			plugin.cutMessageList = ChatPaginator.wordWrap(chatString, 53);
			plugin.displayMessage(chatString, plugin.cutMessageList);
			if (plugin.messageIt == plugin.messages.size() - 1) {
				plugin.messageIt = 0;
			} else {
				plugin.messageIt = plugin.messageIt + 1;
			}
		}
	}

}
