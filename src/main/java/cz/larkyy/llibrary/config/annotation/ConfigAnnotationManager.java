package cz.larkyy.llibrary.config.annotation;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ConfigAnnotationManager {

    private final List<Class<?>> classes = new ArrayList<>();
    private final Class<? extends Annotation> annotationClass;
    private final JavaPlugin main;
    private final FileConfiguration config;

    public ConfigAnnotationManager(Class<? extends Annotation> annotationClass, JavaPlugin main, FileConfiguration config) {
        this.annotationClass = annotationClass;
        this.main = main;
        this.config = config;
    }

    public void register(Class<?> c) {
        classes.add(c);
    }

    public void unRegister(Class<?> c) {
        classes.remove(c);
    }

    public void update() {
        for (Class<?> class1 : classes) {
            for (Field f : class1.getDeclaredFields()) {
                if (f.isAnnotationPresent(annotationClass)) {
                    try {
                        Method method = annotationClass.getDeclaredMethod("value");
                        f.setAccessible(true);
                        f.set(f,config.get(
                                method.invoke(f.getAnnotation(annotationClass)).toString()
                            )
                        );
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
