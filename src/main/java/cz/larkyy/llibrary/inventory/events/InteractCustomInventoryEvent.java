package cz.larkyy.llibrary.inventory.events;

import cz.larkyy.llibrary.inventory.InventoryBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InteractCustomInventoryEvent extends Event implements Cancellable {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;
    private final InventoryBuilder inventory;
    private final int rawSlot;
    private final int slot;
    private final ItemStack is;
    private boolean isCancelled;

    public InteractCustomInventoryEvent(Player p, InventoryBuilder inventory, int rawSlot, int slot, ItemStack is) {
        this.inventory = inventory;
        this.player = p;
        this.rawSlot = rawSlot;
        this.slot = slot;
        this.is = is;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public InventoryBuilder getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRawSlot() {
        return rawSlot;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return is;
    }
}
