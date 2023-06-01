package com.alazeprt;

import me.ryanhamshire.GriefPrevention.events.ClaimChangeEvent;
import me.ryanhamshire.GriefPrevention.events.ClaimCreatedEvent;
import me.ryanhamshire.GriefPrevention.events.ClaimDeletedEvent;
import me.ryanhamshire.GriefPrevention.events.ClaimExpirationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GriefpreventionMaintenanceClaimEvent implements Listener {


    public static GriefpreventionMaintenanceCount count;

    @EventHandler
    public void onPlayerCreateClaim(ClaimCreatedEvent create){
        count.updateData();
    }

    @EventHandler
    public void onPlayerDeleteClaim(ClaimDeletedEvent delete){
        count.updateData();
    }

    @EventHandler
    public void onClaimExpiration(ClaimExpirationEvent expiration){
        count.updateData();
    }

    @EventHandler
    public void onPlayerChangeClaim(ClaimChangeEvent change){
        count.updateData();
    }
}
