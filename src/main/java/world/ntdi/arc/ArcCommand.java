package world.ntdi.arc;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ArcCommand implements CommandExecutor {
    private final Arc m_arc;

    public ArcCommand(final Arc p_arc) {
        m_arc = p_arc;
    }

    @Override
    public boolean onCommand(final CommandSender p_commandSender, final Command p_command, final String p_s, final String[] p_strings) {
        if (!(p_commandSender instanceof Player p)) return false;

        final Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 1.0F);

        if (m_arc.pos1 == null || m_arc.pos2 == null) return false;

        new BukkitRunnable() {
            int i = 0;
            ArmorStand m_armorStand;
            List<Location> arcPoints;
            @Override
            public void run() {
                if (i == 0) {
                    arcPoints = ArcGenerator.generateArc(m_arc.pos1, m_arc.pos2, 50);
                }

                final Location armorstandLocation = arcPoints.get(i + 1).clone().subtract(0, 1.5, 0);

                if (i == 0) {
                    m_armorStand = (ArmorStand) p.getWorld().spawnEntity(armorstandLocation, EntityType.ARMOR_STAND);
                    m_armorStand.setVisible(false);
                    m_armorStand.getEquipment().setHelmet(new ItemStack(Material.TNT));
                }

                arcPoints.get(i).getWorld().spawnParticle(Particle.REDSTONE, arcPoints.get(i), 50, dustOptions);
                arcPoints.get(i).getWorld().spawnParticle(Particle.FLAME, arcPoints.get(i), 5, 0.2, 0.2, 0.2, 0);
                arcPoints.get(i).getWorld().spawnParticle(Particle.EXPLOSION_LARGE, arcPoints.get(i), 5, 0.2, 0.2, 0.2, 0);
                arcPoints.get(i).getWorld().spawnParticle(Particle.SMOKE_LARGE, arcPoints.get(i), 5, 0.2, 0.2, 0.2, 0);


                m_armorStand.teleport(armorstandLocation);
                i++;

                if (i > arcPoints.size() - 2) {
                    i = 0;
                    m_armorStand.getEquipment().clear();
                    m_armorStand.remove();
                    m_armorStand = null;
                }
            }
        }.runTaskTimer(m_arc, 1, 1);

        return true;
    }
}
