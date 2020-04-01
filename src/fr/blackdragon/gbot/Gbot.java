package fr.blackdragon.gbot;

import java.io.File;

import javax.security.auth.login.LoginException;

import fr.blackdragon.gbot.birthday.BirthManager;
import fr.blackdragon.gbot.commands.Commands;
import fr.blackdragon.gbot.database.DatabaseManager;
import fr.blackdragon.gbot.events.PrivateMessageEvent;
import fr.blackdragon.gbot.events.ReactionAddEvent;
import fr.blackdragon.gbot.events.UserJoinEvent;
import fr.blackdragon.gbot.form.FormConfig;
import fr.blackdragon.gbot.morpion.ImageBuilder;
import fr.blackdragon.gbot.morpion.MorpCommand;
import fr.blackdragon.gbot.morpion.Morpion;
import fr.blackdragon.gbot.morpion.leaderboard.LeaderboardManager;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;

public class Gbot {

	private static JDA jda = null;
	public static String prefix = "%";
	private static String jarFolder = Gbot.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("GBOT.jar", "");
	private static DatabaseManager databaseManager;
	private static LeaderboardManager leaderboardManager;

	public static void main(String[] args) throws LoginException {
		
		File folder = new File(Gbot.getJarFolder() + "/classes/tempImage");
		folder.mkdir();

		jda = new JDABuilder(AccountType.BOT).setToken(args[0]).setActivity(Activity.playing("Créer des anti-sèches"))
				.build();

		jda.addEventListener(new UserJoinEvent());
		jda.addEventListener(new ReactionAddEvent());
		jda.addEventListener(new PrivateMessageEvent());
		jda.addEventListener(new Commands());
		jda.addEventListener(new MorpCommand());

		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
		}

		databaseManager = new DatabaseManager(args[1]
				+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris",
				args[2], args[3]);
		databaseManager.open();
		leaderboardManager = new LeaderboardManager("694952018296438875");

		new FormConfig();
		new BirthManager();
		new ImageBuilder();
	}

	public static JDA getJDA() {
		return jda;
	}

	public static String getJarFolder() {
		return jarFolder;
	}

	public static DatabaseManager getDatabase() {
		return databaseManager;
	}
	
	public static LeaderboardManager getLeaderboard() {
		return leaderboardManager;
	}

}
