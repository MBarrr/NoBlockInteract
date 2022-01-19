package github.mbarrr.noblockinteract.CustomListeners;

import github.mbarrr.noblockinteract.NoBlockInteract;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class CustomListener implements Listener{
    
    private final FileConfiguration conf;
    private final String eventName;
    private List<String> permissions;
    private boolean allowEventByDefault;

    public CustomListener(String eventName){
        this.eventName = eventName;
        this.conf = NoBlockInteract.getInstance().getConfig();
        loadPermissions();
    }

    private void loadPermissions(){
        permissions = conf.getStringList("events."+eventName+".permissions");
        allowEventByDefault = conf.getBoolean("events."+eventName+".allowEventByDefault");
    }

    protected boolean calculatePermission(Player player){
        if(allowEventByDefault) return false;
        
        for (String perm : permissions) {
            if (player.hasPermission(perm)) return false;
        }
        NoBlockInteract.getInstance().sendNoPermissionMessage(player);
        return true;
    }

    public void writePermissionsToConfig(){
        conf.set("events." + eventName+".permissions", permissions);

        NoBlockInteract.getInstance().saveConfig();
    }
    
    public String getEventName(){
        return eventName;
    }
    
    public boolean eventContainsPermission(String permission){
        return permissions.contains(permission);
    }
    
    public void removePermission(String permission){
        permissions.remove(permission);
        writePermissionsToConfig();
    }
    
    public void addPermission(String permission){
        permissions.add(permission);
        writePermissionsToConfig();
    }

    public List<String> getPermissions(){
        return permissions;
    }

    public boolean getDefault(){
        return allowEventByDefault;
    }

    public void changeEventDefault(boolean val){
        allowEventByDefault = val;
        conf.set("events."+eventName+".allowEventByDefault", val);
        NoBlockInteract.getInstance().saveConfig();
    }



}
