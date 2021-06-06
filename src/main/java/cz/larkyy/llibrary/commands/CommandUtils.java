package cz.larkyy.llibrary.commands;

import cz.larkyy.llibrary.commands.arguments.Argument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandUtils {
    private static Map<String,CommandBuilder> commands = new HashMap<>();

    public static CommandBuilder getCommand(String cmd) {
        return commands.get(cmd);
    }

    public static void addCommand(CommandBuilder cmd) {
        commands.put(cmd.getCommand(),cmd);
    }

    public static boolean isCommandAlright(CommandBuilder cmd, CommandSender sender, String[] args) {
        if ((!cmd.canSendConsole()) && (!(sender instanceof Player))) {
            sender.sendMessage(cmd.getOnlyPlayerError());
            return false;
        }

        if (args.length < cmd.getRequiredArguments()) {
            sender.sendMessage(cmd.getNotEnoughArgsError());
            return false;
        }

        if (cmd.getPermission()!=null) {
            if ((sender instanceof Player) && !sender.hasPermission(cmd.getPermission())) {
                sender.sendMessage(cmd.getNoPermissionError());
                return false;
            }
        }

        if (args.length > 0 && !cmd.getArguments().isEmpty()) {
            for (Argument arg : cmd.getArguments()) {
                if (args[0].equalsIgnoreCase(arg.toString())) {
                    if ((!arg.canConsoleUse()) && (!(sender instanceof Player))) {
                        sender.sendMessage(cmd.getOnlyPlayerError());
                        return false;
                    }


                    if ((sender instanceof Player) && arg.getPermission()!=null) {
                        if (sender.hasPermission(arg.getPermission())) {
                            sender.sendMessage(cmd.getNoPermissionError());
                            return false;
                        }
                    }

                    switch (arg.getType()) {
                        case PLAYER:
                            if (Bukkit.getPlayer(args[0])==null) {
                                sender.sendMessage(cmd.getUnknownPlayerError());
                                return false;
                            }
                            break;
                        case INTEGER:
                            try {
                                Integer.parseInt(args[0]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(cmd.getUnknownIntError());
                            }
                            break;
                    }

                    return false;
                }
            }
        }

        return true;
    }
}
