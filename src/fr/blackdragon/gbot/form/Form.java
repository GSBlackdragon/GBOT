package fr.blackdragon.gbot.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form {
	
	private int id;
	private String text;
	private FormType type;
	private Map<Integer, String> reactionsMap = new HashMap<Integer, String>();
	
	public static List<Form> forms = new ArrayList<Form>();
	
	public Form(int id, String text, FormType type, Map<Integer, String> reactionsMap) {
		this.id = id;
		this.text = text;
		this.type = type;
		this.reactionsMap = reactionsMap;
		
		forms.add(this);
	}
	
	public int getID() {
		return this.id;
	}
	
	public String getText() {
		return this.text;
	}
	
	public FormType getType() {
		return this.type;
	}
	
	public Map<Integer, String> getReactionMap() {
		return this.reactionsMap;
	}
	
}
