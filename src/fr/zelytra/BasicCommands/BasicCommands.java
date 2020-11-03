package fr.zelytra.BasicCommands;

import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.zelytra.BasicCommands.commands.Home;
import fr.zelytra.BasicCommands.commands.TabCommands;
import fr.zelytra.BasicCommands.commands.spawn;
import fr.zelytra.BasicCommands.commands.tpa;
import fr.zelytra.BasicCommands.listener.OnConnect;
import fr.zelytra.BasicCommands.listener.OnMouvement;
import fr.zelytra.BasicCommands.utils.Function;
import fr.zelytra.BasicCommands.utils.Message;

public class BasicCommands extends JavaPlugin {
	// Color console
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static BasicCommands plugin;
	public static HashMap<Player, Player> tpa = new HashMap<Player, Player>();
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();

	public void onEnable() {
		plugin = this;
		
		//---TPA Commands---//
		getCommand("tpa").setExecutor(new tpa());
		getCommand("tpa").setTabCompleter(new TabCommands());

		getCommand("tpaccept").setExecutor(new tpa());
		getCommand("tpadeny").setExecutor(new tpa());

		getCommand("tpahere").setExecutor(new tpa());
		getCommand("tpahere").setTabCompleter(new TabCommands());
		//-----------------//
		
		//---SPAWN Commands---//
		getCommand("spawn").setExecutor(new spawn());
		getCommand("setspawn").setExecutor(new spawn());
		//--------------------//
		
		//---HOME Commands---//
		getCommand("sethome").setExecutor(new Home());		
		getCommand("homelist").setExecutor(new Home());
		
		getCommand("home").setExecutor(new Home()); // +/home list
		getCommand("home").setTabCompleter(new TabCommands());
		
		getCommand("delhome").setExecutor(new Home());
		getCommand("delhome").setTabCompleter(new TabCommands());
		//-------------------//
		this.getServer().getPluginManager().registerEvents(new OnConnect(), this);
		this.getServer().getPluginManager().registerEvents(new OnMouvement(), this);

		// Creating basic files
		Function.createFolder(getDataFolder().toString());
		Function.createSpawnFile(getDataFolder().toString()+File.separator, "Spawn");
		Function.createConfigFile(getDataFolder().toString()+File.separator, "Config");
		Function.createFolder(getDataFolder().toString()+File.separator+"Homes");
		System.out.println(Message.prefix_console() + "Is UP !");
	}

	public static BasicCommands getPlugin() {
		return plugin;
	}	

	public HashMap<Player, Player> getTPA() {
		return tpa;

	}
	public HashMap<String, Long> getCooldowns() {
		return cooldowns;

	}

}
