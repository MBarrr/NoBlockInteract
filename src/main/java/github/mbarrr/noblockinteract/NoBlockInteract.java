package github.mbarrr.noblockinteract;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class NoBlockInteract extends JavaPlugin implements Listener {


    private String prefix;
    private String noPermissionMessage;
    private List<String> blockBreakPermissions;
    private List<String> blockPlacePermissions;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadPermissions();
        getServer().getPluginManager().registerEvents(this, this);

        //Check config for prefix and message otherwise replace with default values
        checkConfig("prefix", "[CHANGEME]");
        checkConfig("noPermissionMessage", "You do not have permission to do this.");
        checkEvent("blockBreak");
        checkEvent("blockPlace");
        saveConfig();

        this.getCommand("ChangeEventPermission").setExecutor(new ChangePermissionCommand());
        prefix = getConfig().getString("prefix");
        noPermissionMessage = getConfig().getString("noPermissionMessage");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    void blockBreakEvent(BlockBreakEvent e){
        e.setCancelled(checkEventPermissions(blockBreakPermissions, e.getPlayer()));
    }

    @EventHandler
    void blockPlaceEvent(BlockPlaceEvent e){
        e.setCancelled(checkEventPermissions(blockPlacePermissions, e.getPlayer()));
    }

    //Returns false if player has permission, otherwise returns true if player does not have permission.
    private boolean checkEventPermissions(List<String> perms, Player player){
        for (String perm : perms) {
            if (player.hasPermission(perm)) return false;
        }
        sendPlayerMessage(player, noPermissionMessage);
        return true;
    }

    public void sendPlayerMessage(Player player, String message){
        player.sendMessage(ChatColor.DARK_RED+prefix+" "+ ChatColor.RED+message);
    }

    private void checkEvent(String event){
        if(!getConfig().contains("events."+event)) getConfig().set("events."+event, new ArrayList<String>());
    }

    private void checkConfig(String path, String defaultValue){
        if(!getConfig().contains(path)) getConfig().set(path, defaultValue);
    }

    public void loadPermissions(){
        blockBreakPermissions = getConfig().getStringList("events.blockBreak");
        blockPlacePermissions = getConfig().getStringList("events.blockPlace");
    }

    public static NoBlockInteract getInstance(){
        return (NoBlockInteract) Bukkit.getPluginManager().getPlugin("NoBlockInteract");
    }

}
