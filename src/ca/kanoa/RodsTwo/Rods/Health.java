<<<<<<< HEAD
package ca.kanoa.rodstwo.Rods;
=======
package ca.kanoa.rodstwo.rods;
>>>>>>> dev

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;

<<<<<<< HEAD
import ca.kanoa.rodstwo.Objects.ConfigOptions;
import ca.kanoa.rodstwo.Objects.Rod;
=======
import ca.kanoa.rodstwo.objects.ConfigOptions;
import ca.kanoa.rodstwo.objects.Rod;
>>>>>>> dev

public class Health extends Rod {

    public Health() throws Exception {
        super("Health", /*Cost*/ 1, /*RodID*/ 280,  new ConfigOptions(new String[]{"hearts_to_be_healed"}, new Object[]{10}), /*Cooldown (In MS)*/ 3000);
        
        //Set default the recipe
        ShapedRecipe recipe = new ShapedRecipe(super.getItem());
        recipe.shape("MRG");
        recipe.setIngredient('R', Material.STICK);
        recipe.setIngredient('M', Material.MELON);
        recipe.setIngredient('G', Material.GOLD_NUGGET);
        super.setRecipe(recipe);
    }

    @Override
    public boolean run(Player player, ConfigurationSection config){
        double currHealth = player.getHealth();
        int addHealth = config.getInt("hearts_to_be_healed");
        double newHealth = currHealth + addHealth > 20.0 ? 20.0 : currHealth + addHealth;
        if(currHealth == 20.0)
            return false;
        player.setHealth(newHealth);
        player.getWorld().strikeLightningEffect(player.getLocation());
        return true;
    }
}