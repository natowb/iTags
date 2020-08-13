package me.inato.tagredeem.events;

import me.inato.tagredeem.Lib;
import me.inato.tagredeem.Main;
import me.inato.tagredeem.commands.RevokeCommand;
import me.inato.tagredeem.data.StoreData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class TagEvents implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent e){
        try {
            if(e.getView().getTitle().equalsIgnoreCase("iTags Collection")){
                Player player = (Player) e.getWhoClicked();
                //Determine what they selected and what to do
                if(e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                    player.closeInventory();
                    e.setCancelled(true);
                    return;
                }
                if(e.getCurrentItem() == null) {
                    return;
                }
                String tag = e.getCurrentItem().getItemMeta().getLore().get(1);
                String[] tagSplit = tag.split(": ");
                tag = tagSplit[1];
                StoreData pdata = Main.ins.store.getStoreData(player.getUniqueId());
                if(e.getCurrentItem().getType() == Material.NAME_TAG) {
                    if(e.getClick().isRightClick()) {
                        player.sendMessage(tag);
                        if(pdata.getSelectedTag() != null) {
                            if(pdata.getSelectedTag().equalsIgnoreCase(tag)) {
                                Main.ins.store.getStoreData(player.getUniqueId()).setSelectedTag(null);
                            }
                        }
                        player.sendMessage("test");
                        RevokeCommand.revokeTag(null, player, tag, false);
                        player.getInventory().addItem(e.getCurrentItem());
                        Lib.successMsg(player ,"you withdrew the &6<"+tag+">&a tag");
                    }
                    player.closeInventory();
                    e.setCancelled(true);
                }
                if(e.getClick().isLeftClick()){

                    if(pdata.getSelectedTag()!=null) {
                        if(pdata.getSelectedTag().equalsIgnoreCase(tag)) {
                            Main.ins.store.getStoreData(player.getUniqueId()).setSelectedTag(null);
                            Lib.successMsg(player ,"you &c<deselected>&a the &6<"+tag+">&a tag");
                            return;
                        }
                    }

                    Main.ins.store.getStoreData(player.getUniqueId()).setSelectedTag(tag);
                    Lib.successMsg(player ,"you <selected> the &6<"+tag+">&a tag");
                }
            }
        }
        catch (NullPointerException ex) {
            // Stops error if player clicks outside of inventory // should change bad practice
            ex.printStackTrace();
        }
        catch (Exception exx) {
            exx.printStackTrace();
        }
    }
    @EventHandler
    public void useItem(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if((event.getHand() == EquipmentSlot.HAND && event.getItem() !=null) && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK )) {
            if(event.getItem().getType().equals(Material.NAME_TAG)) {
                if(event.getItem().getItemMeta().getLore().size()>=2) {
                    String tag = event.getItem().getItemMeta().getLore().get(1);
                    String[] tagSplit = tag.split(": ");
                    tag = tagSplit[1];
                    if(Main.ins.store.getTagByName(tag)) {
                        Main.ins.store.getStoreData(event.getPlayer().getUniqueId()).getTags().add(tag);
                        player.getInventory().getItemInMainHand().setAmount(0);
                        event.setCancelled(true);
                        Lib.successMsg(player, "You added the &6<"+tag+">&a tag, to your collection");
                    }
                }
            }
        }
    }
}