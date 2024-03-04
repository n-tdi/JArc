package world.ntdi.arc;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Arc extends JavaPlugin implements Listener {
    public Location pos1 = null;
    public Location pos2 = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);

        getCommand("arc").setExecutor(new ArcCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent p_playerInteractEvent) {
        if (!p_playerInteractEvent.getPlayer().isOp() || p_playerInteractEvent.getPlayer().getGameMode() != GameMode.CREATIVE) return;
        if (p_playerInteractEvent.getPlayer().getInventory().getItemInMainHand().getType() != Material.STICK) return;

        if (p_playerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
            pos1 = p_playerInteractEvent.getClickedBlock().getLocation().add(0.5, 0.5, 0.5);
            p_playerInteractEvent.setCancelled(true);
            p_playerInteractEvent.getPlayer().sendMessage(ChatColor.GREEN + "Updated Position 1");
        } else if (p_playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
            pos2 = p_playerInteractEvent.getClickedBlock().getLocation().add(0.5, 0.5, 0.5);
            p_playerInteractEvent.setCancelled(true);
            p_playerInteractEvent.getPlayer().sendMessage(ChatColor.GREEN + "Updated Position 2");
        }
    }
}
