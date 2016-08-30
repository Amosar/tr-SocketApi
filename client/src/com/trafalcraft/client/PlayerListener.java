package com.trafalcraft.client;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.trafalcraft.client.api.SocketApi;


public class PlayerListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		final WaitPlayer tempWaitPlayer = Main.getWaitPlayer(e.getPlayer().getName());
		final Player player = e.getPlayer();
		Bukkit.getLogger().info(e.getPlayer().getName() + " player received");
		if(tempWaitPlayer != null){
			Bukkit.getLogger().info("player from the lobby");
			Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
				
				public void run() {
					SocketApi.getPlugin(tempWaitPlayer.getMinigame()).playerJoin(player.getName(), tempWaitPlayer.getArene(), tempWaitPlayer.getComplement());
					Main.removeWaitPlayer(player.getName());
				}
			}, 40);
		}
	}
	
}
