package github.mbarrr.noblockinteract.Commands;

import github.mbarrr.noblockinteract.NoBlockInteract;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ChangeNoPermissionMessageCommand implements CommandExecutor {

    FileConfiguration conf = NoBlockInteract.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        if(!sender.isOp()){
            NoBlockInteract.getInstance().sendNoPermissionMessage(player);
            return true;
        }

        if(args.length == 0) return false;



        if(args[0].equalsIgnoreCase("help")) return false;


        String constructedMessage = args[0];

        for(int i = 1; i < args.length; i++){
            String arg = args[i];
            constructedMessage = constructedMessage.concat(" "+arg);
        }

        NoBlockInteract.getInstance().changeNoPermsMessage(constructedMessage);

        NoBlockInteract.getInstance().sendPlayerMessage(player, "Command successful");
        return true;
    }
}
