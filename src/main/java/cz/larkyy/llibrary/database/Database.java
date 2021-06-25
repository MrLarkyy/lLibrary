package cz.larkyy.llibrary.database;

public interface Database {

    void setState(String uuid, String state);

    String getState(String uuid);

    void close();
}
