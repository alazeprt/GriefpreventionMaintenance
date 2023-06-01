package com.alazeprt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import com.alazeprt.Timer;

public class GriefpreventionMaintenancePlayerEvent implements Listener {

    public static Timer timer;

    public static List<Player> list = new ArrayList<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent join){
        GriefpreventionMaintenanceClaimEvent.count = new GriefpreventionMaintenanceCount(join.getPlayer());
        list.add(join.getPlayer());
        timer.updateList(list);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent quit){
        list.remove(quit.getPlayer());
        timer.updateList(list);
    }
}
