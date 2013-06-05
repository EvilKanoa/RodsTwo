package ca.kanoa.RodsTwo.Helpers;

import ca.kanoa.RodsTwo.Objects.Rod;
import ca.kanoa.RodsTwo.RodsTwo;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {

	private RodsTwo plugin;

	public EventListener(RodsTwo plugin){
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			for(Rod rod : plugin.getRods()){
				try {
					if(event.getItem().getTypeId() == rod.getRodID() && event.getItem().getItemMeta().getLore().contains(rod.getName()) &&
							event.getItem().getAmount() >= rod.getCost() && 
							(event.getPlayer().hasPermission(rod.getPermission()) || event.getPlayer().hasPermission("lr.use.all")) &&
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
						else{
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

	@EventHandler
	public void signClick(PlayerInteractEvent event) {
		Block b = event.getClickedBlock();
		Material mat = b == null ? Material.AIR : b.getType();
		if (!(event.getAction() == Action.LEFT_CLICK_BLOCK) || b == null ||
				(mat != Material.SIGN && mat != Material.SIGN_POST && mat != Material.WALL_SIGN))
			return;
		Sign sign = (Sign)b;
		if (sign.getLine(0).equalsIgnoreCase("[LightningRods]")) {
			try {
				String rodName = sign.getLine(1);
				int cost = Integer.parseInt(sign.getLine(2));
				int amount = Integer.parseInt(sign.getLine(3));
				Rod rod = null;
				for (Rod r : RodsTwo.rods)
					if (r.getName().equalsIgnoreCase(rodName))
						rod = r;
				if (rod == null) {
					event.getPlayer().sendMessage(Utils.signMsg("Unknown Rod, tell an admin!"));
					return;
				}
				if (event.getPlayer().getInventory().firstEmpty() == -1) {
					event.getPlayer().sendMessage(Utils.signMsg("Your inventory is full!"));
					return;
				}
				event.getPlayer().getInventory().addItem(rod.getItem(amount));
				//remove money
				event.getPlayer().sendMessage(Utils.signMsg("You just bought " + amount + " " + rod.getName() + " Rod(s), for " + cost /*plus currency*/  + "."));
				
			} catch (Exception e){
				event.getPlayer().sendMessage(Utils.signMsg("Badly formated sign, tell an admin!"));
			}
		}
	}
}
