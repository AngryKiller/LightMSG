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
		String lang = config.getString("language");
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("msg"))
		{
			String targetName =  args[0];
			String message = "";
			if(args.length == 0){
				p.sendMessage("Usage: /msg <player> <message>");
				return false;
			}else{
			if(args.length > 1){
			if(Bukkit.getServer().getPlayerExact(targetName) == null){
				p.sendMessage(ChatColor.RED+"Player"+targetName+" isn't connected !");
				return false;
			}else{
				for(int i = 1; i != args.length; i++){
					message += args[i]+" ";
				}
				Player target = (Player) Bukkit.getServer().getPlayer(targetName);
				String messagecolored = ChatColor.translateAlternateColorCodes('&', message);
				String targetPrefix = ChatColor.GRAY+"["+ChatColor.RED+p.getName()+ChatColor.GRAY+"] "+ChatColor.GOLD+"--> "+ChatColor.GRAY+"["+ChatColor.RED+"Me"+ChatColor.GRAY+"]"+ChatColor.WHITE+": ";
				String senderPrefix = ChatColor.GRAY+"["+ChatColor.RED+"Me"+ChatColor.GRAY+"] "+ChatColor.GOLD+"--> "+ChatColor.GRAY+"["+ChatColor.RED+p.getName()+ChatColor.GRAY+"]"+ChatColor.WHITE+": ";
				String targetActionBarMessage = ChatColor.RED+p.getName()+ChatColor.GOLD+" sent you a message: "+ChatColor.WHITE+messagecolored;
				target.sendMessage(targetPrefix+messagecolored);
				p.sendMessage(senderPrefix+messagecolored);
				if(config.getBoolean("actionbar")){
				ActionBar targetActionBar = new ActionBar(targetActionBarMessage);	
				targetActionBar.sendToPlayer(target);
				}
			}
			}else{
				p.sendMessage(ChatColor.RED+"Please specify a message!");
				return false;
			}

			}
		}
		if(label.equalsIgnoreCase("lightmsg")){
			Player player = (Player) sender;
			player.sendMessage(ChatColor.GREEN+"LightMSG version 0.2 by"+ChatColor.BLUE+" AngryKiller");
			player.sendMessage(ChatColor.GREEN+"Configuration:");
			player.sendMessage(ChatColor.RED+"ActionBar"+ChatColor.WHITE+" = "+ChatColor.AQUA+config.getBoolean("actionbar"));
			player.sendMessage(ChatColor.RED+"Language"+ChatColor.WHITE+" = "+ChatColor.AQUA+config.getString("language"));
		}
		return true;
		}
	
	
	public void onDisable(){
		getLogger().info("Plugin stopped successfully (lel)!");
	}
	public void onServerReload(){
		getLogger().info("Plugin reloaded!");
		loadConfig();
	}
}