package fr.blackdragon.gbot.events;

import fr.blackdragon.gbot.form.FormManager;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateMessageEvent extends ListenerAdapter {

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if (event.getAuthor().getMutualGuilds().isEmpty()) {
			return;
		}
		
		if (event.getAuthor().isBot()) {
			return;
		}

		if (!event.getAuthor().getMutualGuilds().get(0).getMember(event.getAuthor()).getRoles()
				.contains(event.getAuthor().getMutualGuilds().get(0).getRoleById("688075891796213943"))) {
			new FormManager(event.getAuthor());
		}
	}

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		if (event.getUser().getMutualGuilds().isEmpty()) {
			return;
		}
		
		if (event.getUser().isBot()) {
			return;
		}

		if (!event.getUser().getMutualGuilds().get(0).getMember(event.getUser()).getRoles()
				.contains(event.getUser().getMutualGuilds().get(0).getRoleById("688075891796213943"))) {
			new FormManager(event.getUser());
		}
	}

}
