package com.trafalcraft.client.api;

import java.util.HashMap;

import org.bukkit.Bukkit;

/**
 * Register your plugin to link your plugin with the socketApi 
 * @author amosar
 *
 */
public class SocketApi {
	
	private static HashMap<String, SocketPlugin> pluginList = new HashMap<String, SocketPlugin>();
	
	/**Register your plugin
	 * 
	 * @param nomPlugin name of plugin used to call the good plugin
	 * @param plugin The class that implement SocketPlugin
	 */
	public static void registerPlugin(String nomPlugin, SocketPlugin plugin){
		pluginList.put(nomPlugin,plugin);
	}
	
	/**Unregister your plugin
	 * 
	 * @param plugin The class that implement SocketPlugin
	 */
	public static void unregisterPlugin(SocketPlugin plugin){
		if(pluginList.containsValue(plugin)){
			pluginList.remove(plugin);
		}else{
			Bukkit.getLogger().warning("SocketApi> error the plugin has not been registered to the plugin");
		}
	}
	
	public static SocketPlugin getPlugin(String key){
		return pluginList.get(key);
	}
}
