package ca.kanoa.RodsTwo.Helpers;

import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.RodsTwo;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {

	private RodsTwo plugin;

	public EventListener(){
		this.plugin = RodsTwo.plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			try {
				if (RodsTwo.noFire.contains(event.getClickedBlock().getType()))
					return;
			} catch (NullPointerException e) {}
			
			for(Rod rod : plugin.getRods()){
				try {
					if(event.getItem().getTypeId() == rod.getRodID() && event.getItem().getItemMeta().getLore().contains(rod.getName()) &&
							event.getItem().getAmount() >= rod.getCost() && 
							(event.getPlayer().hasPermission(rod.getUsePermission()) || event.getPlayer().hasPermission("lr.use.all")) &&
							plugin.rodConfig.getBoolean(rod.getPath("enabled"))){
						
						if(Utils.isCooldownOver(event.getPlayer().getName())){
							
							if(rod.run(event.getPlayer(), plugin.rodConfig.getConfigurationSection("Rods." + rod.getName() + ".options"))){
								ItemStack is = event.getItem();
								if(is.getAmount() == rod.getCost()) is = null;
								else is.setAmount(is.getAmount() - rod.getCost());
								event.getPlayer().setItemInHand(is);

								plugin.cooldowns.put(event.getPlayer().getName(), System.currentTimeMillis() + rod.getCooldown());
							}
							
						}
						else {
							
							String timeLeft = (((float)(((double)(RodsTwo.plugin.cooldowns.get(event.getPlayer().getName())) - System.currentTimeMillis()) / 1000)) + "");
							timeLeft = timeLeft.substring(0, timeLeft.indexOf('.') + 2);
							event.getPlayer().sendMessage(ChatColor.RED + "Slow down! Wait " + ChatColor.YELLOW + '[' + ChatColor.AQUA + (timeLeft.contains("0.0") ? "0.1" : timeLeft) + ChatColor.YELLOW + ']' + ChatColor.RED + " second(s) to regain power!");
							
						}
					}
				} catch (NullPointerException e) {
					//Do nothing
				}
			}
		}
	}

}
