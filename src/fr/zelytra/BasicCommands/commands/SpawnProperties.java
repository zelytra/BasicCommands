package fr.zelytra.BasicCommands.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.zelytra.BasicCommands.utils.Message;


public class SpawnProperties {
	
	private FileConfiguration properties;
	File file;

	public SpawnProperties(File file) {
		if (!file.exists()) {
			System.out.println(Message.prefix_console() + "Yml file not found. Reload for create new one");
		}
		this.file = file;
		properties = YamlConfiguration.loadConfiguration(file);

	}
	
	public double getX() {		
		return properties.getDouble("Spawn.X");

	}
	public double getY() {		
		return properties.getDouble("Spawn.Y");

	}
	public double getZ() {		
		return properties.getDouble("Spawn.Z");

	}
	public double getYaw() {		
		return properties.getDouble("Spawn.Yaw");

	}
	public double getPitch() {		
		return properties.getDouble("Spawn.Pitch");

	}
	public String getWorld() {		
		return properties.getString("Spawn.World");

	}
	public void setX(double input) {		
		 properties.set("Spawn.X",input);
		 savefile();

	}
	public void setY(double input) {		
		properties.set("Spawn.Y",input);
		savefile();

	}
	public void setZ(double input) {		
		 properties.set("Spawn.Z",input);
		 savefile();

	}
	public void setYaw(double input) {		
		 properties.set("Spawn.Yaw",input);
		 savefile();

	}
	public void setPitch(double input) {		
		 properties.set("Spawn.Pitch",input);
		 savefile();

	}
	public void SetWorld(String input) {		
		properties.set("Spawn.World",input);
		savefile();

	}
	private void savefile() {
		try {
			properties.save(file);
		} catch (IOException e) {			
			
		}
		
	}

}
