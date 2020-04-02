package fr.blackdragon.gbot.morpion.leaderboard;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.TreeMap;

import fr.blackdragon.gbot.Gbot;
import fr.blackdragon.gbot.database.Query;
import fr.blackdragon.gbot.utils.EmbedField;
import fr.blackdragon.gbot.utils.EmbedUtils;

public class LeaderboardTask extends TimerTask {

	@Override
	public void run() {
		List<EmbedField> fields = new ArrayList<EmbedField>();
		TreeMap<Float, EmbedField> fieldsMap = new TreeMap<Float, EmbedField>();
		ResultSet set = Query.getUserProfiles();

		try {
			int index = 0;
			set.last();

			while (set.previous()) {
				index++;
				EmbedField field = new EmbedField(
						Gbot.getJDA().getGuilds().get(0).getMemberById(set.getString("userID")).getNickname() + " #"
								+ index,
						"Matchs: " + set.getInt("matchs") + "\nRatio: " + set.getFloat("ratio"), true);
				fields.add(field);
			}

			Gbot.getLeaderboard().getMessage().editMessage(EmbedUtils.buildEmbed("Morpion | Leaderboard",
					"Voici le tableau des champions ... ou peut-être des chanceux, mais bon là n'est pas le sujet ! "
							+ "Si pour ce tournoi tu dois te fixer un objectif, c'est bien de terminer en haut de ce classement !",
					Color.red, null, "Actualisé toute les 30sec", true, fields).build()).queue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
