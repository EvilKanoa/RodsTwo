package ca.kanoa.RodsTwo.Objects;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public abstract class Rod {

    private final String name;
    private final ConfigOptions options;
    private final int rodID;
    private long cooldown;
    private int cost;
    private final long defaultCooldown;
    private final int defaultCost;
    private ShapedRecipe recipe;
    private final Plugin plugin;

    public Rod(String name, int cost, int rodID, ConfigOptions options, long cooldown, Plugin plugin){
        this.name = name;
        this.cost = cost;
        this.options = options;
        this.rodID = rodID;
        this.cooldown = cooldown;
        this.plugin = plugin;
        this.defaultCooldown = cooldown;
        this.defaultCost = cost;
    }

    public long getDefaultCooldown(){
        return defaultCooldown;
    }

    public int getDefaultCost(){
        return defaultCost;
    }

    public String getName(){
        return  name;
    }

    public int getRodID(){
        return rodID;
    }

    public Permission getPermission(){
        return new Permission("lr.use." + name, "Default permission node for " + name + " rod.");
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
        return recipe;
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

    public abstract boolean run(Player player, FileConfiguration config);

    public boolean enable(Server server){
        return true;
    }
}
