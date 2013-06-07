package ca.kanoa.RodsTwo.Listeners;

import ca.kanoa.RodsTwo.Helpers.Utils;
import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.RodsTwo;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CastListener implements Listener {

	private RodsTwo plugin;

	public CastListener(){
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
					if(event.getItem().getTypeId() == rod.getItemID() && 
							event.getItem().getItemMeta().getLore().contains(rod.getName()) &&
							(event.getItem().getAmount() >= rod.getCost() || 
							event.getPlayer().getGameMode() == GameMode.CREATIVE) && 
							(event.getPlayer().hasPermission(rod.getUsePermission()) || 
							event.getPlayer().hasPermission("lr.use.all")) &&
							plugin.rodConfig.getBoolean(rod.getPath("enabled"))){
						
						if(Utils.isCooldownOver(event.getPlayer().getName()) || 
								event.getPlayer().hasPermission("lr.cooldown.exempt")){
							
							if(rod.run(event.getPlayer(), plugin.rodConfig.getConfigurationSection("Rods." + rod.getName() + ".options"))){
								if (!(event.getPlayer().getGameMode() == GameMode.CREATIVE)) {
									ItemStack is = event.getItem();
									if(is.getAmount() == rod.getCost()) is = null;
									else is.setAmount(is.getAmount() - rod.getCost());
									event.getPlayer().setItemInHand(is);
								}

								if (!event.getPlayer().hasPermission("lr.cooldown.exempt"))
									plugin.cooldowns.put(event.getPlayer().getName(), System.currentTimeMillis() + rod.getCooldown());
							}
							
						}
						else if (event.getPlayer().hasPermission("lr.slowdownmessage")) {
							
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
