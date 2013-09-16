<<<<<<< HEAD
package ca.kanoa.rodstwo.Helpers;
=======
package ca.kanoa.rodstwo.helpers;
>>>>>>> dev

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultManager {

    public static Economy vaultEco = null;
    public static boolean eco = false;
    
    public static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null)
            vaultEco = economyProvider.getProvider();
        return (vaultEco != null);
    }
}