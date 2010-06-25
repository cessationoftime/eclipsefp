package net.sf.eclipsefp.haskell.scion.types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author JP Moresmau
 *
 */
public class CabalPackage {
	private String name;
	private String version;
	private boolean exposed;
	private Component[] components;
	
	public CabalPackage(JSONObject obj) throws JSONException {
		name=obj.getString("name");
		version=obj.getString("version");
		exposed=obj.getBoolean("exposed");
		JSONArray arr=obj.getJSONArray("dependent");
		components=new Component[arr.length()];
		for (int a=0;a<arr.length();a++){
			components[a]=new Component(arr.getJSONObject(a));
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isExposed() {
		return exposed;
	}
	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}
	public Component[] getComponents() {
		return components;
	}
	public void setComponents(Component[] components) {
		this.components = components;
	}
	
	
	
}