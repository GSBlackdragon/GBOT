package fr.blackdragon.gbot.birthday;

import java.util.Calendar;
import java.util.TimerTask;

import fr.blackdragon.gbot.Gbot;

public class BirthTask extends TimerTask {

	@Override
	public void run() {
		System.out.println("Birth task instance");

		if (11 <= Calendar.getInstance().get(Calendar.HOUR) && Calendar.getInstance().get(Calendar.HOUR) <= 14) {
			System.out.println("Birth (Time ok) -> Check");
			BirthManager.birthdays.forEach((user, birth) -> {
				Calendar dateOfBirth = Calendar.getInstance();
				dateOfBirth.setTime(birth.getDate());

				if (birth.getCalendar().get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
						&& birth.getCalendar().get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)) {
					Gbot.getJDA().getTextChannelById("692440347166507008")
							.sendMessage("**Joyeux Anniversaire " + user.getAsMention() + " pour tes "
									+ (Calendar.getInstance().get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR))
									+ "ans !**")
							.queue();
				}
			});
		}
	}

}
