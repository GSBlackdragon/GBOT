package fr.blackdragon.gbot.form;

import java.util.HashMap;
import java.util.Map;

import fr.blackdragon.gbot.Gbot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class FormManager extends ListenerAdapter {

	private User user;
	private int formIndex = 1;
	private Map<Integer, String> formMap = new HashMap<Integer, String>();

	public FormManager(User user) {
		this.user = user; 
		
		Gbot.getJDA().addEventListener(this);
		user.openPrivateChannel().queue(privateChannel -> {
			privateChannel
					.sendMessage(
							"**Bienvenue sur le serveur Discord dédié aux premières du Lycée Guillaume le Conquérant**")
					.queue();
		});

		sendQuestion();
	}

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if (event.getAuthor() == this.user) {
			if (FormConfig.forms.get(this.formIndex).getType() == FormType.text) {
				formMap.put(this.formIndex, event.getMessage().getContentDisplay());
				this.formIndex++;
				checkFinish();
			}
		}
	}

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		if (event.getUser() == this.user) {
			if (FormConfig.forms.get(this.formIndex).getType() == FormType.reaction) {
				formMap.put(this.formIndex,
						FormConfig.equivalent.get(event.getReaction().getReactionEmote().getEmoji()));
				this.formIndex++;
				checkFinish();
			}
		}
	}

	public void sendQuestion() {
		this.user.openPrivateChannel().queue(privateChannel -> {
			privateChannel.sendMessage(FormConfig.forms.get(this.formIndex).getText()).queue(message -> {
				FormConfig.forms.get(this.formIndex).getReactionMap().forEach((id, emote) -> {
					message.addReaction(emote).queue();
				});
			});
		});
	}

	public void checkFinish() {
		if (this.formIndex == (FormConfig.forms.values().size()+1)) {
			Gbot.getJDA().removeEventListener(this);
			this.user.openPrivateChannel().queue(privateChannel -> {
				privateChannel.sendMessage(
						"**Merci d'avoir rempli le formulaire, tu as désormais accès a tout les channels te correspondants !**")
						.queue();
			});
		} else {
			sendQuestion();
		}
	}

}
