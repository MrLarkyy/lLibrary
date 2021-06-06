package cz.larkyy.llibrary.commands;

import cz.larkyy.llibrary.chat.ChatUtils;
import cz.larkyy.llibrary.commands.arguments.Argument;
import cz.larkyy.llibrary.commands.arguments.ArgumentType;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
    private final String command;
    private int requiredArguments;
    private boolean canSendConsole;
    private List<Argument> arguments;
    private String permission;
    private List<String> aliases;
    private String description;

    // Error msgs

    private String onlyPlayerError;
    private String notEnoughArgsError;
    private String noPermissionError;

    private String unknownPlayerError;
    private String unknownIntError;

    public CommandBuilder(String name) {
        arguments = new ArrayList<>();
        aliases = new ArrayList<>();
        this.command = name;
        this.requiredArguments = 0;
        this.canSendConsole = true;

        setOnlyPlayerError("&cOnly player can use this command!");
        setNotEnoughArgsError("&cNot enough arguments!");
        setNoPermissionError("&cYou have no permission to do that!");

        setUnknownPlayerError("&cUnknown player!");
        setUnknownIntError("&cUnknown number!");
    }

    public String getCommand() {
        return command;
    }

    //
    //  ERROR MESSAGES
    //

    public CommandBuilder setOnlyPlayerError(String str) {
        this.onlyPlayerError = ChatUtils.format(str);
        return this;
    }

    public CommandBuilder setNotEnoughArgsError(String str) {
        this.notEnoughArgsError = ChatUtils.format(str);
        return this;
    }

    public CommandBuilder setNoPermissionError(String str) {
        this.noPermissionError = ChatUtils.format(str);
        return this;
    }

    public CommandBuilder setUnknownPlayerError(String str) {
        this.unknownPlayerError = ChatUtils.format(str);
        return this;
    }

    public CommandBuilder setUnknownIntError(String str) {
        this.unknownIntError = ChatUtils.format(str);
        return this;
    }

    public String getNoPermissionError() {
        return noPermissionError;
    }

    public String getNotEnoughArgsError() {
        return notEnoughArgsError;
    }

    public String getOnlyPlayerError() {
        return onlyPlayerError;
    }

    public String getUnknownIntError() {
        return unknownIntError;
    }

    public String getUnknownPlayerError() {
        return unknownPlayerError;
    }

    //
    //  ALIASES
    //

    public CommandBuilder addAlias(String alias) {
        this.aliases.add(alias);
        return this;
    }

    public CommandBuilder setAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public CommandBuilder removeAlias(String alias) {
        this.aliases.remove(alias);
        return this;
    }

    public List<String> getAliases() {
        return aliases;
    }

    //
    //  DESCRIPTION
    //

    public CommandBuilder setDescription(String str) {
        this.description = str;
        return this;
    }

    public String getDescription() {
        return description;
    }

    //
    //  CONSOLE STUFF
    //

    public CommandBuilder setCanSendConsole(boolean canSendConsole) {
        this.canSendConsole = canSendConsole;
        return this;
    }

    public boolean canSendConsole() {
        return canSendConsole;
    }

    //
    //  ARGUMENTS
    //

    public CommandBuilder setRequiredArguments(int i) {
        this.requiredArguments = i;
        return this;
    }

    public int getRequiredArguments() {
        return requiredArguments;
    }

    public CommandBuilder setArguments(List<Argument> args) {
        this.arguments = args;
        return this;
    }

    public CommandBuilder addArgument(String argument, ArgumentType argType) {
        arguments.add(new Argument(argument, argType));
        return this;
    }

    public CommandBuilder addArgument(String argument, ArgumentType argType, String permission) {
        arguments.add(new Argument(argument, argType, permission));
        return this;
    }

    public CommandBuilder removeArgument(int i) {
        this.arguments.remove(i);
        return this;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    //
    //  PERMISSION
    //

    public CommandBuilder setPermission(String str) {
        this.permission = str;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    //
    //  FINAL
    //

    public CommandBuilder build() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());

            BukkitCommand cmd = new Command(command);
            cmd.setAliases(aliases);
            cmd.setDescription(description);
            cmd.setPermission(permission);

            commandMap.register(command, cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
