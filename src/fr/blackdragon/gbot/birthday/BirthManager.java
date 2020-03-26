package fr.blackdragon.gbot.birthday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import fr.blackdragon.gbot.Gbot;
import fr.blackdragon.gbot.database.Query;
import net.dv8tion.jda.api.entities.User;

public class BirthManager {

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH-dd/MM/yyyy");
	public static Map<User, Birth> birthdays = new HashMap<User, Birth>();

	public BirthManager() {
		TimerTask task = new BirthTask();
		Timer timer = new Timer();
		ResultSet result = Query.getUserIDwithBirth();

		try {
			while (result.next()) {
				User user = Gbot.getJDA().getUserById(result.getString("userID"));
				Date date = dateFormat.parse("12-" + result.getString("date"));
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
				
				birthdays.put(user, new Birth(null, calendar, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		timer.scheduleAtFixedRate(task, 0, 7200000);
	}

}
