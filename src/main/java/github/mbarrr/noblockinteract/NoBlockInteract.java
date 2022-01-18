package github.mbarrr.noblockinteract;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public final class NoBlockInteract extends JavaPlugin implements Listener {


    private String prefix;
    private String noPermissionMessage;
    private List<String> events;
    private Dictionary dict = new Hashtable<String, List<String>>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        getServer().getPluginManager().registerEvents(this, this);

        //Check config for prefix and message otherwise replace with default values
        checkConfig("prefix", "[CHANGEME]");
        checkConfig("noPermissionMessage", "You do not have permission to do this.");
        if(!getConfig().contains("eventList")) getConfig().set("eventList", new ArrayList<String>());

        saveConfig();

        loadPermissions();

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

    @EventHandler
    void blockBreakEvent(BlockBreakEvent e){
        e.setCancelled(checkEventPermissions(e.getEventName(), e.getPlayer()));
    }

    @EventHandler
    void blockPlaceEvent(BlockPlaceEvent e){
        e.setCancelled(checkEventPermissions(e.getEventName(), e.getPlayer()));
    }

    //Returns false if player has permission, otherwise returns true if player does not have permission.
    private boolean checkEventPermissions(String eventName, Player player){
        if(!events.contains(eventName)) return false;

        List<String> perms = (List<String>) dict.get(eventName);

        for (String perm : perms) {
            if (player.hasPermission(perm)) return false;
        }
        sendPlayerMessage(player, noPermissionMessage);
        return true;
    }

    public void sendPlayerMessage(Player player, String message){
        player.sendMessage(ChatColor.DARK_RED+prefix+" "+ ChatColor.RED+message);
    }


    private void checkConfig(String path, String defaultValue){
        if(!getConfig().contains(path)) getConfig().set(path, defaultValue);
    }

    public void loadPermissions(){
        events = getConfig().getStringList("eventList");

        for (String event : events) {
            dict.put(event, getConfig().get("events." + event));
        }
    }

    public static NoBlockInteract getInstance(){
        return (NoBlockInteract) Bukkit.getPluginManager().getPlugin("NoBlockInteract");
    }

    public void addEvent(String event){
        events.add(event);
        dict.put(event, new ArrayList<String>());
        writeEventsToConfig();
    }

    public void removeEvent(String event){
        events.remove(event);
        dict.remove(event);
        getConfig().set("events."+event, null);
        writeEventsToConfig();
    }

    public void addPermissionToEvent(String eventName, String permissionName){
        ((List<String>) dict.get(eventName)).add(permissionName);
    }

    public void removePermissionFromEvent(String eventName, String permissionName){
        ((List<String>) dict.get(eventName)).remove(permissionName);
    }

    public boolean eventContainsPermission(String eventName, String permissionName){
        if(dict.isEmpty()) return false;
        return ((List<String>) dict.get(eventName)).contains(permissionName);
    }

    public boolean eventExists(String eventName){
        return events.contains(eventName);
    }

    public void writeEventsToConfig(){
        getConfig().set("eventList", events);

        for (String event : events) {
            getConfig().set("events." + event, dict.get(event));
        }
        saveConfig();
    }

    public void registerEvent(Listener listener){
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
