package ca.kanoa.rodstwo.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ca.kanoa.rodstwo.RodsTwo;

public class Store implements Listener {

	private Player buyer;
	private Inventory storeFront;

	public Store(Player sender) {
		this.buyer = sender;
		populateStore();
		Bukkit.getPluginManager().registerEvents(this, RodsTwo.plugin);
	}

	private void populateStore() {
		storeFront = Bukkit.createInventory(buyer, roundUp(RodsTwo.rods.size()), "LightningRod Store");
		for (Rod rod : RodsTwo.rods)
			storeFront.addItem(rod.getItem(1));
	}

	public void show() {
		buyer.openInventory(storeFront);
	}

	private int roundUp(int n) {
		return ((n + 9 - 1) / 9) * 9;
	}

	//Events

	@EventHandler (priority=EventPriority.MONITOR)
	public void onInvClose(InventoryCloseEvent event) {
		if (event.getPlayer().getEntityId() == buyer.getEntityId()) {
			HandlerList.unregisterAll(this);
			buyer.sendMessage(storeMsg("Closing the store..."));
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler (priority=EventPriority.HIGH)
	public void onItemClicked(InventoryClickEvent event) {
		if (event.getWhoClicked().getName().equalsIgnoreCase(buyer.getName())) {
			if (event.getRawSlot() <= storeFront.getSize()) {
			ItemStack is = event.getCurrentItem();
			for (Rod rod : RodsTwo.rods)
				if (is != null && is.getItemMeta() != null && 
				is.getItemMeta().getLore().get(0) != null && 
				is.getItemMeta().getLore().get(0).equalsIgnoreCase(rod.getItem().getItemMeta().getLore().get(0))) {
					buyer.getInventory().addItem(rod.getItem(event.isShiftClick() ? 64 : 1));
					buyer.sendMessage(storeMsg("Buying a " + rod.getName() + " rod!"));
					buyer.updateInventory();
					event.setCancelled(true);
				}
			}
			else if (event.isShiftClick())
				event.setCancelled(true);
		}
	}

	public static String storeMsg(String msg) {
		return ChatColor.BLACK + "" + ChatColor.MAGIC + "....." + ChatColor.RESET + ChatColor.YELLOW + msg + ChatColor.BLACK + ChatColor.MAGIC + ".....";
	}
	
	public static boolean isSame(String s1, String s2) {
		return s1.contains(s2) && s2.contains(s1);
	}

}
