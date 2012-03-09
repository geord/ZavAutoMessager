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
		
		String[] cutMessageList;
		
		boolean messageRandom = plugin.MConfig.getMessageRandom();
		if (plugin.messageToggle) {
			if (plugin.messages.size() == 1) {
				plugin.messageIt = 0;
			} else {
				if (messageRandom) {
					plugin.messageIt = random.nextInt(plugin.messages.size());
				}
			}
			chatString = plugin.MConfig.getChatFormat().replace("%msg", plugin.messages.get(plugin.messageIt));
			chatString = chatString.replace("&", "\u00A7");
			cutMessageList = ChatPaginator.wordWrap(chatString, 53);
			plugin.MHandler.handleMessage(chatString, cutMessageList);
			if (plugin.messageIt == plugin.messages.size() - 1) {
				plugin.messageIt = 0;
			} else {
				plugin.messageIt = plugin.messageIt + 1;
			}
		}
	}

}
