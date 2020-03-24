package fr.blackdragon.gbot.events;

import fr.blackdragon.gbot.form.FormManager;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionAddEvent extends ListenerAdapter {

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		if (event.getUser().getMutualGuilds().isEmpty()) { return; }
		
		if (!event.getReaction().getReactionEmote().getEmoji().equals("💡")) { return; }
		
		if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("688075891796213943"))) {
			new FormManager(event.getUser());
		}
	}

}
