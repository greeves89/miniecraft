package de.plots.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.plots.generation.PlotGeneration;
import de.plots.mysql.plotmysql;
import de.plots.utils.plotjava;

public class PlotBuildBreak implements Listener {

	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (!plotjava.isPlotOwnedByPlayer(p)) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (!plotjava.isPlotOwnedByPlayer(p)) {
			e.setCancelled(true);
		}
	}
	
}
