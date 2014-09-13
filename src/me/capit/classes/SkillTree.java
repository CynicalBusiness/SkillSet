package me.capit.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class SkillTree implements Serializable {
	private static final long serialVersionUID = -2365953189384666737L;
	
	public enum SkillType {
		BREAK, PLACE, CRAFT, USE, INTERACT,
		SMELT, BREW,
		ENCHANT,
		KILL, ATTACK, TAME
	}
	
	class SkillItem implements Serializable{
		private static final long serialVersionUID = 9103082537201131942L;
		public final String key; public final int val; public final SkillType type;
		
		public SkillItem(String key, int val, SkillType type){
			this.key=key; this.val=val;
			this.type = type;
		}
		
		public SkillItem(String ref, SkillType type){
			String[] keyval = ref.split(">");
			this.key = keyval[0];
			this.val = Integer.parseInt(keyval[1]);
			this.type = type;
		}
	}
	
	final SkillSetMain plugin;
	final FileConfiguration config;
	
	public SkillTree(SkillSetMain plugin, FileConfiguration config){
		this.plugin = plugin;
		this.config = config;
		SkillSetMain.logger.info("  Successfully registered skill tree.");
	}

	public boolean skillExists(String skill){
		for (String key : config.getConfigurationSection("skills").getKeys(false)){
			if (key==skill) return true;
		}
		return false;
	}
	
	public List<SkillItem> getJobs(String skill){
		return get(skill, true);
	}
	
	public List<SkillItem> getPrereq(String skill){
		return get(skill, false);
	}
	
	private List<SkillItem> get(String skill, boolean isjob){
		String key = isjob ? "jobs" : "prereq";
		List<SkillItem> items = new ArrayList<SkillItem>();
		if (config.contains("skills."+skill)){
			for (String item : config.getConfigurationSection("skills."+skill+"."+key).getKeys(false)){
				String ref = "skills."+skill+"."+key+".";
				SkillType type = SkillType.valueOf(item);
				if (type!=null){
					items.add(new SkillItem(config.getString(ref+item),type));
				}
			}
		} 
		return items;
	}
}
