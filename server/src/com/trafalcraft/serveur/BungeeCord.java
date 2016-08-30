package com.trafalcraft.serveur;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class BungeeCord implements PluginMessageListener, Listener{

	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {}
	
	public static void sendPlayer(Player p, String server) {
		final ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		if (Bukkit.getOnlinePlayers().size() > 0) {
			p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
		}
		Main.getPlugin().getLogger().info("Player "+p.getName()+" send to the server "+server+" with success");
		
		//Leave.leave(p);
	}
	
	
}
