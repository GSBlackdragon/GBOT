package fr.blackdragon.gbot.morpion;

import java.util.TimerTask;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class DeleteChannelTask extends TimerTask {
	
	private TextChannel channel;
	private Role role;
	
	public DeleteChannelTask(TextChannel channel, Role role) {
		this.channel = channel;
		this.role = role;
	}

	@Override
	public void run() {
		this.channel.delete().queue();
		this.role.delete().queue();
	}

}
