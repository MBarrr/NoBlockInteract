package github.mbarrr.noblockinteract;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NoBlockInteract extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    void blockBreakEvent(BlockBreakEvent e){
        e.setCancelled(!e.getPlayer().hasPermission("minecraft.admin"));
    }

    @EventHandler
    void blockPlaceEvent(BlockPlaceEvent e){
        e.setCancelled(!e.getPlayer().hasPermission("minecraft.admin"));
    }

    public static NoBlockInteract getInstance(){
        return (NoBlockInteract) Bukkit.getPluginManager().getPlugin("NoBlockInteract");
    }
}
