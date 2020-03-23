package fr.blackdragon.gbot.events;

import fr.blackdragon.gbot.form.FormManager;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class UserJoinEvent extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		new FormManager(event.getUser());
	}
	
}
