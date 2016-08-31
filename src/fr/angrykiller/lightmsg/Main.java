package fr.angrykiller.lightmsg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
import fr.angrykiller.lightmsg.ActionBar;
public class Main extends JavaPlugin implements Listener{
	boolean actionbarConfig = false;
	FileConfiguration config = this.getConfig();
	
	public void onEnable(){
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
		
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[]args)
	{
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("msg"))
		{
			String joueur = args[0];
			String pvmessagebrut = "";
			
			for(int i = 1; i < args.length; i++)
			{
				pvmessagebrut = pvmessagebrut + " " + args[i];
			}
			if(pvmessagebrut.isEmpty()){
				p.sendMessage(ChatColor.RED+"Veuillez envoyer un message valide.");
			}
			else{
			try{
				String pvmessagecolored = ChatColor.translateAlternateColorCodes('&', pvmessagebrut);
				Player receiver = Bukkit.getServer().getPlayer(joueur);
				receiver.sendMessage(ChatColor.RED+"["+p.getName()+"] --> [Vous] :"+ChatColor.WHITE+pvmessagecolored);
				p.sendMessage(ChatColor.RED+"[Vous] --> ["+joueur+"] :" +ChatColor.WHITE+pvmessagecolored);
				if(config.getBoolean("actionbar")){
			    ActionBar actionBar = new ActionBar("§7"+p.getName()+" vous a envoyé un message:"+ChatColor.WHITE+pvmessagecolored);
			    actionBar.sendToPlayer(receiver);
				}
				else{}
			
			}catch(NullPointerException npe)
			{
				p.sendMessage(ChatColor.RED+"Le joueur "+joueur+" n'est pas connecté");
			}
			}
			
		}
		
		return true;
	}
	
	public void onDisable(){
		getLogger().info("Plugin stopped successfully !");
	}
	public void onServerReload(){
		getLogger().info("Plugin reloadé!");
		this.reloadConfig();
	}
}