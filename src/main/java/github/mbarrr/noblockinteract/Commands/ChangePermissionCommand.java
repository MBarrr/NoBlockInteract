package github.mbarrr.noblockinteract.Commands;

import github.mbarrr.noblockinteract.NoBlockInteract;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangePermissionCommand implements CommandExecutor {

    //changePermission event permissiongroup boolean

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

        if(args.length != 3) {
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Please enter 3 arguments");
            return true;
        }

        if(!NoBlockInteract.getInstance().eventExists(args[0])) {
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Event not found: "+args[0]);
            return true;
        }

        //Setting values
        String event = args[0];
        String permission = args[1];
        boolean value = Boolean.parseBoolean(args[2]);
        boolean containsPerm = NoBlockInteract.getInstance().eventContainsPermission(event, permission);

        //Permission is to be added
        if(value){
            //Permission already exists within event
            if(containsPerm){
                NoBlockInteract.getInstance().sendPlayerMessage(player, "This event already has this permission.");
                return true;
            }
            //Permission was not found and was added to event
            NoBlockInteract.getInstance().addPermissionToEvent(event, permission);
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Permission successfully added.");
        }

        //Permission is to be removed
        else{
            //Permission could not be found
            if(!containsPerm) {
                NoBlockInteract.getInstance().sendPlayerMessage(player,"Permission was not found.");
                return true;
            }

            //Permission was found and removed
            NoBlockInteract.getInstance().removePermissionFromEvent(event, permission);
            NoBlockInteract.getInstance().sendPlayerMessage(player,"Permission successfully removed.");
        }

        return true;
    }
}
