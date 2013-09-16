package ca.kanoa.rodstwo.objects;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import ca.kanoa.rodstwo.RodsTwo;

import java.util.Arrays;

public abstract class Rod {

    private final String name;
    private final ConfigOptions options;
    private final int defaultRodID;
    private long cooldown;
    private int cost;
    private int rodID;
    private final long defaultCooldown;
    private final int defaultCost;
    private ShapedRecipe recipe;
    private final Plugin plugin;

    /**
     * The default lightning rod
     * @param name The name of the rod, shows up in inventorys and on the item
     * @param cost How many rods get used when you right click
     * @param rodID The default itemID for the rod
     * @param options The options that get placed into the config (under: Rods.rodName.options)
     * @param cooldown The cooldown after using this rod in milliseconds
     * @param plugin The original plugin, USE OTHER CONSTRUSTOR
     */
    @Deprecated
    public Rod(String name, int cost, int rodID, ConfigOptions options, long cooldown, Plugin plugin){
        this.name = name;
        this.cost = cost;
        this.options = options;
        this.defaultRodID = rodID;
        this.rodID = rodID;
        this.cooldown = cooldown;
        this.plugin = RodsTwo.plugin;
        this.defaultCooldown = cooldown;
        this.defaultCost = cost;
    }
    
    /**
     * The default lightning rod
     * @param name The name of the rod, shows up in inventorys and on the item
     * @param cost How many rods get used when you right click
     * @param rodID The default itemID for the rod
     * @param options The options that get placed into the config (under: Rods.rodName.options)
     * @param cooldown The cooldown after using this rod in milliseconds
     */
    public Rod(String name, int cost, int rodID, ConfigOptions options, long cooldown){
    	this.name = name;
        this.cost = cost;
        this.options = options;
        this.defaultRodID = rodID;
        this.rodID = rodID;
        this.cooldown = cooldown;
        this.plugin = RodsTwo.plugin;
        this.defaultCooldown = cooldown;
        this.defaultCost = cost;
    }

    public long getDefaultCooldown(){
        return defaultCooldown;
    }
    
    public void setItemID(int id) {
    	this.rodID = id;
    }
    
    public int getItemID() {
    	return this.rodID;
    }
    
    public int getDefaultItemID() {
    	return this.defaultRodID;
    }

    public int getDefaultCost(){
        return defaultCost;
    }

    public String getName(){
        return  name;
    }

    public Permission getUsePermission(){
        return new Permission("lr.use." + name, "Default permission node for " + name + " rod.");
    }
    
    public Permission getCraftPermission(){
        return new Permission("lr.craft." + name, "Default crafting permission node for " + name + " rod.");
    }

    public int getCost(){
        return cost;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public ConfigOptions getOptions(){
        return options;
    }

    public long getCooldown(){
        return cooldown;
    }

    public void setCooldown(long cooldown){
        this.cooldown = cooldown;
    }

    public ShapedRecipe getRecipe(){
        return recipe == null ? (new ShapedRecipe(getItem())).shape(" ") : recipe;
    }

    public void setRecipe(ShapedRecipe recipe){
        this.recipe = recipe;
    }

    public ItemStack getItem(){
        return getItem(1);
    }

    public ItemStack getItem(int amount){
        ItemStack is = new ItemStack(Material.getMaterial(rodID), amount);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name + " Rod");
        im.setLore(Arrays.asList(new String[]{name}));
        is.setItemMeta(im);
        return is;
    }

    public Plugin getPlugin(){
        return plugin;
    }

    public String getPath(String path){
        return "Rods." + this.name + "." + path;
    }

    public abstract boolean run(Player player, ConfigurationSection config);

    public boolean enable(Server server){
        return true;
    }
}
