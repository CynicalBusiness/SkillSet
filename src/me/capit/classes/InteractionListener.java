package me.capit.classes;

import java.io.Serializable;

import org.bukkit.event.Listener;

public class InteractionListener implements Listener, Serializable {
	private static final long serialVersionUID = -5312464588640480262L;
	
	final SkillSetMain plugin;
	public InteractionListener(SkillSetMain plugin){
		this.plugin = plugin;
	}
	
	

}
