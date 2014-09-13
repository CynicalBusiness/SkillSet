package me.capit.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Skills implements Serializable {
	private static final long serialVersionUID = 5004667268740769774L;
	
	final UUID uuid;
	final SkillSetMain plugin;
	
	private HashMap<String, Integer> levels = new HashMap<String, Integer>();
	private int unspentPoints = 0;
	
	public Skills(SkillSetMain plugin, UUID uuid){
		this.plugin = plugin;
		this.uuid = uuid;
		
	}
	
	public int getSkillValue(String name){
		if (levels.containsKey(name)){
			return levels.get(name);
		} else {
			return 0;
		}
	}
	
	public void setSkillValue(String name, int value){
		levels.put(name, value);
	}
	
	public void incrementSkillValue(String name, int amount){
		int val = getSkillValue(name);
		levels.put(name, val+amount);
	}
	
	public void incrementSkillValue(String name){
		incrementSkillValue(name,1);
	}
	
	public int getUnspentPoints(){
		return unspentPoints;
	}
	
	public void giveUnspentPoints(int amount){
		unspentPoints+=amount;
	}
	
	public boolean hasUnspentPoints(int amount){
		return unspentPoints>=amount;
	}
	
	public boolean takeUnspentPoints(int amount){
		if (hasUnspentPoints(amount)){
			unspentPoints-=amount;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean spendUnspentPoints(String skill, int amount){
		if (takeUnspentPoints(amount)){
			incrementSkillValue(skill, amount);
			return true;
		} else {
			return false;
		}
	}
	
}
