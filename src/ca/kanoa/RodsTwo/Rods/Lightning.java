package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Lightning extends Rod {

    public Lightning(Plugin plugin) throws Exception {
        super("Lightning", 1, 280,  new ConfigOptions(new String[]{"max_distance"}, new Object[]{50}), 1500, plugin);

        //Set default the recipe
        super.setRecipe(new ShapedRecipe(super.getItem()).shape(" I ", " R ", " I ").setIngredient('I', Material.IRON_INGOT).setIngredient('R', Material.STICK));
    }

    @Override
    public boolean run(Player player, ConfigurationSection config){
        player.getWorld().strikeLightning(player.getTargetBlock(null, config.getInt("max_distence")).getLocation());
        return true;
    }
}