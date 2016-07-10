package pf.lapimonster.region;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import pf.lapimonster.region.events.PlayerRegionEditEvent;
import pf.lapimonster.region.events.PlayerRegionEditedEvent;

public class RegionEdit implements Listener
{
	private Location l1, l2;
	private Player p;
	private Region r;
	
	private static ArrayList<RegionEdit> res = new ArrayList<RegionEdit>();
	
	
	public RegionEdit(Player p, Region r)
	{
		res.add(this);
		this.r = r;
		RegionLib.getInstance().getServer().getPluginManager().registerEvents(this, RegionLib.getInstance());
		
		PlayerRegionEditEvent e = new PlayerRegionEditEvent(this.r, p);
		Bukkit.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled())
		{
			this.delete();
			return;
		}
		
		this.p = p;
		this.p.sendMessage("§o§lCliquez à 2 endroit diffèrent pour définir le zone");
	}
	
	public void delete()
	{ 
		HandlerList.unregisterAll(this);
		res.remove(this); 
	}
	
	public Location[] getLocations()
	{ 
		return new Location[] {this.l1, this.l2};
	}
	
	public void setLocation(int i, Location l)
	{ 
		p.sendMessage("Location "+i+" définie.");
		if(i == 0) 
			this.l1 = l;
		else if(i == 1) 
		{
			this.l2 = l;
			this.implement();
		}
	}
	
	public void implement()
	{
		PlayerRegionEditedEvent e1 = new PlayerRegionEditedEvent(this.r, p);
		Bukkit.getServer().getPluginManager().callEvent(e1);
		if(e1.isCancelled() == false)
		{
			r.l1 = this.l1;
			r.l2 = this.l2;
			p.sendMessage("Region initialisé.");
		}
		else p.sendMessage("Edition de la region annulé.");
		this.delete();
	}
	
	public Player getPlayer()
	{ 
		return this.p; 
	}
	
	public Region getRegion()
	{
		return this.r;
	}
	
	public static ArrayList<RegionEdit> getRegionEdits()
	{ 
		return res; 
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Player p = e.getPlayer();
		
		if(e.getItem() == null)
			return;

		for(RegionEdit re : RegionEdit.getRegionEdits())
		{
			if(p.equals(re.getPlayer()) && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getItem().getType() == Material.STICK)
			{
				Location l = e.getClickedBlock().getLocation();
				if(re.getLocations()[0] == null) re.setLocation(0, l);
				else if(re.getLocations()[1] == null) re.setLocation(1, l);
				e.setCancelled(true);
				break;
			}
		}
	}
}