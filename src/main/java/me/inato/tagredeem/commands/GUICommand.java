package me.inato.tagredeem.commands;

import me.inato.tagredeem.data.Store;
import me.inato.tagredeem.data.StoreData;
import me.inato.tagredeem.data.Tag;
import me.inato.tagredeem.Main;
import org.bukkit.Bukkit;

import org.bukkit.ChatColor;

import org.bukkit.Material;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;



import java.util.ArrayList;



public class GUICommand {

    public GUICommand(Player player) {
        Store s = Main.ins.store;
        Inventory gui = Bukkit.createInventory(player, 27, "iTags Collection");
        ArrayList<ItemStack> menu_items = new ArrayList<>();
        try {
            StoreData pdata = s.getStoreData(player.getUniqueId());

            ArrayList<String> tags = new ArrayList<>();

            if(pdata.getTags() != null)  {
                tags = new ArrayList<String>(s.getStoreData(player.getUniqueId()).getTags());
            }
        Main.ins.getServer().getConsoleSender().sendMessage(String.valueOf(tags.size()));

        for (int i = 0; i < 26; i++) {
            if (i < tags.size()) {
                Main.ins.getServer().getConsoleSender().sendMessage(tags.get(i));
                Tag t = s.getTag(tags.get(i));
                if (t == null) {
                    continue;
                }
                ItemStack item = new ItemStack(Material.NAME_TAG);
                ItemMeta meta = item.getItemMeta();
                try {
                    if (s.getStoreData(player.getUniqueId()).getSelectedTag().equalsIgnoreCase(tags.get(i))) {
                        meta.addEnchant(Enchantment.LUCK, 0, true);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                } catch (NullPointerException e) {}

                meta.setDisplayName(t.getDisplay());
                ArrayList<String> pre_lore = new ArrayList<>();
                pre_lore.add(t.getLore());
                pre_lore.add(ChatColor.translateAlternateColorCodes('&',"&eiTags: ") + tags.get(i));
                meta.setLore(pre_lore);
                item.setItemMeta(meta);
                menu_items.add(item);
            }
        }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    gui.setContents(menu_items.toArray(new ItemStack[0]));
    player.openInventory(gui);
    }

}


