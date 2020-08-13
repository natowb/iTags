package me.inato.tagredeem;

import me.inato.tagredeem.commands.ManageCommand;
import me.inato.tagredeem.data.Store;
import me.inato.tagredeem.events.TagEvents;
import me.inato.tagredeem.events.PlayerEvents;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    private File storeFile;
    private FileConfiguration storeConfig;


    public static Main ins;
    public Store store;

    @Override
    public void onEnable() {
        ins = this;
        Lib.init(this);
        getCommand("itags").setExecutor(new ManageCommand());
        getServer().getPluginManager().registerEvents(new TagEvents(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        setupConfig();
        store = new Store();
        store.loadStoreDatabase(getConfig());
        store.loadStoreData(storeConfig);
    }

    public void setupConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        createCustomConfig();
    }


    private void createCustomConfig() {
        storeFile = new File(getDataFolder(), "store.yml");
        if (!storeFile.exists()) {
            storeFile.getParentFile().mkdirs();
            saveResource("store.yml", false);
        }

        storeConfig= new YamlConfiguration();
        try {
            storeConfig.load(storeFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("saving store");
        store.saveStore(storeConfig, storeFile);
    }
}
