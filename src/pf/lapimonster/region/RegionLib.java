package pf.lapimonster.region;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pf.lapimonster.region.listener.RegionListener;
import pf.lapimonster.region.updater.Updater;

public class RegionLib extends JavaPlugin
{
	private static RegionLib instance;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		
		try
		{
			new Updater(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Bukkit.getServer().getPluginManager().registerEvents(new RegionListener(), this);
	}
	
	public static RegionLib getInstance()
	{
		return instance;
	}
}