package fr.blackdragon.gbot.form;

import java.util.HashMap;
import java.util.Map;

import fr.blackdragon.gbot.Gbot;
import fr.blackdragon.gbot.form.data.DataManager;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class FormManager extends ListenerAdapter {

	private User user;
	private int formIndex = 1;
	private Map<Integer, String> formMap = new HashMap<Integer, String>();

	public static Map<User, FormManager> formManagers = new HashMap<User, FormManager>();

	public FormManager(User user) {
		if (!formManagers.containsKey(user)) {
			this.user = user;
			formManagers.put(user, this);

			Gbot.getJDA().addEventListener(this);
			user.openPrivateChannel().queue(privateChannel -> {
				privateChannel.sendMessage(
						"**Bienvenue sur le serveur Discord dédié aux premières du Lycée Guillaume le Conquérant**")
						.queue();
			});

			sendQuestion();
		}
	}

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if (event.getChannel().getType() != ChannelType.PRIVATE) { return;}
		
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
		if (event.getChannelType() != ChannelType.PRIVATE) { return;}
		
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
		if (this.formIndex == (FormConfig.forms.values().size() + 1)) {
			Member member = Gbot.getJDA().getGuilds().get(0).getMember(this.user);

			if (DataManager.validRegister(formMap.get(8), formMap.get(9), formMap.get(10), formMap.get(1),
					formMap.get(2), formMap.get(3), formMap.get(4), formMap.get(5), formMap.get(6), formMap.get(7), this.user)) {
				Gbot.getJDA().removeEventListener(this);
				formManagers.remove(this.user);
				this.user.openPrivateChannel().queue(privateChannel -> {
					privateChannel.sendMessage(
							"**Merci d'avoir rempli le formulaire, tu as désormais accès à tous les channels te correspondants !**")
							.queue();
				});

				member.modifyNickname(formMap.get(8) + " " + formMap.get(9).substring(0, 1)).queue();

				for (Role role : member.getGuild().getRoles()) {
					if (role.getName().contains(formMap.get(2))) {
						if (role.getName().equalsIgnoreCase(formMap.get(2))) {
							member.getGuild().addRoleToMember(member, role).queue();
						} else if (role.getName().equalsIgnoreCase(formMap.get(2) + formMap.get(3))) {
							member.getGuild().addRoleToMember(member, role).queue();
						}
					} else if (role.getName().contains(formMap.get(4))) {
						if (role.getName().equalsIgnoreCase(formMap.get(4))) {
							member.getGuild().addRoleToMember(member, role).queue();
						} else if (role.getName().equalsIgnoreCase(formMap.get(4) + formMap.get(5))) {
							member.getGuild().addRoleToMember(member, role).queue();
						}
					} else if (role.getName().contains(formMap.get(6))) {
						if (role.getName().equalsIgnoreCase(formMap.get(6))) {
							member.getGuild().addRoleToMember(member, role).queue();
						} else if (role.getName().equalsIgnoreCase(formMap.get(6) + formMap.get(7))) {
							member.getGuild().addRoleToMember(member, role).queue();
						}
					} else if (role.getName().equals("1G" + formMap.get(1))) {
						member.getGuild().addRoleToMember(member, role).queue();
					} else if (role.getId().equals("688075891796213943")) {
						member.getGuild().addRoleToMember(member, role).queue();
					} else if (role.getId().equals("689067499962368041")) {
						member.getGuild().addRoleToMember(member, role).queue();
					}
				}
			} else {
				this.user.openPrivateChannel().queue(privateChannel -> {
					privateChannel.sendMessage(
							"**Il y a un problème dans ton formulaire, merci de bien vouloir recommencer et de répondre au plus simplement aux questions qui te sont demandées :)**")
							.queue(message -> {
								this.formIndex = 1;
								sendQuestion();
							});
				});
			}
		} else {
			sendQuestion();
		}
	}

}
