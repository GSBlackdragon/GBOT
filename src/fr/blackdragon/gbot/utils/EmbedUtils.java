package fr.blackdragon.gbot.utils;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedUtils {

	public static EmbedBuilder buildEmbed(String title, String description, Color color, String thumbnail, String footer, EmbedField... fields) {
		EmbedBuilder eb = new EmbedBuilder();
		
		eb.setTitle(title);
		eb.setDescription(description);
		eb.setColor(color);
		eb.setThumbnail(thumbnail);
		
		if (footer != null) {
			eb.setFooter(footer);
		}
		
		if (fields != null) {
			for (EmbedField field : fields) {
				if (field.isBlank()) {
					eb.addBlankField(field.isInline());
				} else {
					eb.addField(field.getTitle(), field.getText(), field.isInline());
				}
			}
		}
		return eb;
	}
	
	public static EmbedBuilder buildEmbed(String title, String description, Color color, String thumbnail, String footer, List<EmbedField> fields) {
		EmbedBuilder eb = new EmbedBuilder();
		
		eb.setTitle(title);
		eb.setDescription(description);
		eb.setColor(color);
		eb.setThumbnail(thumbnail);
		
		if (footer != null) {
			eb.setFooter(footer);
		}
		
		if (fields != null) {
			for (EmbedField field : fields) {
				if (field.isBlank()) {
					eb.addBlankField(field.isInline());
				} else {
					eb.addField(field.getTitle(), field.getText(), field.isInline());
				}
			}
		}
		return eb;
	}
	
}
