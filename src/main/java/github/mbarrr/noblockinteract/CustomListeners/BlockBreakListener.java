package github.mbarrr.noblockinteract.CustomListeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener extends CustomListener{

    public BlockBreakListener(String eventName) {
        super(eventName);
    }

    @EventHandler
    void blockBreakEvent(BlockBreakEvent e){
        calculatePermission(e.getPlayer());
    }

}
