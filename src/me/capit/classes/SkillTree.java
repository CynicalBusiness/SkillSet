package me.capit.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

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
		public final String skill;
		
		public SkillItem(String key, int val, SkillType type, String skill){
			this.key=key; this.val=val;
			this.type = type;
			this.skill = skill;
		}
		
		public SkillItem(String ref, SkillType type, String skill){
			String[] keyval = ref.split(">");
			this.key = keyval[0];
			this.val = Integer.parseInt(keyval[1]);
			this.type = type;
			this.skill = skill;
		}
		
		// Stacks of 1 item have set dv. Stacks of two have any dv.
		// Air will match any item.
		public ItemStack getItem(){
			String[] keydv = key.split(":");
			Material item = null;
			ItemStack is = null;
			item = Material.valueOf(key);
			if (item!=null){
				is = new ItemStack(item, 1);
			} else if (keydv[0]=="*"){
				is = new ItemStack(Material.AIR, 1);
			}
			if (keydv.length>1){
				if (keydv[1]=="*"){
					is.setAmount(2);
				} else {
					is.setDurability((short) Integer.parseInt(keydv[1]));
				}
			}
			return is;
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
	
	public List<String> getSkillNames(){
		List<String> strs = new ArrayList<String>();
		for (String key : config.getConfigurationSection("skills").getKeys(false)){
			strs.add(key);
		}
		return strs;
	}
	
	public List<List<SkillItem>> getAllJobs(){
		List<List<SkillItem>> list = new ArrayList<List<SkillItem>>();
		for (String skill : getSkillNames()){
			list.add(getJobs(skill));
		}
		return list;
	}
	
	public List<List<SkillItem>> getAllPrereq(){
		List<List<SkillItem>> list = new ArrayList<List<SkillItem>>();
		for (String skill : getSkillNames()){
			list.add(getPrereq(skill));
		}
		return list;
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
					items.add(new SkillItem(config.getString(ref+item),type, skill));
				}
			}
		} 
		return items;
	}
}
