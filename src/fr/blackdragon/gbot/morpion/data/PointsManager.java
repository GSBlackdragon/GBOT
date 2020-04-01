package fr.blackdragon.gbot.morpion.data;

import fr.blackdragon.gbot.database.Query;
import net.dv8tion.jda.api.entities.User;

public class PointsManager {

	public static void addUserPointsWin(User winner, User looser) {
		if (Query.hasPoints(winner)) {
			Query.addUserPoints(winner, 1, 1, 0);
		} else {
			Query.registerUser(winner);
			Query.addUserPoints(winner, 1, 1, 0);
		}

		if (Query.hasPoints(looser)) {
			Query.addUserPoints(looser, 1, 0, 1);
		} else {
			Query.registerUser(looser);
			Query.addUserPoints(looser, 1, 0, 1);
		}
	}

	public static void addUserPointsNul(User user1, User user2) {
		if (Query.hasPoints(user1)) {
			Query.addUserPoints(user1, 1, 0, 0);
		} else {
			Query.registerUser(user1);
			Query.addUserPoints(user1, 1, 0, 0);
		}

		if (Query.hasPoints(user2)) {
			Query.addUserPoints(user2, 1, 0, 0);
		} else {
			Query.registerUser(user2);
			Query.addUserPoints(user2, 1, 0, 0);
		}
	}

}
