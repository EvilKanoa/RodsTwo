package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Default extends Rod {

	public Default(Plugin plugin) throws Exception {
        super("Debug", 32, 280, new ConfigOptions(null, null), 500, plugin);
        super.setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DRD", "DDD").setIngredient('D', Material.DIAMOND).setIngredient('R', Material.BLAZE_ROD));
    }

    @Override
    public boolean run(Player player, ConfigurationSection config) {
        return false; 
    }
}
