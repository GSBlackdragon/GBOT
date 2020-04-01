package fr.blackdragon.gbot.morpion;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fr.blackdragon.gbot.Gbot;
import fr.blackdragon.gbot.utils.EmbedField;
import fr.blackdragon.gbot.utils.EmbedUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Morpion extends ListenerAdapter {

	private List<User> users = new ArrayList<User>();
	private Role role;
	private TextChannel channel;
	private Message message;
	private Map<Integer, User> morpMap;
	private MorpionEnum gameStatut = MorpionEnum.WAIT;
	private Map<User, FormEnum> formMap = new HashMap<User, FormEnum>();

	public static List<Morpion> instances = new ArrayList<Morpion>();
	public static List<User> playersInGame = new ArrayList<User>();

	public Morpion() {
	}

	public Morpion(User user1, User user2, TextChannel channel) {
		this.users.add(user1);
		this.users.add(user2);
		this.channel = channel;

		this.morpMap = initializeMap();
		this.role = createRole();

		generateMessage();

		this.formMap.put(user1, FormEnum.CROSS);
		instances.add(this);
		Gbot.getJDA().addEventListener(this);

		Gbot.getJDA().getGuilds().get(0).addRoleToMember(user1.getIdLong(), this.role).queue();
		playersInGame.add(user1);
	}

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		if (event.getUser().isBot()) {
			return;
		}

		if (!event.getMessageId().equals(this.message.getId())) {
			return;
		}

		if (this.gameStatut == MorpionEnum.WAIT) {
			if (this.users.contains(event.getUser()) && event.getReaction().getReactionEmote().getEmoji().equals("❌")) {
				this.message.clearReactions().queue();
				this.message.delete().queue();
				this.role.delete().queue();
				playersInGame.remove(event.getUser());

				return;
			} else if (!this.users.contains(event.getUser())
					&& event.getReaction().getReactionEmote().getEmoji().equals("🥊") && !playersInGame.contains(event.getUser())) {
				playersInGame.add(event.getUser());
				this.users.set(1, event.getUser());
				this.formMap.put(event.getUser(), FormEnum.CIRCLE);
				Gbot.getJDA().getGuilds().get(0).addRoleToMember(event.getUser().getIdLong(), this.role).queue();
				MessageEmbed embed = EmbedUtils.buildEmbed("Morpion | Phase de qualification",
						"Construction du stade en cours...", Color.RED,
						"https://media.gettyimages.com/vectors/tic-tac-toe-game-flat-design-icon-vector-id1146577964?k=6&m=1146577964&s=612x612&w=0&h=xo74d5AiwunyDwYqNy5E9I3prXVRco0szs7kKyIuOA8=",
						null, new EmbedField("Joueur n°1", users.get(0).getAsMention(), true),
						new EmbedField("Joueur n°2", users.get(1).getAsMention(), true)).build();

				this.gameStatut = MorpionEnum.IN_WAIT;
				this.message.editMessage(embed).queue();
				this.message.clearReactions().queue(reactions -> {
					startCooldown(this.message);
				});

				return;
			}
		} else if (this.gameStatut == MorpionEnum.IN_MATCH || this.gameStatut == MorpionEnum.IN_WAIT) {
			if (!users.contains(event.getUser())) {
				event.getReaction().removeReaction().queue();

				return;
			}
		}

		event.getReaction().removeReaction(event.getUser()).queue();
	}

	public List<User> getUsers() {
		return this.users;
	}

	public TextChannel getChannel() {
		return this.channel;
	}

	public Message getMessage() {
		return this.message;
	}

	public Role getRole() {
		return this.role;
	}

	public Map<Integer, User> getMorpMap() {
		return this.morpMap;
	}

	public Map<User, FormEnum> getUserForm() {
		return this.formMap;
	}

	public void setStatut(MorpionEnum statut) {
		this.gameStatut = statut;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setMatchFinish(User winner) {
		MessageEmbed embed;

		if (winner != null) {
			embed = EmbedUtils.buildEmbed("Morpion | Phase de qualification",
					"Match terminé: Victoire écrasante de " + winner.getAsMention(), Color.RED,
					"https://media.gettyimages.com/vectors/tic-tac-toe-game-flat-design-icon-vector-id1146577964?k=6&m=1146577964&s=612x612&w=0&h=xo74d5AiwunyDwYqNy5E9I3prXVRco0szs7kKyIuOA8=",
					null, new EmbedField("Joueur n°1", users.get(0).getAsMention(), true),
					new EmbedField("Joueur n°2", users.get(1).getAsMention(), true)).build();
		} else {
			embed = EmbedUtils.buildEmbed("Morpion | Phase de qualification",
					"Match terminé: Match nul, certainement trop de talents...", Color.RED,
					"https://media.gettyimages.com/vectors/tic-tac-toe-game-flat-design-icon-vector-id1146577964?k=6&m=1146577964&s=612x612&w=0&h=xo74d5AiwunyDwYqNy5E9I3prXVRco0szs7kKyIuOA8=",
					null, new EmbedField("Joueur n°1", users.get(0).getAsMention(), true),
					new EmbedField("Joueur n°2", users.get(1).getAsMention(), true)).build();
		}

		this.message.editMessage(embed).queue();
		this.gameStatut = MorpionEnum.FINISH;
	}
	
	public void setMatchStart() {
		MessageEmbed embed = EmbedUtils.buildEmbed("Morpion | Phase de qualification",
				"Match en cours...", Color.RED,
				"https://media.gettyimages.com/vectors/tic-tac-toe-game-flat-design-icon-vector-id1146577964?k=6&m=1146577964&s=612x612&w=0&h=xo74d5AiwunyDwYqNy5E9I3prXVRco0szs7kKyIuOA8=",
				null, new EmbedField("Joueur n°1", users.get(0).getAsMention(), true),
				new EmbedField("Joueur n°2", users.get(1).getAsMention(), true)).build();
		this.message.editMessage(embed).queue();
	}

	private Map<Integer, User> initializeMap() {
		Map<Integer, User> map = new HashMap<Integer, User>();

		for (int i = 1; i < 10; i++) {
			map.put(i, null);
		}

		return map;
	}

	private Role createRole() {
		int number = 0;

		while (!Gbot.getJDA().getGuilds().get(0).getRolesByName("morp" + number, true).isEmpty()) {
			number++;
		}

		return Gbot.getJDA().getGuilds().get(0).createRole().setName("morp" + number).setColor(Color.GREEN).complete();
	}

	private void generateMessage() {
		MessageEmbed embed = EmbedUtils.buildEmbed("Morpion | Phase de qualification",
				users.get(0).getAsMention() + " cherche un joueur à écraser !", Color.RED,
				"https://media.gettyimages.com/vectors/tic-tac-toe-game-flat-design-icon-vector-id1146577964?k=6&m=1146577964&s=612x612&w=0&h=xo74d5AiwunyDwYqNy5E9I3prXVRco0szs7kKyIuOA8=",
				null, new EmbedField("Joueur n°1", users.get(0).getAsMention(), true),
				new EmbedField("Joueur n°2", "🔎 Recherche d'un joueur", true)).build();

		this.message = this.channel.sendMessage(embed).complete();
		this.message.addReaction("🥊").queue();
		this.message.addReaction("💨").queue();
		this.message.addReaction("❌").queue();
	}

	private void startCooldown(Message message) {
		Morpion instance = this;
		Timer timer = new Timer();
		List<String> reactions = new ArrayList<String>();
		reactions.add("1️⃣");
		reactions.add("2️⃣");
		reactions.add("3️⃣");
		reactions.add("4️⃣");
		reactions.add("5️⃣");

		message.addReaction("1️⃣").queue();
		message.addReaction("2️⃣").queue();
		message.addReaction("3️⃣").queue();
		message.addReaction("4️⃣").queue();
		message.addReaction("5️⃣").queue();

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (reactions.isEmpty()) {
					instance.setStatut(MorpionEnum.IN_MATCH);
					instance.setMatchStart();
					new GameManager(instance);
					this.cancel();
				} else {
					message.removeReaction(reactions.get(reactions.size() - 1)).queue();
					reactions.remove(reactions.size() - 1);
				}
			}
		}, 5000, 1000);
	}

}
