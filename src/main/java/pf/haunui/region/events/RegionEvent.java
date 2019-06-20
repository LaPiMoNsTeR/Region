package pf.haunui.region.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import pf.haunui.region.Region;

/**
 * Abstract class for Region event
 * @author Haunui
 *
 */
public abstract class RegionEvent extends PlayerEvent
{
	private Region region;
	
	public RegionEvent(Region region, Player player) 
	{
		super(player);
		this.region = region;
	}
	
	/**
	 * 
	 * @return region called with the event
	 */
	public Region getRegion()
	{
		return this.region;
	}
}
