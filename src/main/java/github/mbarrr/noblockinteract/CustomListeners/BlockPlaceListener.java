package github.mbarrr.noblockinteract.CustomListeners;

import github.mbarrr.noblockinteract.NoBlockInteract;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener extends CustomListener{

    public BlockPlaceListener(String eventName) {
        super(eventName);
        NoBlockInteract.getInstance().registerEvent(this);
    }

    @EventHandler
    void blockPlaceEvent(BlockPlaceEvent e){
        e.setCancelled(calculatePermission(e.getPlayer()));
    }
}
