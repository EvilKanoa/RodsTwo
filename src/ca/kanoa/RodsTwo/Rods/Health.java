package ca.kanoa.RodsTwo.Rods;

import ca.kanoa.RodsTwo.Objects.ConfigOptions;
import ca.kanoa.RodsTwo.Objects.Rod;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Health extends Rod {

    public Health(Plugin plugin) throws Exception {
        super("Health", /*Cost*/ 1, /*RodID*/ 280,  new ConfigOptions(new String[]{"heartsToBeHealed"}, new Object[]{10}), /*Cooldown (In MS)*/ 3000, /*Main Plugin Object*/ plugin);
        
        //Set default the recipe
        ShapedRecipe recipe = new ShapedRecipe(super.getItem());
        recipe.shape("MRG");
        recipe.setIngredient('R', Material.getMaterial(super.getRodID()));
        recipe.setIngredient('M', Material.MELON);
        recipe.setIngredient('G', Material.GOLD_NUGGET);
        super.setRecipe(recipe);
    }

    @Override
    public boolean run(Player player, FileConfiguration config){
        int currHealth = player.getHealth();
        int addHealth = config.getInt(super.getPath("options.heartsToBeHealed"));
        int newHealth = currHealth + addHealth > 20 ? 20 : currHealth + addHealth;
        if(currHealth == 20)
            return false;
        player.setHealth(newHealth);
        player.getWorld().strikeLightningEffect(player.getLocation());
        return true;
    }
    
    @Override
    public boolean enable(Server server){
        return true;
    }
}