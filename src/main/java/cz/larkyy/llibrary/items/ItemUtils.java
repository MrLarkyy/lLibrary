package cz.larkyy.llibrary.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import cz.larkyy.llibrary.chat.ChatUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static ItemStack makeItemStack(Material material, String displayName) {
        return makeItemStack(material,displayName, new ArrayList<>());
    }

    public static ItemStack addItemLore(ItemStack is, List<String> lore) {
        ItemMeta im = is.getItemMeta();
        List<String> newLore = new ArrayList<>();
        lore.forEach(str -> lore.add(ChatUtils.format(str)));
        im.setLore(newLore);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack setItemModelID(ItemStack is, int modelId) {
        ItemMeta im = is.getItemMeta();
        im.setCustomModelData(modelId);
        is.setItemMeta(im);
        return is;
    }

    public static ItemStack addItemData(JavaPlugin main, ItemStack is, PersistentDataType type, String key, Object value) {
        return addItemData(is,type,new NamespacedKey(main,key),value);
    }

    public static ItemStack addItemData(ItemStack is, PersistentDataType type, NamespacedKey key, Object value) {
        ItemMeta im = is.getItemMeta();
        if (im==null) {
            return is;
        }

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

    public static ItemStack setSkullItemSkin(ItemStack is, String texture) {

        ItemMeta meta = is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field field;
        try {
            field = meta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException x) {
            x.printStackTrace();
        }
        is.setItemMeta(meta);
        return is;
    }

    public static boolean hasItemData(ItemStack is, PersistentDataType type, NamespacedKey key) {
        if (is==null) {
            return false;
        }

        if (is.getItemMeta()==null) {
            return false;
        }
        return is.getItemMeta().getPersistentDataContainer().has(key,type);
    }

    public static ItemStack getItemStackFromConfig(FileConfiguration config, String path) {
        Material mat = Material.valueOf(config.getString(path+".material"));
        String displayName = config.getString(path+".displayName");
        List<String> lore = config.getStringList(path+".lore");

        String texture = null;
        if (config.contains(path+".texture")) {
            texture = config.getString(path+".texture");
        }

        ItemStack is = ItemUtils.makeItemStack(
                mat,
                displayName,
                lore,
                null
        );

        if (texture!=null) {
            setSkullItemSkin(is,texture);
        }

        return is;
    }
}
