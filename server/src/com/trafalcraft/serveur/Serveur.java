package com.trafalcraft.serveur;

import java.io.PrintWriter;
import java.net.Socket;

import org.bukkit.Bukkit;

public class Serveur extends Socket{
	
	String name;
	PrintWriter out;
	
	public Serveur(String name, PrintWriter out){
		this.name = name;
		this.out = out;
	}
	
	public void sendRequest(String miniGames,String arena, String player,String complement){
		out.println("PlayerRequest:"+miniGames+":"+arena+":"+player+":"+complement);
		out.flush();
	}
	
	public void sendUser(String player, String server){
		Main.getPlugin().getLogger().info("Player "+player+" accepted");
		BungeeCord.sendPlayer(Bukkit.getPlayer(player), server);
	}
	public void denyUser(String joueur){
		Main.getPlugin().getLogger().info("Player "+joueur+" refused");
	}
}
