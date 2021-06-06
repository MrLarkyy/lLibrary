package cz.larkyy.llibrary.commands.arguments;

public class Argument {
    private String permission;
    private ArgumentType type;
    private Boolean canConsoleUse;
    private String argument;

    public Argument(String argument,ArgumentType type, String permission, Boolean canConsoleUse) {
        this.type = type;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
    }

    public Argument(String argument,ArgumentType type, String permission) {
        this.type = type;
        this.permission = permission;
        this.canConsoleUse = true;
    }

    public Argument(String argument,ArgumentType type, Boolean canConsoleUse) {
        this.type = type;
        this.permission = null;
        this.canConsoleUse = canConsoleUse;
    }

    public Argument(String argument,ArgumentType type) {
        this.type = type;
        this.permission = null;
        this.canConsoleUse = true;
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

    @Override
    public String toString() {
        return argument;
    }
}
