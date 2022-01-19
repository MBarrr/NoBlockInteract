package github.mbarrr.noblockinteract.CustomListeners;

import github.mbarrr.noblockinteract.NoBlockInteract;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener extends CustomListener{

    public BlockBreakListener(String eventName) {
        super(eventName);
        NoBlockInteract.getInstance().registerEvent(this);
    }

    @EventHandler
    void blockBreakEvent(BlockBreakEvent e){
        e.setCancelled(calculatePermission(e.getPlayer()));
    }

}
