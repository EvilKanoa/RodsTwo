package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Lightning extends Rod {

    public Lightning(Plugin plugin) throws Exception {
        super("Lightning", 1, 280,  new ConfigOptions(new String[]{"maxDistance"}, new Object[]{50}), 1500, plugin);

        //Set default the recipe
        ShapedRecipe recipe = new ShapedRecipe(super.getItem());
        recipe.shape(" I ", " R ", " I ");
        recipe.setIngredient('I', Material.IRON_INGOT);
        recipe.setIngredient('R', Material.getMaterial(super.getRodID()));
        super.setRecipe(recipe);
    }

    @Override
    public boolean run(Player player, FileConfiguration config){
        player.getWorld().strikeLightning(player.getTargetBlock(null, config.getInt(super.getPath("options.maxDistence"))).getLocation());
        return true;
    }
}