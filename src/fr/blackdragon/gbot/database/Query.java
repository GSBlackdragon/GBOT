package fr.blackdragon.gbot.database;

import java.sql.PreparedStatement;

import fr.blackdragon.gbot.Gbot;
import net.dv8tion.jda.api.entities.User;

public class Query {

	public static void registerUser(String prenom, String nom, String date, int classe, String spe1, String spe2, String spe3, User user) {
		String request = "INSERT INTO `users`(`prenom`, `nom`, `date`, `classe`, `spe1`, `spe2`, `spe3`, `userID`) VALUES (?,?,?,?,?,?,?,?)";

		PreparedStatement statement;

		try {
			statement = Gbot.getDatabase().getConnection().prepareStatement(request);
			statement.setString(1, prenom);
			statement.setString(2, nom);
			statement.setString(3, date);
			statement.setInt(4, classe);
			statement.setString(5, spe1);
			statement.setString(6, spe2);
			statement.setString(7, spe3);
			statement.setLong(8, user.getIdLong());
			
			statement.executeUpdate();
		} catch (Exception e) {
		}

	}

}
