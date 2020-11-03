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

public class spawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg) {
		if (!Function.isplayer(sender)) {
			System.out.println(Message.prefix_console() + "Do you try to bypass the system boy ?");
			return false;
		}
		Plugin plugin = BasicCommands.getPlugin();
		Player player = (Player) sender;

		File file = new File(plugin.getDataFolder() + File.separator + "Spawn.yml");
		SpawnProperties spawn = new SpawnProperties(file);
		file = new File(plugin.getDataFolder() + File.separator + "Config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		

		World world = Bukkit.getServer().getWorld(spawn.getWorld());
		Location spawnLocation = new Location(world, spawn.getX(), spawn.getY(), spawn.getZ());
		spawnLocation.setPitch((float) spawn.getPitch());
		spawnLocation.setYaw((float) spawn.getYaw());

		if (cmd.getName().equalsIgnoreCase("spawn")) {
			if (arg.length > 0) {
				player.sendMessage(Message.prefix_player() + "§cSyntax error.");
				player.sendMessage(Message.prefix_player() + "§c6Please type a command or type §e/Bhelp.");
				return false;
			}
			// Cooldown part//			
			int cooldownT = config.getInt("Spawn.Delay");
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

			player.teleport(spawnLocation);
			player.sendMessage(Message.prefix_player() + "§eYou have been teleported to the Spawn.");
			return true;

		}
		if (cmd.getName().equalsIgnoreCase("setspawn")) {
			if (arg.length > 0) {
				player.sendMessage(Message.prefix_player() + "§cSyntax error.");
				player.sendMessage(Message.prefix_player() + "§cPlease type a command or type §e/Bhelp.");
				return false;
			}
			spawn.setX(player.getLocation().getX());
			spawn.setY(player.getLocation().getY());
			spawn.setZ(player.getLocation().getZ());
			spawn.setYaw(player.getLocation().getYaw());
			spawn.setPitch(player.getLocation().getPitch());
			player.sendMessage(Message.prefix_player() + "§eSpawn set successfuly !");
			return true;

		}

		return false;
	}

}
