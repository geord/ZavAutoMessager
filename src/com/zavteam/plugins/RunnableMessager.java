package com.zavteam.plugins;

public class RunnableMessager implements Runnable {
	public Main plugin;
	public RunnableMessager(Main instance) {
		plugin = instance;
	}
	@Override
	public void run() {
		plugin.getServer().broadcastMessage(plugin.messages.get(plugin.random.nextInt(plugin.messages.size() + 1)));
		
	}

}
