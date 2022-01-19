package github.mbarrr.noblockinteract.Commands;

import github.mbarrr.noblockinteract.NoBlockInteract;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveEventCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if(!sender.isOp()){
            NoBlockInteract.getInstance().sendPlayerMessage(player,"You do not have permission to do this.");
            return true;
        }

        if(args[0].equalsIgnoreCase("help")){
            return false;
        }

        if(args.length != 1) {
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Please enter 1 argument");
            return true;
        }

        String event = args[0];

        if(!NoBlockInteract.getInstance().eventExists(event)){
            NoBlockInteract.getInstance().sendPlayerMessage(player,"This event does not exist.");
            return true;
        }

        //REMOVE EVENT HERE
        ////
        ////
        ///
        NoBlockInteract.getInstance().sendPlayerMessage(player,"Event has been removed.");

        return true;
    }
}
