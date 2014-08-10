package ca.kanoa.rodstwo.rods;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.FireworkMeta;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.config.ConfigOptions;

public class God extends Rod implements Listener {

	private HashMap<Integer, String> strikeID;
	private FireworkEffectPlayer fePlayer;
	private HashMap<TNTPrimed, String> bombSites;

	public God() throws Exception {
		super("God", 1, 280, 
				new ConfigOptions(
						new String[]{"height", "max_distance", "effect_delay", "power_per_explosion", "enable_fire_from_explosion"}, 
						new Object[]{16, 100, 5, 2D, false}), 
				10000);
		setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DBD", "DDD").setIngredient('D', Material.SPONGE).setIngredient('B', Material.STICK));
	}

	@Override
	public boolean run(Player player, ConfigurationSection config) {
		
		//Make the firework
		final FireworkEffect fe = FireworkEffect.
				builder().
				flicker(false).
				with(Type.BURST).
				withColor(Color.AQUA).
				withFade(Color.BLUE).
				build();
		
		//Get the effect_delay from the config
		final int effectDelay = config.getInt("effect_delay");
		
		//Get the target location
		Location centerLoc = player.getTargetBlock(null, config.getInt("max_distance")).getLocation().add(0, config.getInt("height"), 0);
		
		//Spawn the firework effects and sounds
		int i = 0;
		for (Location loc : circle(player, centerLoc, 10, 1, true, false, 0)) {
			final Location l1 = loc;
			Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
				public void run() {
					try {
						fePlayer.playFirework(l1.getWorld(), l1, fe);
					} catch (Exception e) {
						e.printStackTrace(); 
					}
				}}, (long) (((double)(effectDelay) / (double)(5)) * i));
			i++;
		}
		
		//Shot lightning from the sky
		final Location centerLoc2 = new Location(centerLoc.getWorld(), centerLoc.getX(), player.getTargetBlock(null, config.getInt("max_distance")).getY(), centerLoc.getZ());
		final Player p1 = player;
		final ConfigurationSection c1 = config;
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			public void run() {
				for (Location loc : circle(p1, centerLoc2, 10, 1, false, false, 0)) {
					//Lightning first
					final int id = loc.getWorld().strikeLightning(loc).getEntityId();
					strikeID.put(id, p1.getName());
			        Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
						public void run() {
							strikeID.remove(id);
						}}, 100L);
			        
			        //Now the explosions
			        final TNTPrimed tnt = p1.getWorld().spawn(loc.add(0, 1, 0), TNTPrimed.class);
					tnt.setYield((float) c1.getDouble("power_per_explosion"));
					tnt.setIsIncendiary(c1.getBoolean("enable_fire_from_explosion"));
					tnt.setFuseTicks(0);
					bombSites.put(tnt, p1.getName());
					Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
						@Override
						public void run() {
							bombSites.remove(tnt);
						}}, 100L);
				}
			}}, (long) (((double)(effectDelay) / (double)(5)) * i));
		
		//Star around a player
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
			public void run() {
				Location pLoc = p1.getLocation().add(0, 2, 0);
				try {
					fePlayer.playFirework(pLoc.getWorld(), pLoc, FireworkEffect.builder().with(Type.STAR).withColor(Color.PURPLE).withFade(Color.MAROON).withFlicker().build());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}}, (long) (((double)(effectDelay) / (double)(5)) * i) + 10L);
		
		//Tell the plugin that it worked
		return true;
	}

	@Override
	public boolean enable(Server serv) {
    	strikeID = new HashMap<Integer, String>();
		bombSites = new HashMap<TNTPrimed, String>();
    	Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
		fePlayer = new FireworkEffectPlayer();
		return true;
	}
    
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerDeathByLightning(EntityDamageByEntityEvent event) {
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
    public void onEntityDamageByLightning(EntityDamageByEntityEvent event) {
    	if (event.getEntity() instanceof Player && 
    			event.getDamager() instanceof LightningStrike &&
    			strikeID.containsKey(event.getDamager().getEntityId()) &&
    			strikeID.get(event.getDamager().getEntityId()).equalsIgnoreCase(((Player) event.getEntity()).getName()))
    		event.setCancelled(true);
    		
    }
	
	@EventHandler(ignoreCancelled=true)
	public void onPlayerDeathByExplosion(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && 
				event.getDamager() instanceof TNTPrimed && 
				((Player)event.getEntity()).getHealth() - event.getDamage() <= 0 &&
				bombSites.containsKey((TNTPrimed) event.getDamager()) &&
				Bukkit.getPlayer(bombSites.get((TNTPrimed) event.getDamager())) != null) {
			((Player) event.getEntity()).damage(event.getDamage(), Bukkit.getPlayer(bombSites.get((TNTPrimed) event.getDamager())));
			event.setCancelled(true);
		}
	}
    
    @EventHandler
    public void onEntityDamageByExplosion(EntityDamageByEntityEvent event) {
    	if (event.getEntity() instanceof Player && 
    			event.getDamager() instanceof TNTPrimed &&
    			bombSites.containsKey((TNTPrimed) event.getDamager()) &&
    			bombSites.get((TNTPrimed) event.getDamager()).equalsIgnoreCase(((Player) event.getEntity()).getName()))
    		event.setCancelled(true);
    		
    }
	
    public static List<Location> circle (Player player, Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                        }
                    }
     
        return circleblocks;
    }

	/**
	 * FireworkEffectPlayer v1.0
	 * 
	 * FireworkEffectPlayer provides a thread-safe and (reasonably) version independant way to instantly explode a FireworkEffect at a given location.
	 * You are welcome to use, redistribute, modify and destroy your own copies of this source with the following conditions:
	 * 
	 * 1. No warranty is given or implied.
	 * 2. All damage is your own responsibility.
	 * 3. You provide credit publicly to the original source should you release the plugin.
	 * 
	 * @author codename_B
	 */
	public class FireworkEffectPlayer {

		/*
		 * Example use:
		 * 
		 * public class FireWorkPlugin implements Listener {
		 * 
		 * FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
		 * 
		 * @EventHandler
		 * public void onPlayerLogin(PlayerLoginEvent event) {
		 *   fplayer.playFirework(event.getPlayer().getWorld(), event.getPlayer.getLocation(), Util.getRandomFireworkEffect());
		 * }
		 * 
		 * }
		 */

		// internal references, performance improvements
		private Method world_getHandle = null;
		private Method nms_world_broadcastEntityEffect = null;
		private Method firework_getHandle = null;

		/**
		 * Play a pretty firework at the location with the FireworkEffect when called
		 * @param world
		 * @param loc
		 * @param fe
		 * @throws Exception
		 */
		public void playFirework(World world, Location loc, FireworkEffect fe) throws Exception {
			// Bukkity load (CraftFirework)
			org.bukkit.entity.Firework fw = (org.bukkit.entity.Firework) world.spawn(loc, org.bukkit.entity.Firework.class);
			// the net.minecraft.server.World
			Object nms_world = null;
			Object nms_firework = null;
			/*
			 * The reflection part, this gives us access to funky ways of messing around with things
			 */
			if(world_getHandle == null) {
				// get the methods of the craftbukkit objects
				world_getHandle = getMethod(world.getClass(), "getHandle");
				firework_getHandle = getMethod(fw.getClass(), "getHandle");
			}
			// invoke with no arguments
			nms_world = world_getHandle.invoke(world, (Object[]) null);
			nms_firework = firework_getHandle.invoke(fw, (Object[]) null);
			// null checks are fast, so having this seperate is ok
			if(nms_world_broadcastEntityEffect == null) {
				// get the method of the nms_world
				nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
			}
			/*
			 * Now we mess with the metadata, allowing nice clean spawning of a pretty firework (look, pretty lights!)
			 */
			// metadata load
			FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
			// clear existing
			data.clearEffects();
			// power of one
			data.setPower(1);
			// add the effect
			data.addEffect(fe);
			// set the meta
			fw.setFireworkMeta(data);
			/*
			 * Finally, we broadcast the entity effect then kill our fireworks object
			 */
			// invoke with arguments
			nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] {nms_firework, (byte) 17});
			// remove from the game
			fw.remove();
		}

		/**
		 * Internal method, used as shorthand to grab our method in a nice friendly manner
		 * @param cl
		 * @param method
		 * @return Method (or null)
		 */
		private Method getMethod(Class<?> cl, String method) {
			for(Method m : cl.getMethods()) {
				if(m.getName().equals(method)) {
					return m;
				}
			}
			return null;
		}

	}

}
