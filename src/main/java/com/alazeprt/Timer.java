package com.alazeprt;

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

public class Timer extends Thread {
    private List<Player> list;

    public Timer(List<Player> list){
        this.list = list;
    }

    public void updateList(List<Player> list){
        this.list = list;
    }

    @Override
    public void start() {
        Bukkit.getScheduler().runTaskTimer(GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class), () -> {
            GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class).getLogger().info("开始读取并保存玩家在线数据...目前的列表为: " + list);
            FileConfiguration fc = YamlConfiguration.loadConfiguration(new File(GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class).getDataFolder(), "data.yml"));
            if(list != null){
                List<String> time = fc.getStringList("players");
                if(time == null){
                    time = new ArrayList<>();
                }
                Map<String, Integer> map = new HashMap<>();
                for(String content : time){
                    String[] strings = content.split(";");
                    map.put(strings[0], Integer.valueOf(strings[1]));
                }
                int i = 0;
                for(Player player : list){
                    if(map.containsKey(player.getName())){
                        int count = map.get(player.getName()) + 1;
                        time.set(i, (player.getName() + ";" + count));
                    } else{
                        time.add((player.getName() + ";" + "1"));
                    }
                    i++;
                }
                fc.set("players", time);
                try {
                    fc.save(new File(GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class).getDataFolder(), "data.yml"));
                } catch (IOException e) {
                    GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class).getLogger().warning(e.getLocalizedMessage());
                }
            }
        }, 0, 20*60);
    }
}
