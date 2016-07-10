package pf.lapimonster.region;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Region
{
	protected Location l1,l2;
	
	private static ArrayList<Region> regions = new ArrayList<Region>();
	
	public Region(Location l1, Location l2) 
	{
		regions.add(this);
		this.l1 = l1;
		this.l2 = l2;
	}
	
	public void destroy()
	{
		regions.remove(this);
	}
	
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
	
	public void generate(Material m) 
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
					target.getBlock().setType(m);
				}
			}
		}
	}
	
	public ArrayList<Player> getPlayerIn() 
	{
		ArrayList<Player> ins = new ArrayList<Player>();
		for(Player p : Bukkit.getServer().getOnlinePlayers()) 
		{
			if(isIn(p.getLocation())) ins.add(p);
		}
		return ins;
	}
	
	public void setLocations(Location l1, Location l2)
	{
		this.l1 = l1;
		this.l2 = l2;
	}
	
	public Location[] getLocations() 
	{ 
		return new Location[] {l1,l2}; 
	}
	
	public static ArrayList<Region> getRegions()
	{
		return regions;
	}
}
