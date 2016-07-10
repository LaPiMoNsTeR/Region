package pf.lapimonster.region.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import pf.lapimonster.region.Region;

/**
 * Called when player start to edit region
 * @author Haunui
 *
 */
public class PlayerRegionEditEvent extends RegionEvent implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	
	public PlayerRegionEditEvent(Region region, Player player) 
	{
		super(region, player);
	}
	
	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancelled) 
	{
		this.cancelled = cancelled;
	}
	
	@Override
	public HandlerList getHandlers() 
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}
