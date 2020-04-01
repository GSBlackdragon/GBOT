package fr.blackdragon.gbot.morpion.leaderboard;

import java.util.List;
import java.util.Timer;

import fr.blackdragon.gbot.Gbot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class LeaderboardManager {

	private Timer timer = new Timer();
	private TextChannel channel;
	private Message message;
	
	public LeaderboardManager(String channelID) {
		this.channel = Gbot.getJDA().getTextChannelById(channelID);
		this.channel.getHistory().retrievePast(100).queue(messages -> {
			for (Message message : messages) {
				if (message.getAuthor().isBot()) {
					this.message = message;
				}
			}
		});
		
		timer.schedule(new LeaderboardTask(), 5000, 120000);
	}
	
	public TextChannel getTextChannel() {
		return this.channel;
	}
	
	public Message getMessage() {
		return this.message;
	}
	
}
