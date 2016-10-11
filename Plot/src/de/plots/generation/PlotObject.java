package de.plots.generation;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlotObject {

	Location min;
	Location max;
	int plotId;
	OfflinePlayer owner;
	
	
	public PlotObject(Location _min, Location _max, int _plotID, OfflinePlayer _owner){
		this.min = _min;
		this.max = _max;
		this.plotId =_plotID;
		this.owner = _owner;
	   
	}

	public Location getMin() {
		return min;
	}

	public void setMin(Location min) {
		this.min = min;
	}

	public Location getMax() {
		return max;
	}

	public void setMax(Location max) {
		this.max = max;
	}

	public int getPlotId() {
		return plotId;
	}

	public void setPlotId(int plotId) {
		this.plotId = plotId;
	}

	public OfflinePlayer getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
}
