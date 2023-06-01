package com.alazeprt;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class hasClaim extends Thread {
    private List<Player> list;

    public hasClaim(List<Player> list){
        this.list = list;
    }

    public void updateList(List<Player> list){
        this.list = list;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTaskTimer(GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class), () -> {
            List<Player> noClaim = new ArrayList<>();
            for(Player player : list){
                if(GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId()).getClaims().size() == 0){
                    noClaim.add(player);
                }
            }
            if(!noClaim.isEmpty()){
                FileConfiguration data = YamlConfiguration.loadConfiguration(new File(GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class).getDataFolder(), "data.yml"));
                if(data.contains("players")){
                    List<String> players = data.getStringList("players");
                    Map<String, Integer> map = new HashMap<>();
                    for(String content : players){
                        String[] strings = content.split(";");
                        map.put(strings[0], Integer.valueOf(strings[1]));
                    }
                    for(Player player : noClaim){
                        map.remove(player.getName());
                    }
                    List<String> newlist = new ArrayList<>();
                    for(String key : map.keySet()){
                        newlist.add(key + ";" + map.get(key));
                    }
                    data.set("players", newlist);
                    try {
                        data.save(new File(GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class).getDataFolder(), "data.yml"));
                    } catch (IOException e) {
                        GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class).getLogger().warning("保存更新后的数据文件(data.yml)失败!");
                    }
                }
            }
        }, 0, 20L * 60L);
    }
}
