package pf.lapimonster.region;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pf.lapimonster.region.listener.RegionListener;

public class RegionLib extends JavaPlugin
{
	private static RegionLib instance;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		
		Bukkit.getServer().getPluginManager().registerEvents(new RegionListener(), this);
	}
	
	public static RegionLib getInstance()
	{
		return instance;
	}
}