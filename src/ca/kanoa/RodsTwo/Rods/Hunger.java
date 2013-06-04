package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Hunger extends Rod {

	public Hunger(Plugin plugin) throws Exception {
        super("Hunger", 1, 280, new ConfigOptions(new String[]{"hunger_given"}, new Object[]{10}), 500, plugin);
        super.setRecipe(new ShapedRecipe(super.getItem()).shape(" P ", "FBF", " P ").setIngredient('P', Material.COOKED_BEEF).setIngredient('F', Material.BREAD).setIngredient('B', Material.STICK));
    }

    @Override
    public boolean run(Player player, ConfigurationSection config) {
    	int currHunger = player.getFoodLevel();
    	if (currHunger >= 20)
    		return false; 
    	
    	int foodToGive = config.getInt("hunger_given");
    	int hunger = currHunger + foodToGive > 20 ? 20 : currHunger + foodToGive;
    	player.getWorld().playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 50);
    	player.setFoodLevel(hunger);
    	
    	return true;
    }
}
