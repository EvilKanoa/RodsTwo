package ca.kanoa.rodstwo.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ShapedRecipe;

import ca.kanoa.rodstwo.rods.Rod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigRecipe {

    public static ShapedRecipe loadRecipeFromConfig(FileConfiguration config, Rod rod) throws InvalidRecipeException {
        //Load all the values from disk
        int amount = config.getInt(rod.getPath("recipe.amount"));
        List<String> shape = config.getStringList(rod.getPath("recipe.shape"));

        List<Character> chars = new ArrayList<Character>();
        for(String s : shape){
            for(char c : s.toCharArray())
                if(!chars.contains(c) && Character.isLetter(c))
                    chars.add(c);
        }
        Map<Character, Integer> ingredients = new HashMap<Character, Integer>();
        for(char c : chars)
            ingredients.put(c, config.getInt(rod.getPath("recipe.ingredients." + c)));

        //Create a new ShapedRecipe object and assign the vales, doing it this way lets us detect null values
        try {
            if(amount < 1) throw new InvalidRecipeException("Found negative or no value for amount");
            ShapedRecipe recipe = new ShapedRecipe(rod.getItem(amount));
            switch(shape.size()){
                case 1: recipe.shape(shape.get(0)); break;
                case 2: recipe.shape(shape.get(0), shape.get(1)); break;
                case 3: recipe.shape(shape.get(0), shape.get(1), shape.get(2)); break;
                default: throw new InvalidRecipeException("Too many strings for shape");
            }
            for(char c : chars){
                if(!Character.isLetter(c)) throw new InvalidRecipeException("Found null value for recipe, at ingredient: " + c);
                recipe.setIngredient(c, Material.getMaterial(ingredients.get(c)));
            }

            return recipe;
        } catch (NullPointerException e) {
            throw new InvalidRecipeException("Found null value for recipe");
        }
    }

    public static void saveDefaultRecipe(FileConfiguration config, Rod rod, boolean overwrite){
        if(config.get(rod.getPath("recipe.amount")) == null || overwrite)
            config.set(rod.getPath("recipe.amount"), rod.getRecipe().getResult().getAmount());

        if(config.get(rod.getPath("recipe.shape")) == null || overwrite)
            config.set(rod.getPath("recipe.shape"), rod.getRecipe().getShape());

        List<Character> chars = new ArrayList<Character>();
        for(String s : rod.getRecipe().getShape()){
            for(char c : s.toCharArray())
                if(!chars.contains(c) && Character.isLetter(c))
                    chars.add(c);
        }

        for(char c : chars){
            if(config.get(rod.getPath("recipe.ingredients." + c)) == null || overwrite)
                config.set(rod.getPath("recipe.ingredients." + c), rod.getRecipe().getIngredientMap().get(c).getTypeId());
        }

    }

}
