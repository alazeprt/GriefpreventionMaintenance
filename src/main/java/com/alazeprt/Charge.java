package com.alazeprt;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.yic.xconomy.api.XConomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.math.BigDecimal;
import java.util.Vector;

public class Charge extends Thread {
    @Override
    public void start() {
        Bukkit.getScheduler().runTaskTimer(GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class), () -> {
            YamlConfiguration playertime = YamlConfiguration.loadConfiguration(new File(GriefpreventionMaintenance.getProvidingPlugin(GriefpreventionMaintenance.class).getDataFolder(), "data.yml"));
            if(playertime.getStringList("players") != null){
                for(String time : playertime.getStringList("players")){
                    String[] sp = time.split(";");
                    if(Integer.parseInt(sp[1]) % 5 == 0){
                        Player player = Bukkit.getPlayerExact(sp[0]);
                        if(player != null && player.isOnline()){
                            Vector<Claim> claims = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId()).getClaims();
                            Claim smallestClaim = null;
                            BigDecimal money = new BigDecimal(0);
                            for(Claim claim : claims){
                                if(smallestClaim == null){
                                    smallestClaim = claim;
                                } else{
                                    if(smallestClaim.getArea() > claim.getArea()){
                                        smallestClaim = claim;
                                    }
                                }
                                money = money.add(BigDecimal.valueOf(claim.getArea() * 0.4));
                            }
                            XConomyAPI xcapi = new XConomyAPI();
                            BigDecimal player_money = xcapi.getPlayerData(player.getName()).getBalance();
                            if (player_money.compareTo(money) >= 0) {
                                xcapi.changePlayerBalance(player.getUniqueId(), player.getName(), money, false, "领地维护");
                                player.sendMessage(ChatColor.GREEN + "成功缴纳领地维护费用: " + money + "金币!");
                            } else {
                                GriefPrevention.instance.dataStore.deleteClaim(smallestClaim);
                                player.sendMessage(ChatColor.RED + "你没有足够的金额缴纳领地维护费用: " + money + "金币!已删除你最小的一块领地!");
                            }
                        }
                    }
                }
            }
        }, 0, 60L * 20L);
    }
}
