package me.inato.tagredeem.commands;

import me.inato.tagredeem.Lib;
import me.inato.tagredeem.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveCommand {

    private JavaPlugin plugin;
    private Player player;
    private String[] args;
    private boolean notify;

    public GiveCommand(JavaPlugin plugin, Player player, String[] args, boolean notify) {
        this.plugin = plugin;
        this.player = player;
        this.args = args;
        this.notify = notify;
        run();
    }



    public void run() {

        if(!Lib.validatePerms(player, "give")) {
            Lib.errorMsg(player, "you don't have permissions to use that command");
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        String tagName = args[2];


        if(target == null) {
            Lib.errorMsg(player, "&6<"+args[1]+">&c not a valid player");
            return;
        }
        if(!Main.ins.store.getTagByName(tagName)) {
            Lib.errorMsg(player, "&6<"+tagName+">&c not a valid tag");
            return;
        }

        giveTag(player, target, tagName);
    }

    private void giveTag(Player player, Player target, String tagName) {
        Main.ins.store.getStoreData(target.getUniqueId()).getTags().add(tagName.toLowerCase());
        Lib.successMsg(player, "Gave &6<"+tagName+">&a to &b<"+target.getName()+">" ); // command sender that he successfully gave the tag
        Lib.msg(player, "&aReceived &6<" + tagName + ">&a from &b<" + player.getName()+">"); // notify target they got a new tag
    }
}
