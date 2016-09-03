package com.trafalcraft.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.bukkit.Bukkit;

import com.trafalcraft.serveur.util.Msg;

public class Accepter_connexion implements Runnable{
	
	private ServerSocket socketserver = null;
	private Socket socket = null;

	public Accepter_connexion(ServerSocket ss){
	 socketserver = ss;
	}
	
	public void run() {
		
		try {
			while(true){
				
			socket = socketserver.accept();
			Main.getPlugin().getLogger().info("A server wants to connect");
			
			new Thread(new Authentification(socket)).start();
			
			socket = null;
			}
		}catch(SocketException e){
			Bukkit.getLogger().info("connection closed");
			
			try {socketserver.close();
			} catch (IOException e1) {}
			
		} catch (IOException e) {
			Bukkit.getLogger().warning("Server Error");
			e.printStackTrace();
		}
		
	}
	
	public class Authentification implements Runnable {

		private Socket socket;
		private PrintWriter out = null;
		private BufferedReader in = null;
		private String id;
		private boolean connection = true;
		
		public Authentification(Socket s){
			 socket = s;
			}
		public void run() {
		
			try {
				
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream());
				
				out.println("login info?");
				out.flush();
				id = in.readLine();
				Serveur serveur = new Serveur(id,out);
				Main.serveurs.put(id, serveur);
				Main.getPlugin().getLogger().info(id +" connected");
				
				connection = true;
				
				while(connection){
					String test = in.readLine();
					Main.getPlugin().getLogger().info("info Received: "+test);
					if(test == null){
						connection = false;
					}else if(test.startsWith("yes")){
						Main.getPlugin().getLogger().info("yes:"+test.split(":")[1]+":"+test.split(":")[2]);
						if(test.split(":").length == 3){
							if(Bukkit.getPlayer(test.split(":")[1]) != null){
								serveur.sendUser(test.split(":")[1],test.split(":")[2]);
								Bukkit.getPlayer(test.split(":")[1]).sendMessage(Msg.Prefix+Msg.requestAccept.toString());
							}
							//test.split(":")[1] = nom du joueur
							//test.split(":")[2] = nom du serveur recepteur
							
						}
					}else if(test.startsWith("no")){
						Main.getPlugin().getLogger().info("no:"+test.split(":")[1]);
						if(test.split(":").length == 2){
							if(Bukkit.getPlayer(test.split(":")[1]) != null){
								Main.getPlugin().getLogger().info(test.split(":")[1]+"deny");
								serveur.denyUser(test.split(":")[1]);
								Bukkit.getPlayer(test.split(":")[1]).sendMessage(Msg.ERROR+Msg.requestDeny.toString());
							}
						}
					}else{
						
					}
				}
				
				Main.serveurs.remove(id);
				socket.close();
				Thread.currentThread().interrupt();
				Main.getPlugin().getLogger().info("connection closed ");
				
			}catch (IOException e) {
				Bukkit.getLogger().warning("No answer");
				e.printStackTrace();
			}
		}
		

	}
	

}
