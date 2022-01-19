package github.mbarrr.noblockinteract;

import github.mbarrr.noblockinteract.Commands.*;
import github.mbarrr.noblockinteract.CustomListeners.BlockBreakListener;
import github.mbarrr.noblockinteract.CustomListeners.BlockPlaceListener;
import github.mbarrr.noblockinteract.CustomListeners.CustomListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class NoBlockInteract extends JavaPlugin implements Listener {

    private String prefix;
    private String noPermissionMessage;
    private List<CustomListener> listeners = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(this, this);

        //Check config for stuff otherwise replace with default values
        checkConfig("prefix", "[World]");
        checkConfig("noPermissionMessage", "You do not have permission to do this.");
        checkConfig("events.BlockBreakEvent.allowEventByDefault", true);
        checkConfig("events.BlockPlace.allowEventByDefault", true);

        if(!getConfig().contains("eventList")) {
            List<String> ev = new ArrayList<>();
            ev.add("BlockBreakEvent");
            ev.add("BlockPlaceEvent");
            getConfig().set("eventList", ev);
        }



        saveConfig();
        loadEvents();

        this.getCommand("ChangePrefix").setExecutor(new ChangePrefixCommand());
        this.getCommand("ChangeNoPermMessage").setExecutor(new ChangeNoPermissionMessageCommand());
        this.getCommand("ChangeEventDefault").setExecutor(new ChangeEventDefaultCommand());
        this.getCommand("ChangeEventPermission").setExecutor(new ChangePermissionCommand());
        this.getCommand("ViewEventPermissions").setExecutor(new ViewPermissionsCommand());
        prefix = getConfig().getString("prefix");
        noPermissionMessage = getConfig().getString("noPermissionMessage");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadEvents(){
        BlockPlaceListener blockPlaceListener = new BlockPlaceListener("BlockPlaceEvent");
        BlockBreakListener blockBreakListener = new BlockBreakListener("BlockBreakEvent");
        listeners.add(blockBreakListener);
        listeners.add(blockPlaceListener);
    }

    private void checkConfig(String path, Object defaultValue){
        if(!getConfig().contains(path)) getConfig().set(path, defaultValue);
    }

    public void addPermissionToEvent(String eventName, String permissionName){
        getListener(eventName).addPermission(permissionName);
    }

    public void removePermissionFromEvent(String eventName, String permissionName){
        getListener(eventName).removePermission(permissionName);
    }

    public boolean eventContainsPermission(String eventName, String permissionName){
        return getListener(eventName).eventContainsPermission(permissionName);
    }

    public boolean eventExists(String eventName){
        for(CustomListener listener: listeners){
            if(listener.getEventName().equals(eventName)) return true;
        }
        return false;
    }

    public void changeEventDefault(String eventName, boolean val){
        getListener(eventName).changeEventDefault(val);
    }

    public void changePrefix(String prefix){
        this.prefix = prefix;
        getConfig().set("prefix", prefix);
        saveConfig();
    }


    public void changeNoPermsMessage(String noPermissionMessage){
        this.noPermissionMessage = noPermissionMessage;
        getConfig().set("noPermissionMessage", noPermissionMessage);
        NoBlockInteract.getInstance().saveConfig();
    }


    public List<String> getEventPermissions(String eventName){
        CustomListener listener = getListener(eventName);
        return listener.getPermissions();
    }

    public CustomListener getListener(String eventName){
        for(CustomListener listener: listeners){
            if(listener.getEventName().equals(eventName)) return listener;
        }
        return null;
    }

    public void registerEvent(Listener listener){
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void sendPlayerMessage(Player player, String message){
        player.sendMessage(ChatColor.DARK_RED+prefix+" "+ ChatColor.YELLOW+message);
    }

    public boolean getDefault(String eventName){
        CustomListener listener = getListener(eventName);
        return listener.getDefault();
    }

    public void sendNoPermissionMessage(Player player){
        sendPlayerMessage(player, ChatColor.RED+noPermissionMessage);
    }

    public static NoBlockInteract getInstance(){
        return (NoBlockInteract) Bukkit.getPluginManager().getPlugin("NoBlockInteract");
    }



}
