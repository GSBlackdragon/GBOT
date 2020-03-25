package fr.blackdragon.gbot.birthday;

import java.util.Calendar;
import java.util.Date;

import net.dv8tion.jda.api.entities.User;

public class Birth {
	
	private User user;
	private Calendar calendar;
	private Date date;
	
	public Birth(User user, Calendar calendar, Date date) {
		this.user = user;
		this.calendar = calendar;
		this.date = date;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public Calendar getCalendar() {
		return this.calendar;
	}
	
	public Date getDate() {
		return this.date;
	}

}
