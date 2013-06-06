package ca.kanoa.RodsTwo.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ca.kanoa.RodsTwo.RodsTwo;
import ca.kanoa.RodsTwo.Helpers.Utils;
import ca.kanoa.RodsTwo.Helpers.VaultManager;
import ca.kanoa.RodsTwo.Objects.Rod;

public class SignListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void signClick(PlayerInteractEvent event) {
		Block b = event.getClickedBlock();
		Material mat = b == null ? Material.AIR : b.getType();
		if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK) || b == null ||
				(mat != Material.SIGN && mat != Material.SIGN_POST && mat != Material.WALL_SIGN))
			return;
		Sign sign = (Sign) b.getState();
		if (sign.getLine(0).equalsIgnoreCase("[LightningRod]")) {
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
				//Vault
				if (VaultManager.eco) {
					if (VaultManager.vaultEco.getBalance(event.getPlayer().getName()) < cost){
						event.getPlayer().sendMessage(Utils.signMsg("You don't have enough money!"));
						return;
					}
					//remove money
					VaultManager.vaultEco.withdrawPlayer(event.getPlayer().getName(), cost);
				}
				else
					Bukkit.getLogger().warning(event.getPlayer().getName() + " is buying a LightningRod but vault is disabled, no money will be withdrawn!");

				ItemStack rodStack = rod.getItem(amount);
				event.getPlayer().getInventory().addItem(rodStack);
				event.getPlayer().updateInventory();
				event.getPlayer().sendMessage(Utils.signMsg("You just bought " + amount + " " + rod.getName() + " Rod(s), for " 
						+ (VaultManager.eco ? VaultManager.vaultEco.format(cost) : cost + " dollars") + "."));

			} catch (Exception e){
				event.getPlayer().sendMessage(Utils.signMsg("Badly formated sign, tell an admin!"));
			}
		}
	}

}
