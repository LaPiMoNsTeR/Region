package pf.lapimonster.region;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class RegionSaver {
	
	private Region region;
	private ArrayList<String> save = new ArrayList<String>();
	private int x = 0, y = 0, z = 0;
	
	public RegionSaver(Region region) 
	{
		this.region = region;
	}
	
	public RegionSaver(Region region, int x, int y, int z) 
	{
		this.region = region;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setMove(int x, int y, int z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void save(Material[] m) 
	{
		Location[] l = region.getLocations();
		
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
					Location target = new Location(l[0].getWorld(),x,y,z);
					if(m == null)
					{
						String block = blockToStr(target.getBlock());
						if(block != null) save.add(block); 
					}
					else
					{
						for(Material a : m)
						{
							if(target.getBlock().getType() == a) 
							{
								String block = blockToStr(target.getBlock());
								if(block != null) save.add(block); 
							}
						}
					}
				}
			}
		}
	}
	
	public void regen() 
	{
		for(String s : save) 
		{
			restoreBlock(s);
		}
		this.region.setLocations(this.region.getLocations()[0].add(new Vector(this.x, this.y, this.z)), 
				this.region.getLocations()[1].add(new Vector(this.x, this.y, this.z)));
	}
	
	@SuppressWarnings("deprecation")
	protected String blockToStr(Block b) 
	{
		if(b.getType() != Material.AIR) return locToStr(b.getLocation()) + "&&" + b.getType().toString() + "//" + b.getData();
		else return null;
	}
	
	@SuppressWarnings("deprecation")
	protected void restoreBlock(String s) 
	{
		String[] split = s.split("&&");
		String[] array = split[0].split("//");
		Location loc = new Location(Bukkit.getWorld(array[0]), Double.parseDouble(array[1])+this.x, Double.parseDouble(array[2])+this.y, Double.parseDouble(array[3])+this.z);
		Block b = loc.getBlock();
		array = split[1].split("//");
		b.setTypeIdAndData(Material.getMaterial(array[0]).getId(), Byte.parseByte(array[1]), true);
	}
	
	protected String locToStr(Location loc) 
	{
		String[] world = loc.getWorld().toString().split("=");
		return world[1].replace("}", "") + "//" + loc.getBlockX() + "//" + loc.getBlockY() + "//" + loc.getBlockZ();
	}

}