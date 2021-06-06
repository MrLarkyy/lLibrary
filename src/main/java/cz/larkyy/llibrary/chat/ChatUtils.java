package cz.larkyy.llibrary.chat;

import cz.larkyy.llibrary.chat.hex.Colors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ChatUtils {
    public static String format(String str) {
        return Colors.process(str);
    }
    public static List<String> format(List<String> list) {
        List<String> newList = new ArrayList<>();
        list.forEach(line -> newList.add(format(line)));
        return newList;
    }

    public static void sendConsoleMsg(JavaPlugin main,String msg) {
        main.getServer().getConsoleSender().sendMessage(format(msg));
    }

    public static void sendPlayerMsg(Player p, String msg) {
        p.sendMessage(format(msg));
    }

    public static void broadcastMsg(String msg) {
        Bukkit.getOnlinePlayers().forEach(p ->
                p.sendMessage(format(msg))
        );
    }

    public static void broadcastMsg(List<String> msgs) {
        Bukkit.getOnlinePlayers().forEach(p ->
                msgs.forEach(msg ->
                        p.sendMessage(format(msg))
                )
        );
    }

}
