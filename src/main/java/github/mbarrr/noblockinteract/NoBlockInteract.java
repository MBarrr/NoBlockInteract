package github.mbarrr.noblockinteract;

import github.mbarrr.noblockinteract.Commands.AddEventCommand;
import github.mbarrr.noblockinteract.Commands.ChangePermissionCommand;
import github.mbarrr.noblockinteract.Commands.RemoveEventCommand;
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

        //Check config for prefix and message otherwise replace with default values
        checkConfig("prefix", "[CHANGEME]");
        checkConfig("noPermissionMessage", "You do not have permission to do this.");

        if(!getConfig().contains("eventList")) {
            List<String> ev = new ArrayList<>();
            ev.add("BlockBreakEvent");
            ev.add("BlockPlaceEvent");
            getConfig().set("eventList", ev);
        }

        if(!getConfig().contains("events.BlockBreakEvent.allowEventByDefault")){
            getConfig().set("events.BlockBreakEvent.allowEventByDefault", true);
        }

        if(!getConfig().contains("events.BlockPlaceEvent.allowEventByDefault")){
            getConfig().set("events.BlockPlaceEvent.allowEventByDefault", true);
        }

        saveConfig();

        loadEvents();

        this.getCommand("AddEvent").setExecutor(new AddEventCommand());
        this.getCommand("ChangeEventPermission").setExecutor(new ChangePermissionCommand());
        this.getCommand("RemoveEvent").setExecutor(new RemoveEventCommand());
        prefix = getConfig().getString("prefix");
        noPermissionMessage = getConfig().getString("noPermissionMessage");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadEvents(){
        List<String> eventList = getConfig().getStringList("eventList");
        for(String event: eventList){
            CustomListener customListener = new CustomListener(event);
            listeners.add(customListener);
        }
    }

    private void checkConfig(String path, String defaultValue){
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
        player.sendMessage(ChatColor.DARK_RED+prefix+" "+ ChatColor.RED+message);
    }

    public void sendNoPermissionMessage(Player player){
        sendPlayerMessage(player, noPermissionMessage);
    }

    public static NoBlockInteract getInstance(){
        return (NoBlockInteract) Bukkit.getPluginManager().getPlugin("NoBlockInteract");
    }


}
