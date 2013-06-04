package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Companion extends Rod {

	public Companion(Plugin plugin) throws Exception {
        super("Companion", 1, 280, new ConfigOptions(new String[]{"number_to_spawn"}, new Object[]{2}), 500, plugin);
        super.setRecipe(new ShapedRecipe(super.getItem()).shape(" W ", "WBW", " W ").setIngredient('W', Material.BONE).setIngredient('B', Material.STICK));
    }

    @Override
    public boolean run(Player player, ConfigurationSection config) {
        for (int i = 0; i < config.getInt("number_to_spawn"); i++) {
        	Wolf wolf = player.getWorld().spawn(player.getLocation(), Wolf.class);
        	wolf.setTamed(true);
        	wolf.setOwner(player);
        }
        return true;
    }
}
