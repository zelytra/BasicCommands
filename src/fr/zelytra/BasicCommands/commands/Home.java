package fr.zelytra.BasicCommands.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.zelytra.BasicCommands.BasicCommands;
import fr.zelytra.BasicCommands.utils.Function;
import fr.zelytra.BasicCommands.utils.Message;

public class Home implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg) {
		if (!Function.isplayer(sender)) {
			System.out.println(Message.prefix_console() + "Do you try to bypass the system boy ?");
			return false;
		}

		Plugin plugin = BasicCommands.getPlugin();
		Player player = (Player) sender;
		File file = new File(
				plugin.getDataFolder() + File.separator + "Homes" + File.separator + player.getUniqueId() + ".yml");
		HomeProperties properties = new HomeProperties(file, player);
		file = new File(plugin.getDataFolder() + File.separator + "Config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if (cmd.getName().equalsIgnoreCase("home")) {
			if (arg.length != 1) {
				player.sendMessage(Message.prefix_player() + "§cSyntax error.");
				player.sendMessage(Message.prefix_player() + "§6Try : /home [home_name]");
				return false;
			}

			if (!properties.homeExist(arg[0])) {
				return false;
			}
			// Cooldown part//
			int cooldownT = config.getInt("Home.Delay");
			BasicCommands.getPlugin().getCooldowns();
			if (BasicCommands.getPlugin().getCooldowns().containsKey(player.getName())) {
				long timeLeft = ((BasicCommands.getPlugin().getCooldowns().get(player.getName()) / 1000) + cooldownT)
						- (System.currentTimeMillis() / 1000);
				if (timeLeft > 0) {
					sender.sendMessage(Message.prefix_player() + "§6You need to wait " + timeLeft
							+ " seconds before using this command again.");
					return false;
				}
			}
			BasicCommands.getPlugin().getCooldowns().put(sender.getName(), System.currentTimeMillis());
			// ------------//

			World world = Bukkit.getServer().getWorld(properties.getWorld(arg[0]));
			Location homeLoc = new Location(world, properties.getX(arg[0]), properties.getY(arg[0]),
					properties.getZ(arg[0]));

			player.teleport(homeLoc);
			player.sendMessage(Message.prefix_player() + "§aYou have been teleported to " + arg[0]);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("sethome")) {

			if (arg.length != 1) {
				player.sendMessage(Message.prefix_player() + "§cSyntax error.");
				player.sendMessage(Message.prefix_player() + "§6Try : /sethome [home_name]");
				return false;
			}
			if (!properties.numberHome()) {
				player.sendMessage(Message.prefix_player() + "§eYou reach the max number of home.");
				return false;
			}

			if (properties.createHome(player.getLocation(), arg[0])) {
				player.sendMessage(Message.prefix_player() + "§aHome created with success !");
				return true;
			}

		}
		if (cmd.getName().equalsIgnoreCase("delhome")) {

			if (arg.length != 1) {
				player.sendMessage(Message.prefix_player() + "§cSyntax error.");
				player.sendMessage(Message.prefix_player() + "§6Try : /delhome [home_name]");
				return false;
			}
			if (!properties.homeExist(arg[0])) {
				return false;
			}

			if (properties.delHome(arg[0])) {
				player.sendMessage(Message.prefix_player() + "§aHome deleted.");
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("homelist")) {
			if (arg.length != 0) {
				player.sendMessage(Message.prefix_player() + "§cSyntax error.");
				player.sendMessage(Message.prefix_player() + "§6Try : /homelist");
				return false;
			}
			player.sendMessage("§e----------");
			player.sendMessage("§eList of your homes:");
			for (String s : properties.getHomeList()) {
				player.sendMessage("§e-§l " + s + "§r");
			}
			player.sendMessage("§e----------");
			return true;
		}

		return false;
	}

}
