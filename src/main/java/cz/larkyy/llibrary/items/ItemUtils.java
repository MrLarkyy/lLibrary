package cz.larkyy.llibrary.items;

import cz.larkyy.llibrary.chat.ChatUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ItemUtils {

    public static ItemStack makeItemStack(Material material, String displayName, List<String> lore, String localizedName, int modelData) {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatUtils.format(displayName));
        if (lore!=null) {
            im.setLore(ChatUtils.format(lore));
        }
        im.setLocalizedName(localizedName);
        if (modelData > -1) {
            im.setCustomModelData(modelData);
        }
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack makeItemStack(Material material, String displayName, List<String> lore, String localizedName) {
        return makeItemStack(material,displayName,lore,localizedName,-1);
    }

    public static ItemStack makeItemStack(Material material, String displayName, List<String> lore, int modelData) {
        return makeItemStack(material,displayName,lore,null,modelData);
    }

    public static ItemStack makeItemStack(Material material, String displayName, List<String> lore) {
        return makeItemStack(material,displayName,lore,-1);
    }

    public static ItemStack addItemData(JavaPlugin main, ItemStack is, PersistentDataType type, String key, Object value) {
        return addItemData(is,type,new NamespacedKey(main,key),value);
    }

    public static ItemStack addItemData(ItemStack is, PersistentDataType type, NamespacedKey key, Object value) {
        ItemMeta im = is.getItemMeta();
        PersistentDataContainer data = im.getPersistentDataContainer();
        data.set(key, type, value);
        is.setItemMeta(im);
        return is;
    }

    public static Object getItemData(JavaPlugin main, ItemStack is, PersistentDataType type, String key) {
        return getItemData(is,type,new NamespacedKey(main,key));
    }

    public static Object getItemData(ItemStack is, PersistentDataType type, NamespacedKey key) {
        if (is.getItemMeta()==null) {
            return null;
        }
        return is.getItemMeta().getPersistentDataContainer().get(key,type);
    }

    public static boolean hasItemData(JavaPlugin main, ItemStack is, PersistentDataType type, String key) {
        return hasItemData(is,type,new NamespacedKey(main,key));
    }

    public static boolean hasItemData(ItemStack is, PersistentDataType type, NamespacedKey key) {
        if (is.getItemMeta()==null) {
            return false;
        }
        return is.getItemMeta().getPersistentDataContainer().has(key,type);
    }

}
