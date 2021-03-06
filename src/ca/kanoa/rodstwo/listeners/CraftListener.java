package ca.kanoa.rodstwo.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.rods.Rod;

public class CraftListener implements Listener {

	@EventHandler
	public void onRodCraft(CraftItemEvent event){
		
		Player player = (Player) event.getViewers().get(0);
		if (player.hasPermission("lr.craft.all"))
			return;
		
		for (Rod rod : RodsTwo.rods) 
			if (event.getRecipe().getResult().getItemMeta().getLore().get(0).equals(rod.getRecipe().getResult().getItemMeta().getLore().get(0))) 
				if (!player.hasPermission(rod.getCraftPermission())) {
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "You don't have permissions to craft that!");
				}
		
	}
	
}
