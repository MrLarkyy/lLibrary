package cz.larkyy.llibrary.commands;

import cz.larkyy.llibrary.commands.events.CustomCommandEvent;
import cz.larkyy.llibrary.commands.events.PreCustomCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class Command extends BukkitCommand {
    private final String cmdStr;

    protected Command(String name) {
        super(name);
        cmdStr = name;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        CommandBuilder cmd = CommandUtils.getCommand(cmdStr);

        PreCustomCommandEvent event1 = new PreCustomCommandEvent(cmd,sender,commandLabel,args);
        Bukkit.getPluginManager().callEvent(event1);

        if (event1.isCancelled()) {
            return false;
        }

        if (cmd==null) {
            return false;
        }

        if (!CommandUtils.isCommandAlright(cmd,sender,args)) {
            return false;
        }

        CustomCommandEvent event2 = new CustomCommandEvent(cmd,sender,commandLabel,args);
        Bukkit.getPluginManager().callEvent(event2);

        return false;
    }
}
