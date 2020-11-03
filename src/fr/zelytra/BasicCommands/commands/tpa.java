package fr.zelytra.BasicCommands.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.scheduler.BukkitScheduler;

import fr.zelytra.BasicCommands.BasicCommands;
import fr.zelytra.BasicCommands.utils.Function;
import fr.zelytra.BasicCommands.utils.Message;

public class tpa implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] arg) {
		if (!Function.isplayer(sender)) {
			System.out.println(Message.prefix_console() + "Do you try to bypass the system boy ?");
			return false;
		}

		Plugin plugin = BasicCommands.getPlugin();
		Player player = (Player) sender;
		File file = new File(plugin.getDataFolder() + File.separator + "Config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if (cmd.getName().equalsIgnoreCase("tpa")) {
			if (arg.length == 0) {
				player.sendMessage(Message.prefix_player() + "§cSyntax error.");
				player.sendMessage(Message.prefix_player() + "§cPlease type a command or type §e/Bhelp.");
				return false;
			}
			if (!(arg.length == 1)) {
				player.sendMessage(Message.prefix_player() + "§cSyntax error.");
				return false;
			}
			Player target = plugin.getServer().getPlayer(arg[0]);
			Player Sender = player;

			if (!Function.isplayer(target)) {
				player.sendMessage(Message.prefix_player() + "§c No player with this name is connected.");
				return false;
			}
			if (target.getName() == player.getName()) {
				player.sendMessage(Message.prefix_player() + "§c You cannot tp to your self...");
				return false;
			}
			// Cooldown part//
			int cooldownT = config.getInt("TPA.Delay");
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

			BasicCommands.getPlugin().getTPA().put(target, Sender);
			player.sendMessage(Message.prefix_player() + "§eYou have send a request to " + target.getName());
			target.sendMessage("§a§l" + player.getName() + "§r§e has send you a TPA request.");

			BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

			scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					if (BasicCommands.getPlugin().getTPA().containsKey(target)) {
						player.sendMessage(Message.prefix_player() + "§eRequest time out");
						target.sendMessage(Message.prefix_player() + "§eRequest time out");
						BasicCommands.getPlugin().getTPA().remove(target);
					}
				}

			}, 200);

		}
		if (cmd.getName().equalsIgnoreCase("tpaccept")) {
			Player Sender;

			if (BasicCommands.getPlugin().getTPA().isEmpty()
					|| !BasicCommands.getPlugin().getTPA().containsKey(player)) {
				player.sendMessage(Message.prefix_player() + "§bNo tpa request has been send for you.");
				return false;
			}

			Sender = BasicCommands.getPlugin().getTPA().get(player);
			if (Sender.getName().equalsIgnoreCase(Sender.getName())) {
				System.out.println(
						Message.prefix_console() + player.getName() + " has been teleported to " + Sender.getName());
				player.sendMessage(Message.prefix_player() + "§eYou accept the request !");
				Sender.teleport(player);
				BasicCommands.getPlugin().getTPA().remove(player);
				return true;
			}

			return false;
		}
		if (cmd.getName().equalsIgnoreCase("tpadeny")) {
			Player Sender;
			if (!BasicCommands.getPlugin().getTPA().containsKey(player)
					|| BasicCommands.getPlugin().getTPA().isEmpty()) {
				player.sendMessage(Message.prefix_player() + "§bNo tpa request has been send for you.");
				return false;
			}

			Sender = BasicCommands.getPlugin().getTPA().get(player);
			if (Sender.getName().equalsIgnoreCase(Sender.getName())) {
				player.sendMessage(Message.prefix_player() + "§eYou deny the request of " + Sender.getName());
				Sender.sendMessage(Message.prefix_player() + "§a§l" + player.getName() + "§r§c deny the request.");
				BasicCommands.getPlugin().getTPA().remove(player);
				return true;
			}

		}
		if (cmd.getName().equalsIgnoreCase("tpahere")) {
			player.sendMessage(Message.prefix_player()+"§cCommand not yet implement.");
			
		}

		return false;
	}

}
