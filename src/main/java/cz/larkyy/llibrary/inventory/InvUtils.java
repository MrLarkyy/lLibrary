package cz.larkyy.llibrary.inventory;

import cz.larkyy.llibrary.inventory.events.InvListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class InvUtils {

    private static final Map<Player,InventoryBuilder> INVENTORIES = new HashMap<>();
    private static InvListener listener;

    public static Map<Player, InventoryBuilder> getINVENTORIES() {
        return INVENTORIES;
    }

    public static void addInventory(JavaPlugin main, InventoryBuilder inv) {
        INVENTORIES.put(inv.getPlayer(),inv);
        if (listener == null) {
            listener = new InvListener(main);
            main.getServer().getPluginManager().registerEvents(listener,main);
        }
    }

    public static InventoryBuilder getInventory(Player p) {
        return INVENTORIES.get(p);
    }
}
