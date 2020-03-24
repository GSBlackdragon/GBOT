package fr.blackdragon.gbot.form;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import fr.blackdragon.gbot.Gbot;

public class FormConfig {

	public static Map<Integer, Form> forms = new HashMap<Integer, Form>();
	public static Map<String, String> equivalent = new HashMap<String, String>();

	public FormConfig() {
		setFile();

		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(Gbot.getJarFolder() + "form.json")) {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			JSONArray jsonArray = (JSONArray) jsonObject.get("form");

			for (Object object : jsonArray) {
				JSONObject jsonObj = (JSONObject) object;
				Map<Integer, String> reactionsMap = new HashMap<Integer, String>();

				for (Object react : (JSONArray) jsonObj.get("reactions")) {
					JSONObject jsonReact = (JSONObject) react;

					reactionsMap.put(Integer.parseInt(jsonReact.get("reactionID").toString()),
							jsonReact.get("emote").toString());
					
					if (!equivalent.containsKey(jsonReact.get("emote").toString())) {
						equivalent.put(jsonReact.get("emote").toString(), jsonReact.get("name").toString());
					}
				}

				Form form = new Form(Integer.parseInt(jsonObj.get("id").toString()), jsonObj.get("text").toString(),
						FormType.valueOf(jsonObj.get("reponseType").toString()), reactionsMap);
				forms.put(Integer.parseInt(jsonObj.get("id").toString()), form);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void setFile() {
		if (!new File(Gbot.getJarFolder() + "form.json").exists()) {
			try {
				System.out.println(getClass().getClassLoader().getResourceAsStream("form.json"));
				Files.copy(getClass().getClassLoader().getResourceAsStream("form.json"), Paths.get(Gbot.getJarFolder() + "form.json"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
