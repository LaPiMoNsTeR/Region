package pf.lapimonster.region.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import pf.lapimonster.region.Region;
import pf.lapimonster.region.events.PlayerEnterRegionEvent;
import pf.lapimonster.region.events.PlayerLeaveRegionEvent;

public class RegionListener implements Listener
{
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e)
	{
		for(Region r : Region.getRegions())
		{
			if(r.isGood() == false) continue;
			
			if(r.isIn(e.getFrom()) == false && r.isIn(e.getTo()))
			{
				PlayerEnterRegionEvent e1 = new PlayerEnterRegionEvent(r, e.getPlayer());
				Bukkit.getPluginManager().callEvent(e1);
				if(e1.isCancelled() == true)
					e.getPlayer().teleport(e.getFrom());
			}
			else if(r.isIn(e.getFrom()) && r.isIn(e.getTo()) == false)
			{
				PlayerLeaveRegionEvent e1 = new PlayerLeaveRegionEvent(r, e.getPlayer());
				Bukkit.getPluginManager().callEvent(e1);
				if(e1.isCancelled() == true)
					e.getPlayer().teleport(e.getFrom());
			}
		}
	}
}
