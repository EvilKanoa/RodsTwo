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

    public EventListener(RodsTwo plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(final PlayerInteractEvent event){
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            for(Rod rod : plugin.getRods()){
                try {
                    if(event.getItem().getTypeId() == rod.getRodID() && event.getItem().getItemMeta().getLore().contains(rod.getName()) &&
                            event.getItem().getAmount() >= rod.getCost() && event.getPlayer().hasPermission(rod.getPermission()) &&
                            plugin.rodConfig.getBoolean(rod.getPath("enabled"))){
                        if(plugin.utils.isCooldownOver(event.getPlayer().getName())){
                            if(rod.run(event.getPlayer(), plugin.rodConfig)){
                                ItemStack is = event.getItem();
                                if(is.getAmount() == rod.getCost()) is = null;
                                else is.setAmount(is.getAmount() - rod.getCost());
                                event.getPlayer().setItemInHand(is);

                                plugin.cooldowns.put(event.getPlayer().getName(), System.currentTimeMillis() + rod.getCooldown());
                            }
                        }
                        else{
                            event.getPlayer().sendMessage(ChatColor.RED + "Slow down! Magic takes " + ((float)rod.getCooldown()/1000) + " second(s) to regain power!");
                        }
                    }
                } catch (NullPointerException e) {
                    //Do nothing
                }
            }
        }
    }
}
