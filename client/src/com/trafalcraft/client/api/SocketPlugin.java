package com.trafalcraft.client.api;

/**
 * Implements this class in your plugin to link your plugin with the socketApi 
 * @author amosar
 *
 */
public interface SocketPlugin {
	
	/**Call if ServerSocket send a player request
	 * It's to check if player can join
	 * 
	 * @param arene Name of the arene called by the ServerSocket
	 * @param complement A list of complements (can contains equip: blue/red for example)
	 * @return your plugin return if the player can join <code>true</code> or not <code>false</code>
	 */
	public boolean checkJoin(String arene, String complement);
	
	/**Call if ServerSocket send a player
	 * 
	 * @param player The player joined
	 * @param arene Name of the arene called by the ServerSocket
	 * @param complement A list of complements (can contains equip: blue/red for example)
	 */
	public void playerJoin(String player, String arene, String complement);
	
}
