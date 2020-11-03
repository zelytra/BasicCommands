package fr.zelytra.BasicCommands.listener;

import java.io.File;
import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.zelytra.BasicCommands.utils.Message;

public class OnConnect implements Listener {
	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent e) {		
		File file = new File("plugins/BasicCommands/Homes/" + e.getPlayer().getUniqueId() + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
				System.out.println(
						Message.prefix_console() + "New home files profil created for: " + e.getPlayer().getName());
			} catch (IOException e1) {
			}
		}
	}

}
