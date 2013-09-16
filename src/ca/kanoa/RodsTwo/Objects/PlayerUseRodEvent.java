package ca.kanoa.rodstwo.objects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUseRodEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    private final Rod rod;
    private final Player player;
 
    public PlayerUseRodEvent(Player player, Rod rod) {
    	this.player = player;
    	this.rod = rod;
    }
 
    public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }

	public Player getPlayer() {
		return player;
	}

	public Rod getRod() {
		return rod;
	}
    
}