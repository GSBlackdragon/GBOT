package fr.blackdragon.gbot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.blackdragon.gbot.Gbot;
import net.dv8tion.jda.api.entities.User;

public class Query {

	private static Connection con = Gbot.getDatabase().getConnection();

	public static void registerUser(String prenom, String nom, String date, int classe, String spe1, String spe2,
			String spe3, User user) {
		String request = "INSERT INTO `users`(`prenom`, `nom`, `date`, `classe`, `spe1`, `spe2`, `spe3`, `userID`) VALUES (?,?,?,?,?,?,?,?)";

		PreparedStatement statement;

		try {
			statement = con.prepareStatement(request);
			statement.setString(1, prenom);
			statement.setString(2, nom);
			statement.setString(3, date);
			statement.setInt(4, classe);
			statement.setString(5, spe1);
			statement.setString(6, spe2);
			statement.setString(7, spe3);
			statement.setString(8, user.getId());

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ResultSet getUserIDwithBirth() {
		String request = "SELECT `date`, `userID` FROM `users` WHERE 1 ";

		PreparedStatement statement;

		try {
			statement = con.prepareStatement(request);

			return statement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static boolean hasPoints(User user) {
		String request = "SELECT `userID` FROM `morpion` WHERE userID = ?";

		PreparedStatement statement;
		ResultSet result = null;

		try {
			statement = con.prepareStatement(request);
			statement.setString(1, user.getId());

			result = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return countRows(result) > 0;
	}

	public static void registerUser(User user) {
		String request = "INSERT INTO `morpion`(`userID`) VALUES (?)";
		
		PreparedStatement statement;

		try {
			statement = con.prepareStatement(request);
			statement.setString(1, user.getId());

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int getUserMatchs(User user) {
		String request = "SELECT `matchs` FROM `morpion` WHERE userID = ?";

		PreparedStatement statement;
		ResultSet result = null;

		try {
			statement = con.prepareStatement(request);
			statement.setString(1, user.getId());

			result = statement.executeQuery();

			result.first();
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static int getUserWin(User user) {
		String request = "SELECT `win` FROM `morpion` WHERE userID = ?";

		PreparedStatement statement;
		ResultSet result = null;

		try {
			statement = con.prepareStatement(request);
			statement.setString(1, user.getId());

			result = statement.executeQuery();

			result.first();
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static int getUserLoose(User user) {
		String request = "SELECT `loose` FROM `morpion` WHERE userID = ?";

		PreparedStatement statement;
		ResultSet result = null;

		try {
			statement = con.prepareStatement(request);
			statement.setString(1, user.getId());

			result = statement.executeQuery();

			result.first();
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static void addUserPoints(User user, int m, int w, int l) {
		int matchs = m + getUserMatchs(user);
		int win = w + getUserWin(user);
		int loose = l + getUserLoose(user);

		String request = "UPDATE `morpion` SET `matchs`=?,`win`=?,`loose`=? WHERE  userID = ?";

		PreparedStatement statement;

		try {
			statement = con.prepareStatement(request);
			statement.setInt(1, matchs);
			statement.setInt(2, win);
			statement.setInt(3, loose);
			statement.setString(4, user.getId());

			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet getUserProfiles() {
		String request = "SELECT * FROM `morpion` ORDER BY `ratio`";
		
		PreparedStatement statement;
		ResultSet result = null;
		
		try {
			statement = con.prepareStatement(request);
			result = statement.executeQuery();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public static int countRows(ResultSet result) {
		try {
			result.last();

			return result.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
