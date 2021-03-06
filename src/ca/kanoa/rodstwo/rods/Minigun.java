package ca.kanoa.rodstwo.rods;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.config.ConfigOptions;

public class Minigun extends Rod implements Listener {

	private Set<String> isFiring;

	public Minigun() throws Exception {
		super("Minigun", 1, 280, new ConfigOptions(new String[]{"shots_per_second", "overheat_time", "minigun_item", "damage_per_shot"}, new Object[]{10, 5, Material.BOW.getId(), 1}), 5000);
		setRecipe(new ShapedRecipe(super.getItem()).shape("SSS", "RBR", "SSS").setIngredient('S', Material.SNOW).setIngredient('R', Material.BOW).setIngredient('B', Material.STICK));
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean run(Player player, ConfigurationSection config) {
		if (isFiring.contains(player.getName()))
			return false;
		isFiring.add(player.getName());
		int loops = config.getInt("shots_per_second") * config.getInt("overheat_time");
		final Player p1 = player;
		final ItemStack gun = new ItemStack(Material.getMaterial(config.getInt("minigun_item")), 1);
		final int orginalSlot = p1.getInventory().getHeldItemSlot();
		ItemMeta im = gun.getItemMeta();
		im.setDisplayName(ChatColor.RESET + "" + ChatColor.RED + ChatColor.BOLD + "Minigun");
		im.setLore(Arrays.asList(new String[]{ChatColor.BLUE + "It shoots things...", ChatColor.GOLD + "Like...really fast!"}));
		gun.setItemMeta(im);
		for (int i = 0; i < loops; i++)
			if (i + 1 >= loops)
				Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.getInstance(), new Runnable(){
					@Override
					public void run() {
						if (isFiring.contains(p1.getName())) {
							p1.launchProjectile(org.bukkit.entity.Snowball.class);
							p1.playSound(p1.getLocation(), Sound.ITEM_BREAK, 1, 1);
							isFiring.remove(p1.getName());
							p1.getInventory().remove(gun);
							p1.getInventory().setHeldItemSlot(orginalSlot);
							p1.updateInventory();
						}
					}}, (20 / config.getInt("shots_per_second")) * i);
			else if (i == 0)
				Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.getInstance(), new Runnable(){
					@Override
					public void run() {
						if (isFiring.contains(p1.getName())) {
							p1.getInventory().addItem(gun);
							if (p1.getInventory().first(gun) < 9)
								p1.getInventory().setHeldItemSlot(p1.getInventory().first(gun));
							p1.updateInventory();
							p1.launchProjectile(org.bukkit.entity.Snowball.class);
							p1.playSound(p1.getLocation(), Sound.ITEM_BREAK, 1, 1);
						}
					}}, (20 / config.getInt("shots_per_second")) * i);
			else
				Bukkit.getScheduler().scheduleSyncDelayedTask(RodsTwo.getInstance(), new Runnable(){
					@Override
					public void run() {
						if (isFiring.contains(p1.getName())) {
							p1.launchProjectile(org.bukkit.entity.Snowball.class);
							p1.playSound(p1.getLocation(), Sound.ITEM_BREAK, 1, 1);
						}
					}}, (20 / config.getInt("shots_per_second")) * i);
		return true;

	}

	@Override
	public boolean enable(Server serv) {
		isFiring = new HashSet<String>();
		Bukkit.getPluginManager().registerEvents(this, RodsTwo.getInstance());
		return true;
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onSnowballHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof org.bukkit.entity.Snowball && event.getEntity() instanceof Player) {
			Snowball ball = (Snowball) event.getDamager();
			if (ball.getShooter() instanceof Player) {
				Player player = (Player) ball.getShooter();
				if (isFiring.contains(player.getName()))
					event.setDamage(RodsTwo.getInstance().rodConfig.getInt(getPath("options.damage_per_shot")));
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onPlayerDeth(PlayerDeathEvent event) {
		if (isFiring.contains(event.getEntity().getName()))
			isFiring.remove(event.getEntity().getName());
	}
	
}
