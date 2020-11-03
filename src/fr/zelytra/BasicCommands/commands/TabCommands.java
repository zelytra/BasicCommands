package fr.zelytra.BasicCommands.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.zelytra.BasicCommands.utils.Function;

public class TabCommands implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String msg, String[] arg) {
		List<String> commandsList = new ArrayList<String>();
		if (Function.isplayer(sender)) {
			if (arg.length == 1) {
				commandsList = new ArrayList<>();
				if (cmd.getName().equalsIgnoreCase("tpa") || cmd.getName().equalsIgnoreCase("tpahere")) {
					for (Player s : Bukkit.getOnlinePlayers()) {
						commandsList.add(s.getName());
					}
				}
				if (cmd.getName().equalsIgnoreCase("home") || cmd.getName().equalsIgnoreCase("delhome")) {
					Player player = (Player) sender;
					File file = new File("plugins/BasicCommands/Homes/" + player.getUniqueId() + ".yml");
					HomeProperties home = new HomeProperties(file, player);
					for (String s : home.getHomeList()) {
						commandsList.add(s);
					}
				}

			}
			return commandsList;
		}

		return null;
	}

}
