package com.alazeprt;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static com.alazeprt.GriefpreventionMaintenancePlayerEvent.list;
import static com.alazeprt.GriefpreventionMaintenancePlayerEvent.timer;


public class GriefpreventionMaintenance extends JavaPlugin {

    @Override
    public void onEnable() {
        File file = new File(getDataFolder(), "data.yml");
        if(!file.exists()){
            saveResource("data.yml", false);
        }
        getServer().getPluginManager().registerEvents(new GriefpreventionMaintenanceClaimEvent(), this);
        getServer().getPluginManager().registerEvents(new GriefpreventionMaintenancePlayerEvent(), this);
        timer = new Timer(list);
        timer.start();
        Charge charge = new Charge();
        charge.start();
    }
}
