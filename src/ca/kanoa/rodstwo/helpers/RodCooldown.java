package ca.kanoa.rodstwo.helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ca.kanoa.rodstwo.RodsTwo;

public class RodCooldown implements Runnable, Listener {

	private static long currentCooldownID = 0;

	private Player player;
	private int taskID;
	private final ItemStack item;
	private final long cooldownID;

	public RodCooldown(Player player, long cooldown) {
		this.player = player;
		this.item = player.getItemInHand();
		this.cooldownID = ++currentCooldownID;
		player.setItemInHand(generateItem(item, cooldown, cooldownID));
		player.updateInventory();
		Bukkit.getPluginManager().registerEvents(this, RodsTwo.getInstance());
	}

	@Override
	public void run() {
		ItemStack[] contents = player.getInventory().getContents();
		for (int i = 0; i < contents.length; i++) {
			ItemStack item = contents[i];
			if (item != null && isMyCooldownItem(item)) {
				if (item.getAmount() > 1) {
					item.setAmount(item.getAmount() - 1);
					ItemMeta im = item.getItemMeta();
					im.setDisplayName(name(im.getDisplayName()));
					item.setItemMeta(im);
				} else {
					contents[i] = this.item;
					player.getInventory().setContents(contents);
					player.updateInventory();
					Bukkit.getScheduler().cancelTask(taskID);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (event.getPlayer() == player) {
			haltCooldown();
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (event.getEntity() == player) {
			haltCooldown();
		}
	}

	private void haltCooldown() {
		ItemStack[] contents = player.getInventory().getContents();
		for (int i = 0; i < contents.length; i++) {
			ItemStack item = contents[i];
			if (item != null && isMyCooldownItem(item)) {
				contents[i] = this.item;
				player.getInventory().setContents(contents);
				player.updateInventory();
				Bukkit.getScheduler().cancelTask(taskID);
			}
		}
	}
	
	public long getCooldownID() {
		return cooldownID;
	}

	private boolean isMyCooldownItem(ItemStack item) {
		try {
			return item.getItemMeta().getLore().get(2).contains(Long.toString(cooldownID));
		} catch (Exception ex) {
			return false;
		}
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	private static String name(String name) {
		String end = name.substring(name.indexOf("."));
		name = name.substring(0, name.indexOf("."));
		switch (end.length()) {
		case 1: 
			return name + "..";
		case 2:
			return name + "...";
		case 3:
			return name + ".";
		default:
			return name + "...";
		}
	}

	private static ItemStack generateItem(ItemStack orig, long cooldown, long cooldownID) {
		ItemStack item = new ItemStack(orig);
		item.setType(Material.INK_SACK);
		item.setDurability((short) 8);
		item.setAmount((int) Math.ceil(cooldown / (float) 1000));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(meta.getDisplayName() + "...");
		List<String> lore = (meta.getLore() != null ? meta.getLore() : new ArrayList<String>());
		lore.add("Cooldown ID: " + cooldownID);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public static boolean isCooldownItem(ItemStack item) {
		try {
			return item.getType() == Material.INK_SACK &&
					item.getDurability() == (short) 8 &&
					item.getItemMeta().getLore().get(2).contains("Cooldown ID: ");
		} catch (Exception ex) {
			return false;
		}
	}

}
