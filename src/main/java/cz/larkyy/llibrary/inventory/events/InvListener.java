package cz.larkyy.llibrary.inventory.events;

import cz.larkyy.llibrary.inventory.InvUtils;
import cz.larkyy.llibrary.inventory.InventoryBuilder;
import cz.larkyy.llibrary.items.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class InvListener implements Listener {

    private final JavaPlugin main;
    public InvListener(JavaPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getWhoClicked();

        if (!(e.getInventory().getHolder() instanceof InventoryBuilder)) {
            return;
        }

        InteractCustomInventoryEvent event = new InteractCustomInventoryEvent(p,InvUtils.getInventory(p),e.getRawSlot(),e.getSlot(),e.getCurrentItem());
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            e.setCancelled(true);
        }

        if (ItemUtils.hasItemData(main,e.getCurrentItem(), PersistentDataType.STRING,"InvUtils-Function")) {
            InteractFunctionItemEvent event1 = new InteractFunctionItemEvent(InvUtils.getInventory(p),p,e.getCurrentItem(),(String)ItemUtils.getItemData(main,e.getCurrentItem(), PersistentDataType.STRING,"InvUtils-Function"));
            Bukkit.getPluginManager().callEvent(event1);

            if (event.isCancelled()) {
                return;
            }
        }
    }
}
