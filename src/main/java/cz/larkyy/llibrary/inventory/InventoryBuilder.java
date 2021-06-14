package cz.larkyy.llibrary.inventory;

import cz.larkyy.llibrary.chat.ChatUtils;
import cz.larkyy.llibrary.items.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryBuilder implements InventoryHolder, Listener {

    private Inventory inv;
    private final Player p;
    private List<String> lines;
    private Map<Character, ItemStack> items;
    private Map<Character, String> functions;
    private String title;
    private final JavaPlugin main;
    private Map<String,Object> data;

    public InventoryBuilder(JavaPlugin main, Player p){
        this.p = p;
        this.lines = new ArrayList<>();
        this.items = new HashMap<>();
        this.functions = new HashMap<>();
        this.title = "Inventory";
        this.main = main;
        this.data = new HashMap<>();
    }

    @Override
    public @NotNull Inventory getInventory() {
        this.inv = Bukkit.createInventory(this,lines.size()*9,title);

        setItemsToInv();

        InvUtils.addInventory(main,this);
        return inv;
    }

    private void setItemsToInv() {
        for (int i = 0; i < lines.size();i++) {
            List<ItemStack> itemStacks = translateLine(lines.get(i));
            for (int ii = 0; ii < 9; ii++) {
                inv.setItem(i*9+ii,itemStacks.get(ii));
            }
        }
    }

    private List<ItemStack> translateLine(String line) {
        String[] chars = line.split("");
        List<ItemStack> itemStacks = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            try {
                char key = chars[i].charAt(0);
                ItemStack is;
                if (items.containsKey(key)) {
                    is = items.get(key);
                } else {
                    is =new ItemStack(Material.AIR);
                }
                itemStacks.add(is);
                if (functions.containsKey(key)) {
                    ItemUtils.addItemData(main,is, PersistentDataType.STRING,"InvUtils-Function",functions.get(key));
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                itemStacks.add(new ItemStack(Material.AIR));
                continue;
            }
        }
        return itemStacks;
    }

    //  LINES

    public InventoryBuilder addLine(String line) {
        lines.add(line);
        return this;
    }

    public InventoryBuilder removeLine(String line) {
        lines.remove(line);
        return this;
    }

    public InventoryBuilder removeLine(int i) {
        lines.remove(i);
        return this;
    }

    public InventoryBuilder setLines(List<String> lines) {
        this.lines = lines;
        return this;
    }

    //  ITEMS

    public InventoryBuilder addItem(char character, ItemStack is) {
        items.put(character,is);
        return this;
    }

    public InventoryBuilder removeItem(char character) {
        items.remove(character);
        return this;
    }

    // FUNCTIONS

    public InventoryBuilder addFunction(char key, String function) {
        functions.put(key,function);
        return this;
    }

    public InventoryBuilder removeFunction(char key) {
        functions.remove(key);
        return this;
    }

    public boolean hasItemFunction(ItemStack is) {
        return ItemUtils.hasItemData(main,is,PersistentDataType.STRING,"InvUtils-Key");
    }

    public String getItemFunction(ItemStack is) {
        return (String) ItemUtils.getItemData(main,is,PersistentDataType.STRING,"InvUtils-Key");
    }

    //  TITLE

    public InventoryBuilder setTitle(String title) {
        this.title = ChatUtils.format(title);
        return this;
    }

    // DATA

    public InventoryBuilder addData(String key, Object data) {
        this.data.put(key,data);
        return this;
    }

    public InventoryBuilder removeData(String key) {
        this.data.remove(key);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    // OTHERS

    public InventoryBuilder clearAll() {
        this.title = "Inventory";
        this.functions = new HashMap<>();
        this.items = new HashMap<>();
        this.lines = new ArrayList<>();
        this.data = new HashMap<>();
        return this;
    }

    public Player getPlayer() {
        return p;
    }

    public Inventory getInv() {
        return inv;
    }
}
