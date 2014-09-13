package me.capit.classes;

import java.io.Serializable;

import org.bukkit.configuration.file.YamlConfiguration;

public class SkillTree implements Serializable {
	private static final long serialVersionUID = -2365953189384666737L;
	
	final SkillSetMain plugin;
	final YamlConfiguration config;
	
	public SkillTree(SkillSetMain plugin, YamlConfiguration config){
		this.plugin = plugin;
		this.config = config;
	}

	public boolean skillExists(String skill){
		for (String key : config.getConfigurationSection("skills").getKeys(false)){
			if (key==skill) return true;
		}
		return false;
	}
	
}
