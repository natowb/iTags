package me.inato.tagredeem.commands;

import me.inato.tagredeem.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if(sender instanceof Player) {
        Player player = (Player) sender;
            switch (args.length) {
                case 0: {
                    if(!player.hasPermission("tags.gui")) {
                        lackPermisions(player);
                        return true;
                    }
                    new GUICommand(player);
                    return true;
                }

                case 1: {
                    if(args[0].equalsIgnoreCase("help")) {
                        helpPopup(player);
                    }
                    return true;
                }

                case 3: {
                    if(args[0].equalsIgnoreCase("give")) {
                        new GiveCommand(Main.ins, player, args, true);
                    }
                    if(args[0].equalsIgnoreCase("revoke")) {
                        new RevokeCommand(Main.ins, player, args, true);
                    }

                    return true;
                }

                default:
                    break;
            }


        }
        return true;
    }



    private void helpPopup(Player player) {
        player.sendMessage("help command");
    }

    private void lackPermisions(Player player) {
        player.sendMessage("you dont have permissions to do this");
    }






}
