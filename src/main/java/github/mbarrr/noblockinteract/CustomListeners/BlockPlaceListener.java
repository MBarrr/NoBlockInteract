package github.mbarrr.noblockinteract.CustomListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener extends CustomListener{
    public BlockPlaceListener(String eventName) {
        super(eventName);
    }

    @EventHandler
    void blockPlaceEvent(BlockPlaceEvent e){
        calculatePermission(e.getPlayer());
    }
}
