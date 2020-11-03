package fr.zelytra.BasicCommands.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMouvement implements Listener {
	@EventHandler
	public void OnPlayerMove(PlayerMoveEvent event) {
		/*if (BasicCommands.getPlugin().getTPA().containsValue(event.getPlayer())) {
			
			BasicCommands.getPlugin().getTPA().remove(event.getPlayer());
			event.getPlayer().sendMessage(Message.prefix_player()+"§cYou moved... TPA canceld.");
		}*/
		
	}
	

}
