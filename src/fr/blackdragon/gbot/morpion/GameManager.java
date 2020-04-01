package fr.blackdragon.gbot.morpion;

import java.util.Random;
import java.util.Timer;

import fr.blackdragon.gbot.Gbot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GameManager extends ListenerAdapter {

	private Morpion instance;
	private TextChannel morpChannel;
	private Message lastMessage;
	private User waitUser;

	public GameManager(Morpion instance) {
		Random rand = new Random();

		this.instance = instance;
		this.morpChannel = generateChannel();

		this.waitUser = instance.getUsers().get(rand.nextInt(instance.getUsers().size()));

		Gbot.getJDA().addEventListener(this);

		startGame();
	}

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		if (event.getUser().isBot()) {
			return;
		}

		if (event.getChannel().getIdLong() != morpChannel.getIdLong()) {
			return;
		}

		if (waitUser != event.getUser()) {
			event.getReaction().removeReaction().queue();
		}

		if (event.getMessageIdLong() == this.lastMessage.getIdLong()) {
			Timer timer = new Timer();
			switch (event.getReactionEmote().getEmoji()) {
			case "1️⃣":
				this.instance.getMorpMap().replace(1, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;
			case "2️⃣":
				this.instance.getMorpMap().replace(2, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;
			case "3️⃣":
				this.instance.getMorpMap().replace(3, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;
			case "4️⃣":
				this.instance.getMorpMap().replace(4, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;
			case "5️⃣":
				this.instance.getMorpMap().replace(5, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;
			case "6️⃣":
				this.instance.getMorpMap().replace(6, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;
			case "7️⃣":
				this.instance.getMorpMap().replace(7, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;
			case "8️⃣":
				this.instance.getMorpMap().replace(8, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;
			case "9️⃣":
				this.instance.getMorpMap().replace(9, this.waitUser);
				new ControlWin(this.waitUser, this.instance.getMorpMap(), checkWin -> {
					if (checkWin.isWin()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage("Très beau coup décisif de la part de "
								+ checkWin.getWinner().getAsMention() + " qui remporte ce match !\nDommage "
								+ ((this.instance.getUsers().indexOf(checkWin.getWinner()) == 1)
										? this.instance.getUsers().get(0)
										: this.instance.getUsers().get(1)).getAsMention()
								+ ", peut-être pour une prochaine fois 🤷").queue();
						this.instance.setMatchFinish(checkWin.getWinner());
					} else if (checkWin.isNull()) {
						timer.schedule(new DeleteChannelTask(this.morpChannel, this.instance.getRole()), 15000);
						Gbot.getJDA().removeEventListener(this);
						this.morpChannel.sendMessage(
								"De très bon athlètes français ce soir, mais malheureusement peut-être tout simplement trop fort 🤷\nCe match se fini donc sur un match nul, 0 - 0, balle au centre !")
								.queue();
						this.instance.setMatchFinish(null);
					} else {
						nextMessage();
					}
				});
				return;

			default:
				return;
			}
		}
	}

	private void startGame() {
		this.morpChannel.sendMessage(
				"Mesdames et Messieurs, j'espère que vous êtes chaud ! \nAujourd'hui nous accueillons deux nouveaux combattants, merci d'accueillir "
						+ this.instance.getUsers().get(0).getAsMention() + " et "
						+ this.instance.getUsers().get(1).getAsMention() + "!")
				.queue();
		this.morpChannel.sendMessage("A ton tour " + this.waitUser.getAsMention())
				.addFile(ImageBuilder.makeImage(this.instance.getMorpMap(), this.instance.getUserForm()))
				.queue(message -> {
					try {
						message.addReaction("1️⃣").queue();
						message.addReaction("2️⃣").queue();
						message.addReaction("3️⃣").queue();
						message.addReaction("4️⃣").queue();
						message.addReaction("5️⃣").queue();
						message.addReaction("6️⃣").queue();
						message.addReaction("7️⃣").queue();
						message.addReaction("8️⃣").queue();
						message.addReaction("9️⃣").queue();
					} catch (Exception e) {
						System.out.println("Morpion: Erreur des réactions par " + this.waitUser);
					}

					this.lastMessage = message;
				});
	}

	private void nextMessage() {
		this.waitUser = (this.instance.getUsers().indexOf(this.waitUser) == 1) ? this.instance.getUsers().get(0)
				: this.instance.getUsers().get(1);

		this.morpChannel.sendMessage("A ton tour " + this.waitUser.getAsMention())
				.addFile(ImageBuilder.makeImage(this.instance.getMorpMap(), this.instance.getUserForm()))
				.queue(message -> {
					this.lastMessage.delete().queue(deleted -> {
						this.lastMessage = message;
					});

					int index = 0;
					String[] emoji = { "1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣" };

					for (User user : this.instance.getMorpMap().values()) {
						if (user == null) {
							try {
								message.addReaction(emoji[index]).queue();
							} catch (Exception e) {
								System.out.println("Morpion: Erreur des réactions par " + this.waitUser);
							}
						}

						index++;
					}
				});
	}

	private TextChannel generateChannel() {
		int number = 0;

		while (!Gbot.getJDA().getGuilds().get(0).getTextChannelsByName("morp" + number, true).isEmpty()) {
			number++;
		}

		return Gbot.getJDA().getGuilds().get(0).getCategoryById("692793898623303826").createTextChannel("morp" + number)
				.complete();
	}

}
