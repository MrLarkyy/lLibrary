package cz.larkyy.llibrary.chat.hex;

import cz.larkyy.llibrary.chat.hex.patterns.ClassicPattern;
import cz.larkyy.llibrary.chat.hex.patterns.GradientPattern;
import cz.larkyy.llibrary.chat.hex.patterns.IPattern;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Colors {
    private static final int VERSION = Integer.parseInt(getMajorVersion(Bukkit.getVersion()).substring(2));

    private static final boolean SUPPORTSRGB = (VERSION >= 16);

    private static final Map<Color, ChatColor> colors = new HashMap<Color, ChatColor>() {

    };

    private static final List<IPattern> patterns = Arrays.asList(new GradientPattern(), new ClassicPattern());

    @Nonnull
    public static String process(@Nonnull String string) {
        string = ChatColor.translateAlternateColorCodes('&', string);
        for (IPattern pattern : patterns)
            string = pattern.process(string);
        return string;
    }

    @Nonnull
    public static String color(@Nonnull String string, @Nonnull Color color) {
        return (SUPPORTSRGB ? ChatColor.of(color).toString() : getClosestColor(color).toString()) + string;
    }

    @Nonnull
    public static String color(@Nonnull String string, @Nonnull Color start, @Nonnull Color end, @Nonnull String color) {
        StringBuilder stringBuilder = new StringBuilder();
        ChatColor[] colors = createGradient(start, end, string.length());
        String[] characters = string.split("");
        for (int i = 0; i < string.length(); i++)
            stringBuilder.append(colors[i]).append(color).append(characters[i]);
        return stringBuilder.toString();
    }

    @Nonnull
    public static ChatColor getColor(@Nonnull String string) {
        return SUPPORTSRGB ? ChatColor.of(new Color(Integer.parseInt(string, 16))) : getClosestColor(new Color(Integer.parseInt(string, 16)));
    }

    @Nonnull
    private static ChatColor[] createGradient(@Nonnull Color start, @Nonnull Color end, int step) {
        ChatColor[] colors = new ChatColor[step];
        int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
        int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
        int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
        int[] direction = { (start.getRed() < end.getRed()) ? 1 : -1, (start.getGreen() < end.getGreen()) ? 1 : -1, (start.getBlue() < end.getBlue()) ? 1 : -1 };
        for (int i = 0; i < step; i++) {
            Color color = new Color(start.getRed() + stepR * i * direction[0], start.getGreen() + stepG * i * direction[1], start.getBlue() + stepB * i * direction[2]);
            if (SUPPORTSRGB) {
                colors[i] = ChatColor.of(color);
            } else {
                colors[i] = getClosestColor(color);
            }
        }
        return colors;
    }

    @Nonnull
    private static ChatColor getClosestColor(Color color) {
        Color nearestColor = null;
        double nearestDistance = 2.147483647E9D;
        for (Color constantColor : colors.keySet()) {
            double distance = Math.pow((color.getRed() - constantColor.getRed()), 2.0D) + Math.pow((color.getGreen() - constantColor.getGreen()), 2.0D) + Math.pow((color.getBlue() - constantColor.getBlue()), 2.0D);
            if (nearestDistance > distance) {
                nearestColor = constantColor;
                nearestDistance = distance;
            }
        }
        return colors.get(nearestColor);
    }

    @Nonnull
    private static String getMajorVersion(@Nonnull String version) {
        Validate.notEmpty(version, "Cannot get major Minecraft version from null or empty string");
        int index = version.lastIndexOf("MC:");
        if (index != -1) {
            version = version.substring(index + 4, version.length() - 1);
        } else if (version.endsWith("SNAPSHOT")) {
            index = version.indexOf('-');
            version = version.substring(0, index);
        }
        int lastDot = version.lastIndexOf('.');
        if (version.indexOf('.') != lastDot)
            version = version.substring(0, lastDot);
        return version;
    }
}
