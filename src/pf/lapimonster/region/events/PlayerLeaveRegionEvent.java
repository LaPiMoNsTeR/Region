package pf.lapimonster.region.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

import pf.lapimonster.region.Region;

/**
 * Called when player leave region
 * @author Haunui
 *
 */
public class PlayerLeaveRegionEvent extends RegionEvent implements Cancellable
{
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	
	public PlayerLeaveRegionEvent(Region r, Player p) 
	{
		super(r, p);
	}
	
	@Override
	public boolean isCancelled()
	{
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean arg0) 
	{
		this.cancelled = arg0;
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
