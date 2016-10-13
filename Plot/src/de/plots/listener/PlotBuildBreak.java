package de.plots.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import de.plots.generation.PlotGeneration;

public class PlotBuildBreak implements Listener {

	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		
	}
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
	}
	public static boolean isPlotOwned(Player p) {
		
		
		
		return false;
	}
	
}
