package fr.blackdragon.gbot.morpion;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import fr.blackdragon.gbot.Gbot;
import net.dv8tion.jda.api.entities.User;

public class ImageBuilder {

	private Map<Integer, User> morpMap = new HashMap<Integer, User>();
	private Morpion instance;

	public ImageBuilder() {
		if (!new File(Gbot.getJarFolder() + "background.png").exists()) {
			try {
				Files.copy(getClass().getClassLoader().getResourceAsStream("background.png"),
						Paths.get(Gbot.getJarFolder() + "background.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!new File(Gbot.getJarFolder() + "circle.png").exists()) {
			try {
				Files.copy(getClass().getClassLoader().getResourceAsStream("circle.png"),
						Paths.get(Gbot.getJarFolder() + "circle.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!new File(Gbot.getJarFolder() + "cross.png").exists()) {
			try {
				Files.copy(getClass().getClassLoader().getResourceAsStream("cross.png"),
						Paths.get(Gbot.getJarFolder() + "cross.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static File makeImage(Map<Integer, User> morpMap, Map<User, FormEnum> formMap) {
		Graphics graphics;

		BufferedImage background;
		File output = randomTMPFile(Gbot.getJarFolder() + "classes/tempImage/", ".png");

		int x = 60;
		int y = 60;

		try {
			background = ImageIO.read(new File(Gbot.getJarFolder() + "background.png"));
			graphics = background.getGraphics();

			for (User user : morpMap.values()) {
				if (user != null) {
					BufferedImage form = ImageIO.read(new File(Gbot.getJarFolder() + formMap.get(user).toString().toLowerCase() + ".png"));
					graphics.drawImage(form, x, y, null);
				}

				x = x + 180;
				if (x == 600) {
					y = y + 180;
					x = 60;
				}
			}
			
			ImageIO.write(resize(background, 480, 480), "png", output);
			
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

	public static File randomTMPFile(String path, String extension) {
		File file = new File(path + generate(16) + extension);

		while (file.exists()) {
			file = new File(path + generate(16) + extension);
		}

		return file;
	}

	public static String generate(int length) {
		Random random = new Random();
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"; // Tu supprimes les lettres
																							// dont tu ne veux pas
		String pass = "";
		for (int x = 0; x < length; x++) {
			pass += chars.charAt(random.nextInt(chars.length()-1));
		}
		return pass;
	}

}
