package pf.lapimonster.region;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Abstract Region Class
 * @author Haunui
 *
 */
public abstract class Region
{
	protected Location l1,l2;
	
	private static ArrayList<Region> regions = new ArrayList<Region>();
	
	/**
	 * Create the region relative to Location 1 and 2
	 * @param l1 - Location 1
	 * @param l2 - Location 2
	 */
	public Region(Location l1, Location l2) 
	{
		regions.add(this);
		this.l1 = l1;
		this.l2 = l2;
	}
	
	/**
	 * Destroy the Region
	 */
	public void destroy()
	{
		regions.remove(this);
	}
	
	/**
	 * Check if player is in region
	 * @param location
	 * @return true if player is in region, or false if he's out
	 */
	public boolean isIn(Location location) 
	{
		Location[] l = {l1,l2};
		
		int xmin = (l[0].getBlockX() <= l[1].getBlockX()) ? l[0].getBlockX() : l[1].getBlockX();
		int ymin = (l[0].getBlockY() <= l[1].getBlockY()) ? l[0].getBlockY() : l[1].getBlockY();
		int zmin = (l[0].getBlockZ() <= l[1].getBlockZ()) ? l[0].getBlockZ() : l[1].getBlockZ();
		
		int xmax = (l[0].getBlockX() >= l[1].getBlockX()) ? l[0].getBlockX() : l[1].getBlockX();
		int ymax = (l[0].getBlockY() >= l[1].getBlockY()) ? l[0].getBlockY() : l[1].getBlockY();
		int zmax = (l[0].getBlockZ() >= l[1].getBlockZ()) ? l[0].getBlockZ() : l[1].getBlockZ();
		
		return location.getBlockX() >= xmin && location.getBlockY() >= ymin && location.getBlockZ() >= zmin &&
				location.getBlockX() <= xmax && location.getBlockY() <= ymax && location.getBlockZ() <= zmax &&
				location.getWorld().equals(l1.getWorld());
	}
	
	/**
	 * Replace all block in region by specified Material
	 * @param material - The material will be use to replace
	 */
	public void generate(Material material) 
	{
		Location[] l = {l1,l2};
		
		int xmin = (l[0].getBlockX() <= l[1].getBlockX()) ? l[0].getBlockX() : l[1].getBlockX();
		int ymin = (l[0].getBlockY() <= l[1].getBlockY()) ? l[0].getBlockY() : l[1].getBlockY();
		int zmin = (l[0].getBlockZ() <= l[1].getBlockZ()) ? l[0].getBlockZ() : l[1].getBlockZ();
		
		int xmax = (l[0].getBlockX() >= l[1].getBlockX()) ? l[0].getBlockX() : l[1].getBlockX();
		int ymax = (l[0].getBlockY() >= l[1].getBlockY()) ? l[0].getBlockY() : l[1].getBlockY();
		int zmax = (l[0].getBlockZ() >= l[1].getBlockZ()) ? l[0].getBlockZ() : l[1].getBlockZ();
		
		for(int x=xmin;x<=xmax;x++) 
		{
			for(int y=ymin;y<=ymax;y++)
			{
				for(int z=zmin;z<=zmax;z++) 
				{
					Location target = new Location(l1.getWorld(), x, y, z);
					target.getBlock().setType(material);
				}
			}
		}
	}
	
	/**
	 * @return All player in region
	 */
	public ArrayList<Player> getPlayerIn() 
	{
		ArrayList<Player> ins = new ArrayList<Player>();
		for(Player p : Bukkit.getServer().getOnlinePlayers()) 
		{
			if(isIn(p.getLocation())) ins.add(p);
		}
		return ins;
	}
	
	/**
	 * Redefine Location
	 * @param l1 - Location 1
	 * @param l2 - Location 2
	 */
	public void setLocations(Location l1, Location l2)
	{
		if(l1 != null)
			this.l1 = l1;
		if(l2 != null)
			this.l2 = l2;
	}
	
	/**
	 * @return array of 2 Locations
	 */
	public Location[] getLocations() 
	{ 
		return new Location[] {l1,l2}; 
	}
	
	/**
	 * @return Location 1
	 */
	public Location getLocation1()
	{
		return this.l1;
	}
	
	/**
	 * @return Location 2
	 */
	public Location getLocation2()
	{
		return this.l2;
	}
	
	/**
	 * @return a array list of all regions
	 */
	public static ArrayList<Region> getRegions()
	{
		return regions;
	}
}
