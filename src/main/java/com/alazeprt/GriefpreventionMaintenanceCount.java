package com.alazeprt;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Vector;

public class GriefpreventionMaintenanceCount {
    private final Player player;
    private BigDecimal money;

    public GriefpreventionMaintenanceCount(Player player){
        this.player = player;
        this.money = new BigDecimal(0);
        Vector<Claim> claims = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId()).getClaims();
        if(claims != null){
            for(Claim claim : claims){
                this.money = this.money.add(new BigDecimal(20));
                int area = claim.getArea();
                this.money = this.money.add(new BigDecimal((area * 0.4)));
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void updateData(){
        this.money = new BigDecimal(0);
        Vector<Claim> claims = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId()).getClaims();
        if(claims != null){
            for(Claim claim : claims){
                this.money = this.money.add(new BigDecimal(20));
                int area = claim.getArea();
                this.money = this.money.add(new BigDecimal((area * 0.4)));
            }
        }
    }
}
