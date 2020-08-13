package me.inato.tagredeem.data;

import me.inato.tagredeem.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class Store {

    private HashMap<String,Tag> database;
    private HashMap<UUID, StoreData> data;

    public Store() {
        this.database = new HashMap<>();
        this.data = new HashMap<>();
    }

    public StoreData getStoreData(UUID pID) {
        StoreData d = data.get(pID);
        return d;
    };

    public void saveStore(FileConfiguration config, File file) {
        for(UUID pID : data.keySet()) {
            Main.ins.getServer().getConsoleSender().sendMessage(pID.toString() + ": saving data");
            config.set("players."+pID+".tags", new ArrayList<String>(data.get(pID).getTags()));
            config.set("players."+pID+".selected", data.get(pID).getSelectedTag());
        }
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }

    public void addStoreData(Player player) {
        data.put(player.getUniqueId(), new StoreData("",new HashSet<>()));
    }

    public Tag getTag(String id) {
        for(String name : database.keySet()) {
            if(name.equalsIgnoreCase(id)) { return database.get(name); }
        }
        return null;
    }

    public boolean getTagByName(String name) {
        for(String t : database.keySet()) {
            if(t.equalsIgnoreCase(name)) { return true; }
        }
        return false;
    }

    public void loadStoreDatabase(FileConfiguration config) {
        try {
            for (String s : config.getConfigurationSection("tags").getKeys(false)) {
                String display = ChatColor.translateAlternateColorCodes('&', config.getString("tags."+s+".display"));
                String lore = ChatColor.translateAlternateColorCodes('&', config.getString("tags."+s+".lore"));
                String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("tags."+s+".prefix"));
                String suffix = ChatColor.translateAlternateColorCodes('&', config.getString("tags."+s+".suffix"));
                Tag tag = new Tag(display, lore, prefix, suffix, false);
                database.put(s, tag);
            }
        } catch (NullPointerException e) { e.printStackTrace(); }
    }

    public void loadStoreData(FileConfiguration config) {
        try {
            for (String s : config.getConfigurationSection("players").getKeys(false)) {
                StoreData d = new StoreData(config.getString("players."+s+".selected"),new HashSet<String>(config.getStringList("players."+s+".tags")));
                data.put(UUID.fromString(s), d);

            }
        } catch (NullPointerException e) { }
    }

}
