package me.inato.tagredeem.commands;

import me.inato.tagredeem.Lib;
import me.inato.tagredeem.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RevokeCommand {

    private JavaPlugin plugin;
    private Player player;
    private String[] args;
    private boolean notify;

    public RevokeCommand(JavaPlugin plugin, Player player, String[] args, boolean notify) {
        this.plugin = plugin;
        this.player = player;
        this.args = args;
        this.notify = notify;

        run();
    }



    public void run() {

        if(!Lib.validatePerms(player, "revoke")) {
            Lib.errorMsg(player, "you dont have access to this command");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {
            player.sendMessage("not a valid player");
            return;
        }
        String tagName = args[2];
        if(!Main.ins.store.getTagByName(tagName)) {
            player.sendMessage("not a valid tag");
        } else{
            revokeTag(player, target, tagName, notify);
        }
    }


    public static void revokeTag(Player player, Player target, String tagName, boolean notify){

        Main.ins.store.getStoreData(target.getUniqueId()).getTags().remove(tagName);
        if(notify) {
            player.sendMessage("Revoked " + tagName + " from " + target.getName());
            target.sendMessage(player.getName()+ " Revoked " + tagName + " from you");
        }
    }

}
