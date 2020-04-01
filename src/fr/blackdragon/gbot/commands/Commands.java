package fr.blackdragon.gbot.commands;

import fr.blackdragon.gbot.Gbot;
import fr.blackdragon.gbot.morpion.MorpCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {

	public void onMessageReceived(MessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args[0].equalsIgnoreCase(Gbot.prefix + "ping")) {

			if (event.getAuthor().getMutualGuilds().isEmpty()) {
				return;
			}

			if (event.getAuthor().getMutualGuilds().get(0).getMember(event.getAuthor()).getRoles()
					.contains(event.getAuthor().getMutualGuilds().get(0).getRoleById("688073869369671685"))) {
				event.getChannel().sendMessage("pongs").queue();
			}

		} else if (args[0].equalsIgnoreCase(Gbot.prefix + "morpstatut-off")) {
			if (event.getAuthor().getMutualGuilds().isEmpty()) {
				return;
			}

			if (event.getAuthor().getMutualGuilds().get(0).getMember(event.getAuthor()).getRoles()
					.contains(event.getAuthor().getMutualGuilds().get(0).getRoleById("688073869369671685"))) {
				Gbot.getJDA().removeEventListener(new MorpCommand());
				event.getChannel().sendMessage("Désactivation de la commande morp").queue();
			}
		} else if (args[0].equalsIgnoreCase(Gbot.prefix + "morpstatut-on")) {
			if (event.getAuthor().getMutualGuilds().isEmpty()) {
				return;
			}

			if (event.getAuthor().getMutualGuilds().get(0).getMember(event.getAuthor()).getRoles()
					.contains(event.getAuthor().getMutualGuilds().get(0).getRoleById("688073869369671685"))) {
				Gbot.getJDA().addEventListener(new MorpCommand());
				event.getChannel().sendMessage("Activation de la commande morp").queue();
			}
		}

	}

}