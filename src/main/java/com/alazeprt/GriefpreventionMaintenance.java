package com.alazeprt;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

import static com.alazeprt.GriefpreventionMaintenanceEvent.list;
import static com.alazeprt.GriefpreventionMaintenanceEvent.timer;


public class GriefpreventionMaintenance extends JavaPlugin {

    public static FileConfiguration config;

    private static Economy econ = null;

    @Override
    public void onEnable() {
        boolean isEconomy = setupEconomy();
        if(!isEconomy){
            getServer().getPluginManager().disablePlugin(this);
            throw new RuntimeException("未初始化经济引擎!");
        }
        File config = new File(getDataFolder(), "config.yml");
        if(!config.exists()){
            saveResource("config.yml", false);
        }
        GriefpreventionMaintenance.config = YamlConfiguration.loadConfiguration(config);
        File data = new File(getDataFolder(), "data.yml");
        if(!data.exists()){
            saveResource("data.yml", false);
        }
        getServer().getPluginManager().registerEvents(new GriefpreventionMaintenanceEvent(), this);
        timer = new Timer(list);
        timer.start();
        Charge charge = new Charge(econ);
        charge.start();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
