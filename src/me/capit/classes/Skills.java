package me.capit.classes;

import java.util.UUID;

public class Skills {
	final UUID uuid;
	final SkillSetMain plugin;
	
	public Skills(SkillSetMain plugin, UUID uuid){
		this.plugin = plugin;
		this.uuid = uuid;
	}
	
}
