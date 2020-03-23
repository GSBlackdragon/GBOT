package fr.blackdragon.gbot;

import javax.security.auth.login.LoginException;

import fr.blackdragon.gbot.events.UserJoinEvent;
import fr.blackdragon.gbot.form.FormConfig;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;

public class Gbot {

	private static JDA jda = null;
	public static String prefix = "%";
	private static String jarFolder = Gbot.class.getProtectionDomain().getCodeSource().getLocation().getPath();

	public static void main(String[] args) throws LoginException {

		jda = new JDABuilder(AccountType.BOT).setToken(args[0]).setActivity(Activity.playing("Créer des anti-sèches"))
				.build();

		jda.addEventListener(new UserJoinEvent());

		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
		}

		new FormConfig();
	}

	public static JDA getJDA() {
		return jda;
	}

	public static String getJarFolder() {
		return jarFolder;
	}

}
