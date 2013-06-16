package ca.kanoa.RodsTwo.Rods;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import ca.kanoa.RodsTwo.RodsTwo;
import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;

public class Firework extends Rod {

	public static final float speed = 4.5f;
	
	private FireworkEffectPlayer fePlayer;

	public Firework(Plugin plugin) throws Exception {
		super("Firework", 1, 280, new ConfigOptions(new String[]{"radius", "damage"}, new Object[]{4, 10}), 1000);
		setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DBD", "DDD").setIngredient('D', Material.SPONGE).setIngredient('B', Material.STICK));
	}

	@Override
	public boolean run(Player player, ConfigurationSection config) {
		org.bukkit.entity.Firework firework = player.getWorld().spawn(player.getLocation().add(0, 1.5f, 0), org.bukkit.entity.Firework.class);
		FireworkMeta fwm = firework.getFireworkMeta();
		fwm.addEffect(FireworkEffect.builder().withTrail().with(Type.BALL).withColor(Color.RED).withFade(Color.WHITE).build());
		firework.setFireworkMeta(fwm);
		firework.setVelocity(player.getEyeLocation().getDirection().multiply(speed));
		final ConfigurationSection c1 = config;
		final Player p1 = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new FireworkHitBlock(firework, new FireworkRunnable(){
			@Override
			public void run(Location loc, org.bukkit.entity.Firework firework) {
				int radius = c1.getInt("radius");
				Collection<Player> players = loc.getWorld().getEntitiesByClass(Player.class);
				players.remove(p1);
				int damage = c1.getInt("damage") / (players.size() == 0 ? 1 : players.size());
				for (Player p : players)
					if (p.getLocation().distance(loc) <= radius && p != p1) {
						p.damage(damage, p1);
						p.playSound(loc, Sound.EXPLODE, 1, 5.05f);
					}
			}}), 1l);
		return true;
	}

	@Override
	public boolean enable(Server serv) {
		fePlayer = new FireworkEffectPlayer();
		return true;
	}

	class FireworkHitBlock implements Runnable {

		private org.bukkit.entity.Firework firework;
		private FireworkRunnable whenHitsBlock;

		public final List<Material> trans = Arrays.asList(new Material[]{Material.AIR, Material.LONG_GRASS, Material.WATER});

		public FireworkHitBlock (org.bukkit.entity.Firework firework, FireworkRunnable whenHitsBlock) {
			this.firework = firework;
			this.whenHitsBlock = whenHitsBlock;
		}

		@Override
		public void run() {
			Block loc = firework.getLocation().getBlock();
			if (!firework.isValid()) 
				firework.remove();
			else if (loc.getType() != Material.AIR ||
					(Double.parseDouble((firework.getLocation().getY() + "").substring(2)) < 0.6f &&
							!trans.contains(loc.getRelative(0, -1, 0).getType())) ||
							!trans.contains(loc.getRelative(1, 0, 0).getType()) ||
							!trans.contains(loc.getRelative(0, 0, 1).getType()) ||
							!trans.contains(loc.getRelative(-1, 0, 0).getType()) ||
							!trans.contains(loc.getRelative(0, 0, -1).getType())) {
				firework.remove();
				for (FireworkEffect fe : firework.getFireworkMeta().getEffects())
					try {
						fePlayer.playFirework(loc.getWorld(), firework.getLocation(), fe);
					} catch (Exception e) {
						e.printStackTrace();
					}
				whenHitsBlock.run(loc.getLocation(), firework);
			}
			else
				Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, this, 1L);
		}

	}

	interface FireworkRunnable {
		public void run(Location loc, org.bukkit.entity.Firework firework);
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
