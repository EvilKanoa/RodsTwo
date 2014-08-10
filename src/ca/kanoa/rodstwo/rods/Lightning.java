package ca.kanoa.rodstwo.rods;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.config.ConfigOptions;

public class Lightning extends Rod implements Listener {

	private HashMap<Integer, String> strikeID;
	
    public Lightning() throws Exception {
        super("Lightning", 1, 280,  new ConfigOptions(new String[]{"max_distance"}, new Object[]{50}), 1500);

        //Set default the recipe
        super.setRecipe(new ShapedRecipe(super.getItem()).shape(" I ", " R ", " I ").setIngredient('I', Material.IRON_INGOT).setIngredient('R', Material.STICK));
    }

    @Override
    public boolean run(Player player, ConfigurationSection config){
    	final int id = player.getWorld().strikeLightning(player.getTargetBlock(null, config.getInt("max_distence")).getLocation()).getEntityId();
        strikeID.put(id, player.getName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			public void run() {
				strikeID.remove(id);
			}}, 100);
        return true;
    }
    
    @Override
    public boolean enable(Server serv) {
    	strikeID = new HashMap<Integer, String>();
    	Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
    	return true;
    }
    
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerDeath(EntityDamageByEntityEvent event) {
    	if (event.getEntity() instanceof Player && 
    			event.getDamager() instanceof LightningStrike &&
    			strikeID.containsKey(event.getDamager().getEntityId()) &&
    			((Player)event.getEntity()).getHealth() - event.getDamage() <= 0 &&
    			Bukkit.getServer().getPlayer(strikeID.get(event.getDamager().getEntityId())) != null) {
    		event.setCancelled(true);
    		((Player)event.getEntity()).damage(event.getDamage(), Bukkit.getPlayer(strikeID.get(event.getDamager().getEntityId())));
    	}
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    	if (event.getEntity() instanceof Player && 
    			event.getDamager() instanceof LightningStrike &&
    			strikeID.containsKey(event.getDamager().getEntityId()) &&
    			strikeID.get(event.getDamager().getEntityId()).equalsIgnoreCase(((Player) event.getEntity()).getName()))
    		event.setCancelled(true);
    		
    }
    
}