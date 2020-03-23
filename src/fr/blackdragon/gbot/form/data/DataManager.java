package fr.blackdragon.gbot.form.data;

import fr.blackdragon.gbot.database.Query;

public class DataManager {

	public static boolean validRegister(String prenom, String nom, String date, String classe, String spe1,
			String numberSpe1, String spe2, String numberSpe2, String spe3, String numberSpe3) {

		if (isNumeric(classe) && isNumeric(numberSpe1) && isNumeric(numberSpe2) && isNumeric(numberSpe3)
				&& date.split("/").length == 3) {
			Query.registerUser(prenom, nom, date, Integer.parseInt(classe), (spe1 + numberSpe1), (spe2 + numberSpe2),
					(spe3 + numberSpe3));

			return true;
		}

		return false;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

}
