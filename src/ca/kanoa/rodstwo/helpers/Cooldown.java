package ca.kanoa.rodstwo.helpers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cooldown implements Runnable {

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (ItemStack stack : p.getInventory().getContents()) {
				if (Utils.getRod(stack) != null && stack.getAmount() > 1) {
					stack.setAmount(stack.getAmount() - 1);
				}
			}
		}
	}

}
