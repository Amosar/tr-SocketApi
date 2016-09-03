package com.trafalcraft.serveur.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.trafalcraft.serveur.Main;

public enum Msg {
	
	Prefix("§9[§3Mini-Games§9]> §2"),
	ERROR("§9[§4Mini-Games§9]> §c"),
	NO_PERMISSIONS("§4Error §9§l> §r§bYou dont have permission to do that!"),
	Command_Use("§l> §r§cCommand use: §6$command"),
	//Joueur
	requestAccept("§2Connection request accepted you'll be teleported"),
	requestDeny("§cConnection request refused the party is probably in progress"),
	serverNotOnline("§cMinigame is not available for this moment please try later\n§cOr contact an Admin on the forum > forum.com"),
	requestSend("Connection request sent please wait"),//Demande de connexion envoyé veuillez patienter
	requestSendForOtherPlayer("Connection request for $player sent please wait!"),//Demande de connexion envoyé pour $player veuillez patienter
	requestSendByOtherPlayer("Connection request by $player sent please wait!"),//Demande de connexion envoyé par $player veuillez patienter
	playerdontExist("The player must exist");//Le joueur à envoyé doit éxister
	
	static JavaPlugin plugin = Main.getPlugin();
	  public static void getHelp(Player sender){
	        sender.sendMessage("");
	        sender.sendMessage("§3§l-------------------ServerSocket-------------------");
	        sender.sendMessage("§3/join <server> <mini-games> <arena> (player)  (complement) §b- Send join request.");
	        sender.sendMessage("§3------------------------------------------------");
	        sender.sendMessage("");
		  }
	  
	  
	    private String value;
		private Msg(String value) {
			this.value = value;
	    }
		
	    public String toString(){
	    	return value;
	    }
	    public void replaceby(String value){
			this.value = value;
	    }
	    
	    public static void load(){
	    	Prefix.replaceby(Main.getPlugin().getConfig().getString("Msg.default.prefix").replace("&", "§"));
	    	ERROR.replaceby(Main.getPlugin().getConfig().getString("Msg.default.error").replace("&", "§"));
	    	NO_PERMISSIONS.replaceby(Main.getPlugin().getConfig().getString("Msg.default.no_permission").replace("&", "§"));
	    	Command_Use.replaceby(Main.getPlugin().getConfig().getString("Msg.default.command_use").replace("&", "§"));
	    	
	    	requestAccept.replaceby(Main.getPlugin().getConfig().getString("Msg.player.requestAccept").replace("&", "§"));
	    	requestDeny.replaceby(Main.getPlugin().getConfig().getString("Msg.player.requestDeny").replace("&", "§"));
	    	serverNotOnline.replaceby(Main.getPlugin().getConfig().getString("Msg.player.serverNotOnline").replace("&", "§"));
	    	requestSend.replaceby(Main.getPlugin().getConfig().getString("Msg.player.requestSend").replace("&", "§"));
	    	requestSendForOtherPlayer.replaceby(Main.getPlugin().getConfig().getString("Msg.player.requestSendForOtherPlayer").replace("&", "§"));
	    	requestSendByOtherPlayer.replaceby(Main.getPlugin().getConfig().getString("Msg.player.requestSendByOtherPlayer").replace("&", "§"));
	    	playerdontExist.replaceby(Main.getPlugin().getConfig().getString("Msg.player.playerdontExist").replace("&", "§"));
	    }
	    
	   /* public static void DefaultMsg(){
	    	//default
	    	Main.getPlugin().getConfig().set("Msg.default.prefix", Prefix.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.default.error", ERROR.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.default.no_permission", NO_PERMISSIONS.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.default.command_use", Command_Use.toString().replace("§", "&"));
	    	
	    	Main.getPlugin().getConfig().set("Msg.player.requestDeny", requestDeny.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.player.requestSend", requestSend.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.player.requestSendForOtherPlayer", requestSendForOtherPlayer.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.player.requestSendByOtherPlayer", requestSendByOtherPlayer.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.player.playerdontExist", playerdontExist.toString().replace("§", "&"));
	    }*/
	
}
