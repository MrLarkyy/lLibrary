package cz.larkyy.llibrary.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config {

    private final File file;
    private FileConfiguration config;

    private final JavaPlugin main;

    public Config(JavaPlugin main,String path) {
        this.main = main;
        this.file = new File(main.getDataFolder(), path);
    }

    public void load() {
        if (!file.exists())
            try {
                main.saveResource(file.getName(), false);
            } catch (IllegalArgumentException e) {
                try {
                    file.createNewFile();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }


        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfiguration() {
        if (config == null)
            load();

        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
