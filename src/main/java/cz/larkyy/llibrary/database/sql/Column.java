package cz.larkyy.llibrary.database.sql;

public class Column {

    private final String name;
    private final String type;
    private final boolean canBeNull;

    public Column(String name, String type, boolean canBeNull) {
        this.canBeNull = canBeNull;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isCanBeNull() {
        return canBeNull;
    }
}
