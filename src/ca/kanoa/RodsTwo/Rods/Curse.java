<<<<<<< HEAD
package ca.kanoa.rodstwo.Rods;
=======
package ca.kanoa.rodstwo.rods;
>>>>>>> dev

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;

import ca.kanoa.rodstwo.RodsTwo;
<<<<<<< HEAD
import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;
=======
import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;
>>>>>>> dev

public class Curse extends Rod implements Listener {

	private Random rand;
	private Set<AffectedPlayer> affected;

	public Curse() throws Exception {
		super("Curse", 1, 280, new ConfigOptions(new String[]{"random_potion_length", "random_potion_level"}, new Object[]{30, 2}), 2000);
		setRecipe(new ShapedRecipe(super.getItem()).shape("SSS", "RBR").setIngredient('S', Material.SPIDER_EYE).setIngredient('R', Material.ROTTEN_FLESH).setIngredient('B', Material.STICK));
	}

	@Override
	public boolean run(Player player, ConfigurationSection config) {

		Entity target = getTarget(player);
		if (target == null || !(target instanceof LivingEntity))
			return false;

		PotionEffectType type;
		switch (rand.nextInt(7) + 1) {
		case 1:   type = PotionEffectType.BLINDNESS;    break;
		case 2:   type = PotionEffectType.CONFUSION;    break;
		case 3:   type = PotionEffectType.HARM;         break;
		case 4:   type = PotionEffectType.POISON;       break;
		case 5:   type = PotionEffectType.WITHER;       break;
		case 6:   type = PotionEffectType.SLOW;         break;
		case 7:   type = PotionEffectType.WEAKNESS;     break;
		default:  type = PotionEffectType.HEAL;
		}
		PotionEffect potion = new PotionEffect(type, config.getInt("random_potion_length") * 20, config.getInt("random_potion_level") - 1);

		((LivingEntity)target).addPotionEffect(potion);
		target.getWorld().playEffect(target.getLocation(), Effect.POTION_BREAK, 4);

		if (target instanceof Player) {
			final AffectedPlayer afft = new AffectedPlayer(player, (Player) target, potion);
			affected.add(afft);
			Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.plugin, new Runnable(){
				public void run() {
					affected.remove(afft);
				}}, (config.getInt("random_potion_length") * 20) + 30);
		}

		return true;
	}

	@Override
	public boolean enable(Server serv) {
		rand = new Random(serv.getSpawnRadius() * serv.getMaxPlayers() * 10);
		Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
		affected = new HashSet<AffectedPlayer>();
		return true;
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onPlayerDeath(EntityDamageEvent event) {
		if ((event.getCause() == DamageCause.MAGIC || 
				event.getCause() == DamageCause.WITHER) && 
				event.getEntity() instanceof Player &&
				((Player)event.getEntity()).getHealth() - event.getDamage() <= 0) {
			Player player = (Player) event.getEntity();
			for (AffectedPlayer ap : affected) {
				if (ap.getAffectee() == player && player.hasPotionEffect(ap.getEffect().getType())) {
					event.setCancelled(true);
					player.damage(event.getDamage(), ap.getAffecter());
					affected.remove(player.getName());
				}
			}
		}
	}

	private Entity getTarget(Player player) {

		BlockIterator iterator = new BlockIterator(player.getWorld(), player
				.getLocation().toVector(), player.getEyeLocation()
				.getDirection(), 0, 100);
		Entity target = null;
		while (iterator.hasNext()) {
			Block item = iterator.next();
			for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
				int acc = 2;
				for (int x = -acc; x < acc; x++)
					for (int z = -acc; z < acc; z++)
						for (int y = -acc; y < acc; y++)
							if (entity.getLocation().getBlock()
									.getRelative(x, y, z).equals(item)) 
								return target = entity;
			}
		}
		return target;
	}

	private class AffectedPlayer {

		private final Player affecter;
		private final Player affectee;
		private final PotionEffect effect;

		public AffectedPlayer(Player affecter, Player affectee, PotionEffect effect) {
			this.affecter = affecter;
			this.affectee = affectee;
			this.effect = effect;
		}

		public PotionEffect getEffect() {
			return effect;
		}

		public Player getAffecter() {
			return affecter;
		}

		public Player getAffectee() {
			return affectee;
		}

	}
}
