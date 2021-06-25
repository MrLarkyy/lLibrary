package cz.larkyy.llibrary.commands.arguments;


import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class Argument {
    private String permission;
    private ArgumentType type;
    private Boolean canConsoleUse;
    private String argument;

    public Argument(String argument,ArgumentType type, String permission, Boolean canConsoleUse) {
        this.type = type;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        this.argument = argument;
    }

    public Argument(String argument,ArgumentType type, String permission) {
        this.type = type;
        this.permission = permission;
        this.canConsoleUse = true;
        this.argument = argument;
    }

    public Argument(String argument,ArgumentType type, Boolean canConsoleUse) {

        this.type = type;
        this.permission = null;
        this.canConsoleUse = canConsoleUse;
        this.argument = argument;
    }

    public Argument(String argument,ArgumentType type) {
        this.type = type;
        this.permission = null;
        this.canConsoleUse = true;
        this.argument = argument;
    }

    public String getPermission() {
        return permission;
    }

    public ArgumentType getType() {
        return type;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setType(ArgumentType type) {
        this.type = type;
    }

    public Boolean canConsoleUse() {
        return canConsoleUse;
    }

    public String getName() {
        return argument;
    }
}
