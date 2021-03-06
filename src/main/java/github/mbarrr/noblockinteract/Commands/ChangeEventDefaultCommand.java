package github.mbarrr.noblockinteract.Commands;

import github.mbarrr.noblockinteract.CustomListeners.CustomListener;
import github.mbarrr.noblockinteract.NoBlockInteract;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeEventDefaultCommand implements CommandExecutor {


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

        if(args.length != 2) {
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Please enter 2 arguments");
            return true;
        }

        if(!NoBlockInteract.getInstance().eventExists(args[0])) {
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Event not found: "+args[0]);
            return true;
        }

        NoBlockInteract.getInstance().changeEventDefault(args[0], Boolean.parseBoolean(args[1]));
        NoBlockInteract.getInstance().sendPlayerMessage(player, "Event successfully updated");

        return true;
    }
}
