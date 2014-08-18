package ca.kanoa.rodstwo.helpers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.RodsTwo;
import ca.kanoa.rodstwo.config.ConfigRecipe;
import ca.kanoa.rodstwo.config.InvalidRecipeException;
import ca.kanoa.rodstwo.rods.Rod;

import java.io.File;
import java.io.IOException;

public class Utils {

	public static void makeConfig(boolean overwrite) {
		File configFile = new File(RodsTwo.getInstance().getDataFolder(), "rods.yml");
		if (!configFile.exists()) {
			RodsTwo.getInstance().saveResource("rods.yml", true);
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

		for (Rod rod : RodsTwo.rods) {
			if (config.get(rod.getPath("enabled")) == null || overwrite) {
				config.set(rod.getPath("enabled"), true);
			}
			if (config.get(rod.getPath("uses")) == null || overwrite) {
				config.set(rod.getPath("uses"), overwrite ? rod.getDefaultUses() : rod.getUses());
			}
			if (config.get(rod.getPath("cooldown")) == null || overwrite) {
				config.set(rod.getPath("cooldown"), overwrite ? rod.getDefaultCooldown() : rod.getCooldown());
			}
			if (config.get(rod.getPath("itemID")) == null) {
				config.set(rod.getPath("itemID"), overwrite ? rod.getDefaultItemID() : rod.getItemID());
			}
			ConfigRecipe.saveDefaultRecipe(config, rod, overwrite);

			for (Object[] s : rod.getOptions().getAllOptionsAsArray()) {
				if (config.get(rod.getPath("options." + s[0])) == null || overwrite)
					config.set(rod.getPath("options." + s[0]), s[1]);
			}
		}

		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Rod getRod(ItemStack stack) {
		if (stack == null) {
			return null;
		}
		for (Rod rod : RodsTwo.getInstance().getRods()) {
			if (stack.getTypeId() == rod.getItemID() && 
					stack.getItemMeta().getLore().contains(rod.getName())) {
				return rod;
			}
		}
		return null;
	}
	
	//TODO: load set of rods from folder plugins/lr2/rods

	public static void loadRodOptions() {
		for (Rod rod : RodsTwo.rods) {
			try {
				rod.setCooldown(RodsTwo.getInstance().rodConfig.getLong(rod.getPath("cooldown")));
				rod.setItemID(RodsTwo.getInstance().rodConfig.getInt(rod.getPath("itemID")));
				rod.setUses(RodsTwo.getInstance().rodConfig.getInt(rod.getPath("uses")));
			} catch (Exception e) {
				RodsTwo.getInstance().logger.warning("Invalid config formatting for rod: " + rod.getName() + ".");
			}
		}
	}

	public static void addRecipes() {
		for (Rod rod : RodsTwo.rods) {
			try {
				ShapedRecipe recipe = ConfigRecipe.loadRecipeFromConfig(RodsTwo.getInstance().rodConfig, rod);
				RodsTwo.getInstance().getServer().addRecipe(recipe);
				rod.setRecipe(recipe);
			} catch (InvalidRecipeException e) {
				e.printStackTrace();
				RodsTwo.getInstance().logger.warning("Error while loading recipe for rod: " + rod.getName() + "!");
			} catch(NullPointerException e){ e.printStackTrace(); }
		}
	}

	public static boolean isCooldownOver(ItemStack item) {
		return getRod(item) != null && item.getAmount() == 1;
	}

	public static void initializeRods() {
		for (Rod rod : RodsTwo.rods) {
			if (!rod.enable(RodsTwo.getInstance().getServer())) {
				RodsTwo.rods.remove(rod);
			}
		}
	}

	public static void debug(String msg) {
		Bukkit.broadcastMessage(ChatColor.YELLOW + "" + '[' + ChatColor.AQUA + "Debug" + ChatColor.YELLOW + "] " + ChatColor.RED + msg);
	}

	public static String signMsg(String msg) {
		return ChatColor.BLACK + "[" + ChatColor.YELLOW + "SIGN" + ChatColor.BLACK + "] " + ChatColor.AQUA + msg;
	}
}
