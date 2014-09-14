package me.capit.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.capit.classes.SkillTree.SkillItem;
import me.capit.classes.SkillTree.SkillType;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class InteractionListener implements Listener, Serializable {
	private static final long serialVersionUID = -5312464588640480262L;
	
	final SkillSetMain plugin;
	public InteractionListener(SkillSetMain plugin){
		this.plugin = plugin;
	}
	
	public List<SkillItem> getMatchedFor(SkillType type, String key, boolean isjob){
		List<SkillItem> list = new ArrayList<SkillItem>();
		for (String skill : SkillSetMain.tree.getSkillNames()){
			List<SkillItem> items = isjob ? SkillSetMain.tree.getJobs(skill) : SkillSetMain.tree.getPrereq(skill);
			for (SkillItem item : items){
				// TODO Check for stars.
				if (item.type==type && (item.key==key)) list.add(item);
			}
		}
		return list;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if (!e.isCancelled()){
			Block b = e.getBlock();
			Player p = e.getPlayer();
			String key = b.getType().toString().toLowerCase();
			if (b.getData()!=0){
				key+=":"+b.getData();
			}
			List<SkillItem> jobs = getMatchedFor(SkillType.BREAK, key, true);
			List<SkillItem> prereq = getMatchedFor(SkillType.BREAK, key, false);
			SkillSetMain.logger.info("Player "+p.getName()+" broke "+b.getType().toString()+" with "+jobs.size()
					+" jobs and "+prereq.size()+" prereq.");
		}
	}

}
