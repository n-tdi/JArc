package world.ntdi.arc;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ArcGenerator {
    public static List<Location> generateArc(Location start, Location end, double resolution) {
        List<Location> arcPoints = new ArrayList<>();

        Location center = start.clone().add(end).multiply(0.5);
        double maxHeight = Math.max(start.getY(), end.getY()) + 3;
        double heightScale = 0.1;

        double distance = start.distance(end);

        for (double i = 0; i <= resolution; i++) {
            double t = i / resolution;

            double x = start.getX() + t * (end.getX() - start.getX());
            double y = start.getY() + t * (end.getY() - start.getY());
            double z = start.getZ() + t * (end.getZ() - start.getZ());

            double arcHeight = maxHeight * heightScale * 4 * t * (1 - t);

            arcPoints.add(new Location(start.getWorld(), x, y + arcHeight, z));
        }


        return arcPoints;
    }
}
