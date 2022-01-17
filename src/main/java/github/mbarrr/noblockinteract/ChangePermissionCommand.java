package github.mbarrr.noblockinteract;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ChangePermissionCommand implements CommandExecutor {

    //changePermission event permissiongroup boolean

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;
        if(!sender.isOp()){
            sender.sendMessage("You do not have permission to do this.");
            return false;
        }
        if(args.length != 3) {
            sender.sendMessage("Please enter 3 arguments");
            return false;
        }
        if(!NoBlockInteract.getInstance().getConfig().contains("events."+args[0])) {
            sender.sendMessage("Event not found: "+args[0]);
            return false;
        }

        //Setting values
        List<String> perms = NoBlockInteract.getInstance().getConfig().getStringList("events."+args[0]);
        String event = args[0];
        String permission = args[1];
        boolean value = Boolean.parseBoolean(args[2]);
        boolean containsPerm = perms.contains(permission);

        //Permission is to be added
        if(value && !containsPerm){
            perms.add(permission);
            sender.sendMessage("Permission successfully added.");
        }

        //Permission is to be removed
        else{
            if(!containsPerm) {
                sender.sendMessage("Permission was not found.");
                return false;
            }

            else{
                perms.remove(permission);
                sender.sendMessage("Permission successfully removed.");
            }
        }

        NoBlockInteract.getInstance().getConfig().set("events."+event, perms);
        NoBlockInteract.getInstance().saveConfig();
        return true;
    }
}
