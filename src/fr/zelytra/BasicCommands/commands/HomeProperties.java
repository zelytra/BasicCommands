package fr.zelytra.BasicCommands.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import fr.zelytra.BasicCommands.BasicCommands;
import fr.zelytra.BasicCommands.utils.Message;

public class HomeProperties {

	private FileConfiguration properties;
	private FileConfiguration config;
	private Player p;
	File file;
	Plugin plugin = BasicCommands.getPlugin();

	ArrayList<String> homeList = new ArrayList<String>();

	public HomeProperties(File file, Player player) {
		if (!file.exists()) {
			player.sendMessage(
					Message.prefix_player() + "§6No home profile is link to your player please contact an Admin.");
			System.out.println(Message.prefix_console() + "Any home files were find for " + player.getName() + " with :"
					+ player.getUniqueId());
		}
		this.file = file;
		this.p = player;
		properties = YamlConfiguration.loadConfiguration(file);
		file = new File(plugin.getDataFolder() + File.separator + "Config.yml");
		config = YamlConfiguration.loadConfiguration(file);

	}

	public boolean createHome(Location location, String homeName) {
		try {
			if (properties.getString(homeName).contains(homeName)) {
				p.sendMessage(Message.prefix_player() + "§6Home already exist.");
				return false;
			}
		} catch (Exception e) {
		}
		properties.set(homeName + ".X", location.getX());
		properties.set(homeName + ".Y", location.getY());
		properties.set(homeName + ".Z", location.getZ());
		properties.set(homeName + ".World", location.getWorld().getName());
		savefile();

		return true;
	}

	public ArrayList<String> getHomeList() {
		homeList.addAll(properties.getKeys(true));
		for (int x = 0; x < homeList.size(); x++) {
			if (homeList.get(x).contains(".")) {
				homeList.remove(x);
				x--;
			}
		}
		return homeList;
	}

	public String getWorld(String homeName) {
		return properties.getString(homeName + ".World");

	}

	public double getX(String homeName) {
		return properties.getDouble(homeName + ".X");

	}

	public double getY(String homeName) {
		return properties.getDouble(homeName + ".Y");

	}

	public double getZ(String homeName) {
		return properties.getDouble(homeName + ".Z");

	}

	public boolean homeExist(String homeName) {
		try {
			if (properties.getString(homeName).contains(homeName)) {
				return true;
			}
		} catch (Exception e) {
			p.sendMessage(Message.prefix_player() + "§6This home doesn't exist.");
			return false;
		}
		return false;
	}

	public boolean delHome(String homeName) {
		properties.set(homeName, null);
		savefile();
		return true;
	}

	public boolean numberHome() {
		System.out.println(getHomeList().size() + "  " + config.getInt("Home.MaxHome"));
		if ((getHomeList().size()) > (config.getInt("Home.MaxHome"))) {
			return false;
		}
		return true;
	}

	private void savefile() {
		try {
			properties.save(file);
		} catch (IOException e) {

		}

	}

}
