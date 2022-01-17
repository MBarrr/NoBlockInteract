package github.mbarrr.noblockinteract;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class NoBlockInteract extends JavaPlugin implements Listener {

    private List<String> blockBreakPermissions;
    private List<String> blockPlacePermissions;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("ChangeEventPermission").setExecutor(new ChangePermissionCommand());
        reloadPermissions();

        //config stuff
        checkEvent("blockBreak");
        checkEvent("blockPlace");
        saveConfig();
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

    private boolean checkEventPermissions(List<String> perms, Player player){
        for (String perm : perms) {
            if (player.hasPermission(perm)) return true;
        }
        return false;
    }

    private void checkEvent(String event){
        if(!getConfig().contains("events."+event)) getConfig().set("events."+event, new ArrayList<String>());
    }

    public void reloadPermissions(){
        blockBreakPermissions = getConfig().getStringList("events.blockBreak");
        blockPlacePermissions = getConfig().getStringList("events.blockPlace");
    }

    public static NoBlockInteract getInstance(){
        return (NoBlockInteract) Bukkit.getPluginManager().getPlugin("NoBlockInteract");
    }

}
