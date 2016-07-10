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

/**
 * Used for Edit a region
 * @author Haunui
 *
 */
public class RegionEdit implements Listener
{
	private Location l1, l2;
	private Player player;
	private Region region;
	
	private static ArrayList<RegionEdit> res = new ArrayList<RegionEdit>();
	
	/**
	 * 
	 * @param player - the player who edit the region
	 * @param region - the region that edit
	 */
	public RegionEdit(Player player, Region region)
	{
		res.add(this);
		this.region = region;
		RegionLib.getInstance().getServer().getPluginManager().registerEvents(this, RegionLib.getInstance());
		
		PlayerRegionEditEvent e = new PlayerRegionEditEvent(this.region, player);
		Bukkit.getServer().getPluginManager().callEvent(e);
		if(e.isCancelled())
		{
			this.delete();
			return;
		}
		
		this.player = player;
		this.player.sendMessage("§o§lCliquez à 2 endroit diffèrent pour définir le zone");
	}
	
	/**
	 * Delete the RegionEdit
	 */
	public void delete()
	{ 
		HandlerList.unregisterAll(this);
		res.remove(this); 
	}
	
	/**
	 * @return array of 2 Locations
	 */
	public Location[] getLocations()
	{ 
		return new Location[] {this.l1, this.l2};
	}
	
	/**
	 * @param i - index (0 or 1)
	 * @param location - the new location
	 */
	public void setLocation(int i, Location location)
	{ 
		player.sendMessage("Location "+i+" définie.");
		if(i == 0) 
			this.l1 = location;
		else if(i == 1) 
		{
			this.l2 = location;
			this.implement();
		}
	}
	
	/**
	 * Implement the location to the region
	 */
	public void implement()
	{
		PlayerRegionEditedEvent e1 = new PlayerRegionEditedEvent(this.region, player);
		Bukkit.getServer().getPluginManager().callEvent(e1);
		if(e1.isCancelled() == false)
		{
			region.l1 = this.l1;
			region.l2 = this.l2;
			player.sendMessage("Region initialisé.");
		}
		else player.sendMessage("Edition de la region annulé.");
		this.delete();
	}
	
	/**
	 * @return Region's editor
	 */
	public Player getPlayer()
	{ 
		return this.player; 
	}
	
	/**
	 * @return Edited region
	 */
	public Region getRegion()
	{
		return this.region;
	}
	
	/**
	 * @return array list of all RegionEdit
	 */
	public static ArrayList<RegionEdit> getRegionEdits()
	{ 
		return res; 
	}
	
	/**
	 * The event will called when player will edit the region (by right click stick)
	 * @param e - event
	 */
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