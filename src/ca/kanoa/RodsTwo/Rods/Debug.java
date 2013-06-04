package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

/**
 * Created with IntelliJ IDEA.
 * User: Kanoa
 * Date: 13/04/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Debug extends Rod {

    public Debug(Plugin plugin) throws Exception {
        super("Debug", 32, 280, new ConfigOptions(new String[]{}, new Object[]{}), 500, plugin);
        super.setRecipe(new ShapedRecipe(super.getItem()).shape("DDD", "DRD", "DDD").setIngredient('D', Material.DIAMOND).setIngredient('R', Material.BLAZE_ROD));
    }

    @Override
    public boolean run(Player player, FileConfiguration config) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
