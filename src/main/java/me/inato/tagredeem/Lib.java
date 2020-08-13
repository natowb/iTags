package me.inato.tagredeem;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Lib {

    private static JavaPlugin plugin;

    public static void init(JavaPlugin pl) {
        plugin = pl;
    }

    public static void msg(Player player, String message) {
        String pre = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));
        String msg = ChatColor.translateAlternateColorCodes('&', message);
        player.sendMessage(pre + " " +msg);
    }



    public static void errorMsg(Player player, String message) {
        String pre = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));
        String msg = ChatColor.translateAlternateColorCodes('&', "&cError: "+ message);
        player.sendMessage(pre + " " +msg);
    }

    public static void successMsg(Player player, String message) {
        String pre = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"));
        String msg = ChatColor.translateAlternateColorCodes('&', "&aSuccess: "+ message);
        player.sendMessage(pre + " " +msg);
    }

    public static boolean validatePerms(Player player, String perm) {
        return player.hasPermission("itags."+perm);
    }

    public static void errorPerms(Player player) {


    }




}
