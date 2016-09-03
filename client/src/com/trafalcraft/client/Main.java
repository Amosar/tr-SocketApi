package com.trafalcraft.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.trafalcraft.client.api.SocketApi;
import com.trafalcraft.client.api.SocketPlugin;

public class Main extends JavaPlugin{
	
	
		private static Socket socket = null;
		private static Thread t1;
		private static PrintWriter out = null;
		private static BufferedReader in = null;
		private static boolean connection = true;
		private static JavaPlugin plugin;
		
		private static String ip = "127.0.0.1";
		private static int port = 2016;
		private static String serverName;
		
		private static HashMap<String, WaitPlayer> waitPlayers = new HashMap<String, WaitPlayer>();
		
		public void onEnable(){
			plugin = this;
			
			plugin.getConfig().options().copyDefaults(true);
			plugin.saveDefaultConfig();
			plugin.reloadConfig();
			
			serverName = plugin.getConfig().getString("serverName");
			ip = plugin.getConfig().getString("ipLobby");
			port = plugin.getConfig().getInt("port");
			
			t1 = new Thread(new connexion());
			t1.start();
			
			//System.out.println(t1);
			
			Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(),this);
			
		}
		
		public  void onDisable(){
			if(t1 != null){
				t1.interrupt();
			}
			//System.out.println("t1:"+t1);
		}
		
		
		private static boolean checkJoin(String nameMinigame, String arene, String player, String complement){
			SocketPlugin sa = SocketApi.getPlugin(nameMinigame);
			if(sa == null){
				return false;
			}
			if(sa.checkJoin(arene, complement)){
				waitPlayers.put(player, new WaitPlayer(nameMinigame, arene, complement));
				return true;
			}
			return false;
		}
		
		public static WaitPlayer getWaitPlayer(String namePlayer){
			return waitPlayers.get(namePlayer);
		}
		
		public static WaitPlayer removeWaitPlayer(String namePlayer){
			return waitPlayers.remove(namePlayer);
		}
		
		public class connexion implements Runnable{
			
			public void run() {
				connect();
			}
			
			public void connect(){
				try {
					Main.this.getLogger().info("connection request");
					socket = new Socket(ip,port);
					Main.this.getLogger().info("Connection established with the server, authentication :"); // Message de connexion
					out = new PrintWriter(socket.getOutputStream());
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					
					Main.this.getLogger().info(in.readLine());
					
					out.println(serverName);
					out.flush();
					Main.this.getLogger().info("transmitted : "+serverName);
					connection = true;
					
					while(connection){
						String test= in.readLine();
						if(test == null){
							connection = false;
						}else{
							if(test.startsWith("PlayerRequest")){
								if(test.split(":").length == 4){
									if(checkJoin(test.split(":")[1], test.split(":")[2], test.split(":")[3], null)){
										out.println("yes:"+test.split(":")[3]+":"+serverName);
										out.flush();
										Main.this.getLogger().info("transmitted : yes:"+test.split(":")[3]+":"+serverName);
										Main.this.getLogger().info("player accepted");
									}else{
										out.println("no:"+test.split(":")[3]);
										out.flush();
										Main.this.getLogger().info("transmitted : no:"+test.split(":")[3]);
										Main.this.getLogger().info("player refuse");
									}
									//test.split(":")[1] == minijeux;
									//test.split(":")[2] == arene;
									//test.split(":")[3] == joueur;
								}else if(test.split(":").length == 5){
									if(checkJoin(test.split(":")[1], test.split(":")[2], test.split(":")[3], test.split(":")[4])){
										out.println("yes:"+test.split(":")[3]+":"+serverName);
										out.flush();
										Main.this.getLogger().info("transmitted : yes:"+test.split(":")[3]+":"+serverName);
										Main.this.getLogger().info("player agrees");
									}else{
										out.println("no:"+test.split(":")[3]);
										out.flush();
										Main.this.getLogger().info("transmitted : no:"+test.split(":")[3]);
										Main.this.getLogger().info("player refused");
									}
									//test.split(":")[1] == minijeux;
									//test.split(":")[2] == arene;
									//test.split(":")[3] == joueur;
								}
							}
						}
					}

					Main.this.getLogger().info("connection closed");
					
				} catch (UnknownHostException e) {
					Bukkit.getLogger().warning("Unable to connect to the address "+socket.getLocalAddress());
				} catch (IOException e) {
					Bukkit.getLogger().warning("No server listening port 2016");
				}
				try {
					socket.close();
				} catch (IOException e) {
				}
				
					
				reconnect();
			}
			
			public void reconnect(){
				try {
					Thread.sleep(5000);
					connect();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}

		
		public static JavaPlugin getPlugin(){
			return plugin;
		}
		
}
