package fr.angrykiller.lightmsg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
public class Main extends JavaPlugin implements Listener{
	boolean actionbarConfig = false;
	protected static FileConfiguration config;
	public void loadConfig() {
	      config = this.getConfig();
		  config.options().copyDefaults(true);
	      this.saveDefaultConfig();
	}
	public void onEnable(){
		loadConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("Plugin started!");
		getLogger().info("ActionBar is: "+config.getBoolean("actionbar")+"!");
		getLogger().info("Language is:"+config.getString("language")+" !");
	}

	

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("msg"))
		{
			String targetName =  args[0];
			String message = "";
			if(args.length == 0){
				p.sendMessage("Utilisation: /msg <joueur> <message>");
				return false;
			}else{
			if(args.length > 1){
			if(Bukkit.getServer().getPlayerExact(targetName) == null){
				p.sendMessage(ChatColor.RED+"Le joueur "+targetName+" n'est pas connecté !");
				return false;
			}else{
				for(int i = 1; i != args.length; i++){
					message += args[i]+" ";
				}
				Player target = (Player) Bukkit.getServer().getPlayer(targetName);
				String messagecolored = ChatColor.translateAlternateColorCodes('&', message);
				String targetPrefix = ChatColor.GRAY+"["+ChatColor.RED+p.getName()+ChatColor.GRAY+"] "+ChatColor.GOLD+"--> "+ChatColor.GRAY+"["+ChatColor.RED+"Moi"+ChatColor.GRAY+"]"+ChatColor.WHITE+": ";
				String senderPrefix = ChatColor.GRAY+"["+ChatColor.RED+"Moi"+ChatColor.GRAY+"] "+ChatColor.GOLD+"--> "+ChatColor.GRAY+"["+ChatColor.RED+p.getName()+ChatColor.GRAY+"]"+ChatColor.WHITE+": ";
				String targetActionBarMessage = ChatColor.RED+p.getName()+ChatColor.GOLD+" vous a envoyé un message: "+ChatColor.WHITE+messagecolored;
				target.sendMessage(targetPrefix+messagecolored);
				p.sendMessage(senderPrefix+messagecolored);
				if(config.getBoolean("actionbar")){
				ActionBar targetActionBar = new ActionBar(targetActionBarMessage);	
				targetActionBar.sendToPlayer(target);
				}
			}
			}else{
				p.sendMessage(ChatColor.RED+"Veuillez spécifier un message !");
				return false;
			}

			}
		}
		return true;
		}
	
	
	public void onDisable(){
		getLogger().info("Plugin stopped successfully !");
	}
	public void onServerReload(){
		getLogger().info("Plugin reloaded!");
		loadConfig();
	}
}