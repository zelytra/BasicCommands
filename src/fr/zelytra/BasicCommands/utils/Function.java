package fr.zelytra.BasicCommands.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Function {
	public static boolean isplayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			return false;
		}
	}

	public static void createFolder(String FileName) {
		File folder = new File(FileName);
		if (!folder.exists()) {
			folder.mkdir();
			System.out.println(Message.prefix_console() + "File [" + FileName + "] created.");
		}
	}

	@SuppressWarnings("null")
	public static boolean createSpawnFile(String File, String FileName) {

		File file = new File(File + FileName + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.set("Spawn.X", 0);
			config.set("Spawn.Y", 0);
			config.set("Spawn.Z", 0);
			config.set("Spawn.Pitch", 0);
			config.set("Spawn.Yaw", 0);
			config.set("Spawn.World", "world");

			try {
				config.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		}
		return false;
	}	
	@SuppressWarnings("null")
	public static boolean createConfigFile(String File, String FileName) {

		File file = new File(File + FileName + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.set("Home.MaxHome", 2);
			config.set("Home.Delay", 5);
			config.set("Spawn.Delay", 5);
			config.set("TPA.Delay", 5);		

			try {
				config.save(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		}
		return false;
	}	

	public static boolean deleteFile(String FileName) {
		File file = new File(FileName);
		if (file.exists()) {
			file.delete();
			return true;
		}
		return false;
	}

	public static File getYmlFile(String FileName) {
		return new File("Plugins" + File.separator + "StructureGenerator" + File.separator + "Properties"
				+ File.separator + FileName + ".yml");
	}

}
