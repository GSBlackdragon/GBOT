package fr.blackdragon.gbot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.*;

public class Gbot {
	
	private  static JDA jda = null;
	public static String prefix = "%";
	
	public static void main(String[] args) throws LoginException {
		
		jda = new JDABuilder(AccountType.BOT).setToken(args[0]).build();
		
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			
		}
	}
	
	public static JDA getJDA() {
		return jda;
	}
	
}
