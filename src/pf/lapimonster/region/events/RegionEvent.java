package pf.lapimonster.region.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import pf.lapimonster.region.Region;

public abstract class RegionEvent extends PlayerEvent
{
	private Region region;
	
	public RegionEvent(Region region, Player p) 
	{
		super(p);
		this.region = region;
	}
	
	public Region getRegion()
	{
		return this.region;
	}
}
