package fr.blackdragon.gbot.utils;

public class EmbedField {

	private boolean blank;
	
	private String title;
	private String text;
	private boolean inline;
	
	
	public EmbedField(boolean blank, boolean inline) {
		this.blank = blank;
		this.inline = inline;
	}
	
	public EmbedField(String title, String text, boolean inline) {
		this.title = title;
		this.text = text;
		this.inline = inline;
	}
	
	public boolean isBlank() {
		return this.blank;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getText() {
		return this.text;
	}
	
	public boolean isInline() {
		return this.inline;
	}
	
}
