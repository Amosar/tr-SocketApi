package com.trafalcraft.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.trafalcraft.serveur.util.Msg;


public class Main extends JavaPlugin{
	
	public static HashMap<String, Serveur> serveurs = new HashMap<String, Serveur>();
	private ServerSocket ss = null;
	private Thread t;
	private int port= 2016;
	
	private static JavaPlugin plugin;
	
	 public void onEnable(){
			try {
				plugin = this;
				
				plugin.getConfig().options().copyDefaults(true);
				plugin.saveDefaultConfig();
				plugin.reloadConfig();
				
				port = plugin.getConfig().getInt("port");
				
				//instance pour envoyé des messages
				this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
				//instance pour recevoir des messages
				this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeCord());
				
				ss = new ServerSocket(port);
				this.getLogger().info("The server is listening the port "+ss.getLocalPort());
				
				t = new Thread(new Accepter_connexion(ss));
				t.start();
				
			} catch (IOException e) {
				Bukkit.getLogger().warning("The port "+ss.getLocalPort()+" is already used ! plugin stopped");
				onDisable();
			}
		
	 }
	 public void onDisable(){
		 try {
			ss.close();
			t.interrupt();
		} catch (IOException e) {
		}
	 }

	 
	 @Override
	public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {
		 if(cmd.getName().equalsIgnoreCase("join")){
			 //serveur,mini-jeux,arene
			 
			 
			 if(args.length == 3){
				 if(sender instanceof Player){
					 if(sender.hasPermission("socketServeurApi.sendRequest")){
						 serveurs.get(args[0]).sendRequest(args[1], args[2], sender.getName(),null);
						 sender.sendMessage(Msg.Prefix.toString()+Msg.requestSend);
					 }else{
						 sender.sendMessage(Msg.Prefix.toString()+Msg.NO_PERMISSIONS);
					 }
				 }else{
					 this.getLogger().warning("The console can not join minigames\n"
					 		+ "use: /join <server> <mini-games> <arena> <player>  (complement)");
				 }
			 }else if(args.length == 4){
				 if(Bukkit.getPlayer(args[3]) == null){
					 if(sender.hasPermission("socketServeurApi.sendRequest")){
						 serveurs.get(args[0]).sendRequest(args[1], args[2], sender.getName(),args[3]);
						 sender.sendMessage(Msg.Prefix.toString()+Msg.requestSend);
						 return true;
					 }else{
						 sender.sendMessage(Msg.Prefix.toString()+Msg.NO_PERMISSIONS);
						 return true;
					 }
				 }
				 if(sender.hasPermission("socketServeurApi.sendRequestForOtherPlayer")){
					 serveurs.get(args[0]).sendRequest(args[1], args[2], args[3],null);
					 sender.sendMessage(Msg.Prefix.toString()+Msg.requestSendForOtherPlayer.toString().replace("$player", args[3]));
					 Bukkit.getPlayer(args[3]).sendMessage(Msg.Prefix.toString()+Msg.requestSendByOtherPlayer.toString().replace("$player", sender.getName()));
				 }else{
					 sender.sendMessage(Msg.Prefix.toString()+Msg.NO_PERMISSIONS);
				 }
			}else if(args.length == 5){
				 if(Bukkit.getPlayer(args[3]) == null){
					 sender.sendMessage(Msg.ERROR.toString()+Msg.playerdontExist);
					 return true;
				 }
				 if(sender.hasPermission("socketServeurApi.sendRequestForOtherPlayer")){
					 serveurs.get(args[0]).sendRequest(args[1], args[2], args[3],args[4]);
					 sender.sendMessage(Msg.Prefix.toString()+Msg.requestSendForOtherPlayer.toString().replace("$player", args[3]));
					 Bukkit.getPlayer(args[3]).sendMessage(Msg.Prefix.toString()+Msg.requestSendByOtherPlayer.toString().replace("$player", sender.getName()));
				 }else{
					 sender.sendMessage(Msg.Prefix.toString()+Msg.NO_PERMISSIONS);
				 }
			 }else{
				 sender.sendMessage(Msg.ERROR.toString()+Msg.Command_Use.toString().replace("$command", "/join <server> <mini-game> <arena> (player) (complement)"));
			 }
		 }
		return false;
	}
	 
	 public static JavaPlugin getPlugin(){
		 return plugin;
	 }
}
