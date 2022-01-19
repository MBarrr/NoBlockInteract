package github.mbarrr.noblockinteract.Commands;

import github.mbarrr.noblockinteract.NoBlockInteract;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ViewPermissionsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(!sender.isOp()){
            NoBlockInteract.getInstance().sendNoPermissionMessage(player);
            return true;
        }

        if(args.length == 0) return false;

        if(args[0].equalsIgnoreCase("help")){
            return false;
        }

        if(args.length != 1) {
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Please enter 1 argument");
            return true;
        }

        if(!NoBlockInteract.getInstance().eventExists(args[0])) {
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Event not found: "+args[0]);
            return true;
        }

        List<String> permissions = NoBlockInteract.getInstance().getEventPermissions(args[0]);
        boolean eventDefault = NoBlockInteract.getInstance().getDefault(args[0]);

        NoBlockInteract.getInstance().sendPlayerMessage(player,"Event default for "+ args[0]+" is set to: " + eventDefault);
        NoBlockInteract.getInstance().sendPlayerMessage(player,"and is accessible by the following groups:");

        for(String perm: permissions){
            NoBlockInteract.getInstance().sendPlayerMessage(player, perm);
        }

        return true;
    }
}
