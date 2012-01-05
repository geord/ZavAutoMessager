package com.zavteam.plugins;

public class RunnableMessager implements Runnable {
	public Main plugin;
	public RunnableMessager(Main instance) {
		plugin = instance;
	}
	@Override
	public void run() {
		plugin.messageIt = plugin.random.nextInt(plugin.messages.size()) + 1;
		plugin.chatString = plugin.chatFormat.replace("%msg", plugin.messages.get(plugin.messageIt));
		plugin.getServer().broadcastMessage(plugin.chatString);
		
	}

}
