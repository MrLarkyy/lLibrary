package cz.larkyy.llibrary.inventory.events;

import cz.larkyy.llibrary.inventory.InventoryBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InteractFunctionItemEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled;
    private final Player opener;
    private final InventoryBuilder inventory;
    private final ItemStack is;
    private final String function;

    public InteractFunctionItemEvent(InventoryBuilder inventory, Player opener, ItemStack is, String function) {
        this.opener = opener;
        this.inventory = inventory;
        this.is = is;
        this.function = function;
        this.isCancelled = false;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public Player getOpener() {
        return opener;
    }

    public InventoryBuilder getInventory() {
        return inventory;
    }

    public ItemStack getItemStack() {
        return is;
    }

    public String getFunction() {
        return function;
    }
}
