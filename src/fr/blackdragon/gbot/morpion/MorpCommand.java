package fr.blackdragon.gbot.morpion;

import fr.blackdragon.gbot.Gbot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MorpCommand extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args[0].equalsIgnoreCase(Gbot.prefix + "morp")) {
			if (Morpion.playersInGame.contains(event.getAuthor())) {
				event.getMessage().delete().queue();
			} else {
				new Morpion(event.getAuthor(), null, event.getJDA().getTextChannelById("694216025356369950"));
				event.getMessage().delete().queue();
			}
		}

	}

}
