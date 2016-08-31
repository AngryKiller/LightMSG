package fr.angrykiller.lightmsg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
import fr.angrykiller.lightmsg.ActionBar;

public class Main extends JavaPlugin implements Listener{	
	public void onEnable(){
		
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("Plugin started !");	
	}
	@EventHandler
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[]args)
	{
		Player p = (Player) sender;
		
		if(label.equalsIgnoreCase("msg"))
		{
			String joueur = args[0];
			String pvmessage = "";
			
			for(int i = 1; i < args.length; i++)
			{
				pvmessage = pvmessage + " " + args[i];
			}	
			try{
				Player receiver = Bukkit.getServer().getPlayer(joueur);
				receiver.sendMessage(ChatColor.RED+"["+p.getName()+"] --> [Vous] :"+ChatColor.WHITE+pvmessage);
				p.sendMessage(ChatColor.RED+"[Vous] --> ["+joueur+"] :" +ChatColor.WHITE+pvmessage);
			    ActionBar actionBar = new ActionBar("§7"+p.getName()+" vous a envoyé un message:"+ChatColor.WHITE+pvmessage);
			    actionBar.sendToPlayer(receiver);
			}catch(NullPointerException npe)
			{
				p.sendMessage(ChatColor.RED+"Le joueur "+joueur+" n'est pas connecté");
			}
			
		}
		
		return true;
	}
	
	public void onDisable(){
		getLogger().info("Plugin stopped successfuly !");
	}
}