package fr.blackdragon.gbot.morpion;

import java.util.Map;
import java.util.function.Consumer;

import net.dv8tion.jda.api.entities.User;

public class ControlWin {

	private Integer[][] possibilities = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 },
			{ 1, 5, 9 }, { 3, 5, 7 } };

	private boolean win = false;
	private boolean nul = false;
	private User winner = null;

	public ControlWin(User user, Map<Integer, User> map, Consumer<? super ControlWin> consumer) {
		int totalHut = 0;
		int validHut = 0;

		for (Integer[] posibility : this.possibilities) {
			validHut = 0;
			for (Integer hut : posibility) {
				if (map.get(hut) != null) {
					totalHut++;
					if (map.get(hut) == user) {
						validHut++;
					}
				}

				if (validHut == 3) {
					this.win = true;
					this.winner = user;

					consumer.accept(this);
					return;
				}
			}
		}

		if (totalHut == 24) {
			this.nul = true;
			consumer.accept(this);
			return;
		} else {
			consumer.accept(this);
			return;
		}
	}

	public Boolean isWin() {
		return this.win;
	}

	public Boolean isNull() {
		return this.nul;
	}

	public User getWinner() {
		return this.winner;
	}

}