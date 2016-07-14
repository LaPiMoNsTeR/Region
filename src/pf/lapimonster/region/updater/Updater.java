package pf.lapimonster.region.updater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Updater 
{
	private String version;
	private JavaPlugin plugin;
	
	private String jarPath;
	private String versionPath;
	
	private String jarLocalPath;
	
	public Updater(JavaPlugin plugin) throws IOException 
	{
		this.plugin = plugin;
		this.version = this.plugin.getDescription().getVersion();
		this.jarPath = "http://lapimonster.ovh/plugins/"+this.plugin.getName()+"/version.txt";
		this.versionPath = "http://lapimonster.ovh/plugins/"+this.plugin.getName()+"/"+plugin.getName()+".jar";
		this.jarLocalPath = this.plugin.getDataFolder().getParent();
		
		URLConnection availableVersionFile = new URL(this.jarPath).openConnection();
		URLConnection availableJar = new URL(this.versionPath).openConnection();
		
		final String availableVersion = IOUtils.toString(availableVersionFile.getInputStream());
		File localJar = new File(this.jarLocalPath, plugin.getName()+".jar");
		
		if(version.equals(availableVersion) == false || localJar.length() != availableJar.getContentLength())
		{
			new BukkitRunnable() 
			{
				
				@Override
				public void run() 
				{
					Bukkit.getServer().getLogger().info("["+Updater.this.plugin.getName()+"] [Updater] Nouvelle version disponible : "+availableVersion);
					Bukkit.getServer().getLogger().info("["+Updater.this.plugin.getName()+"] [Updater] Mise à jour en cours ...");
					
					Updater.this.download(Updater.this.versionPath, new File(Updater.this.jarLocalPath));
					
					Bukkit.getServer().getLogger().info("["+Updater.this.plugin.getName()+"] [Updater] Mise à jour terminé. (version "+availableVersion+")");
					Bukkit.getServer().getLogger().info("["+Updater.this.plugin.getName()+"] [Updater] Rechargement du serveur ...");
					
					Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "reload");
				}
			}.runTaskAsynchronously(this.plugin);
		}
		else Bukkit.getServer().getLogger().info("["+Updater.this.plugin.getName()+"] [Updater] Plugin à jour.");
	}
	
	
	private void download(String host, File path)
    {
        InputStream input = null;
        FileOutputStream writeFile = null;
        
        try
        {
            URL url = new URL(host);
            URLConnection connection = url.openConnection();
            int fileLength = connection.getContentLength();

            if (fileLength == -1)
            {
                System.out.println("Invalide URL or file.");
                return;
            }

            input = connection.getInputStream();
            String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
            writeFile = new FileOutputStream(new File(path, fileName));
            byte[] buffer = new byte[1024];
            int read;

            while ((read = input.read(buffer)) > 0)
                writeFile.write(buffer, 0, read);
            writeFile.flush();
        }
        catch (IOException e)
        {
            System.out.println("["+this.plugin.getName()+"] [Updater] Error while trying to download the file.");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                writeFile.close();
                input.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
