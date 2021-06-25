package cz.larkyy.llibrary.holograms;

import cz.larkyy.llibrary.chat.ChatUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class Hologram {

    private Location location;
    private List<String> lines;
    private List<ArmorStand> armorStands;

    public Hologram(Location location, List<String> lines) {
        this.location = location;
        this.lines = lines;
        this.armorStands = new ArrayList<>();
    }
    public Hologram(List<String> lines) {
        this.location = null;
        this.lines = lines;
        this.armorStands = new ArrayList<>();
    }

    //
    //  LOCATION
    //

    public Location getLocation() {
        return location;
    }

    public Hologram teleport(Location loc) {
        this.location = loc;
        return this;
    }

    public Hologram setLocation(Location location) {
        this.location = location;
        return this;
    }

    //
    //  UTILS
    //

    public void spawn() {
        spawn(location);
    }

    public void spawn(Location location) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Location loc = location.clone().add(0,lines.size()*0.25-i*0.25,0);

            ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
            as.setCanTick(false);
            as.setCanMove(false);
            as.setGravity(false);
            as.setSmall(true);
            as.setBasePlate(false);
            as.setMarker(true);
            as.setInvisible(true);
            as.setAI(false);
            as.setCustomNameVisible(true);
            as.setCustomName(ChatUtils.format(line));
            as.setPersistent(false);

            armorStands.add(as);
        }
    }

    public void despawn() {
        armorStands.forEach(Entity::remove);
        armorStands = new ArrayList<>();
    }

    public Hologram copy() {
        return new Hologram(new ArrayList<>(lines));
    }

    public boolean isSpawned() {
        return (!armorStands.isEmpty());
    }

    //
    //  LINES
    //

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
