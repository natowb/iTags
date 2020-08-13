package me.inato.tagredeem.events;

import me.inato.tagredeem.Main;
import me.inato.tagredeem.data.StoreData;
import me.inato.tagredeem.data.Tag;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerEvents implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent e) {

        Player player = e.getPlayer();


        if(Main.ins.store.getStoreData(player.getUniqueId())==null) {
            Main.ins.getServer().getConsoleSender().sendMessage(player.getName() + ": adding new StoreData");
            Main.ins.store.addStoreData(player);
        } else{
            Main.ins.store.updatePlayerTag(player);
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(Main.ins.store.getStoreData(player.getUniqueId())!=null) {
            StoreData pdata = Main.ins.store.getStoreData(player.getUniqueId());
            if(pdata.getSelectedTag()!=null) {
                Tag tag = Main.ins.store.getTag(pdata.getSelectedTag());
                String format = "<<group-prefix><player><group-suffix>> : <message>";
                format = format.replace("<player>", "%1$s"); //the player name will be automatically replaced by player.getDisplayName() you could write "%s" too but if you do it like that, you can place the message before the player's name
                format = format.replace("<group-prefix>", (tag.getPrefix()==null?"null":tag.getPrefix())); //something like that
                format = format.replace("<group-suffix>", (tag.getSuffix()==null?"null":tag.getSuffix())); //something like that
                format = format.replace("<message>", "%2$s");
                event.setFormat(format);
            }
        }
    }


}


