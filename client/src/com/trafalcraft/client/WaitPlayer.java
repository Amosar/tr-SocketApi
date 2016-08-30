package com.trafalcraft.client;

public class WaitPlayer {
	
	private String minigame;
	private String arene;
	private String complement;
	
	public String getMinigame() {
		return minigame;
	}

	public String getArene() {
		return arene;
	}
	
	public String getComplement(){
		return complement;
	}


	public WaitPlayer(String minigame, String arene, String complement){
		this.minigame = minigame;
		this.arene = arene;
		this.complement = complement;
	}
	
}
